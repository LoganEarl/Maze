package maze.model;

import maze.Direction;
import maze.model.question.Question;
import maze.model.question.SkeletonKey;
import utils.Pair;

import java.util.*;

import static maze.Direction.*;

public class RandomWorldBuilder implements World.Builder {
    private int numRooms;
    private Random rnd;
    private List<Question> questions;
    private int maxCorridorLength;
    private long randomSeed;

    public RandomWorldBuilder(int numRooms, Set<Question> questions, int maxCorridorLength, long randomSeed) {
        if(numRooms < 2)
            throw new IllegalArgumentException("Need at least 2 rooms");
        if(questions == null || questions.size() < numRooms * 1.5)
            throw new IllegalArgumentException("Not enough questions");
        if(maxCorridorLength < 0)
            throw new IllegalArgumentException("Corridor length must be greater than or equal to 0");
        rnd = new Random(randomSeed);


        this.numRooms = numRooms;
        this.questions = new ArrayList<>(questions);
        this.maxCorridorLength = maxCorridorLength;
        this.randomSeed = randomSeed;
    }

    @Override
    public World build() {
        //phase 1 generate rooms
        Map<Point, TempRoom> rooms = new LinkedHashMap<>();
        generateRooms(numRooms, new Point(0, 0), null, rooms, new LinkedHashSet<>());

        //phase 2 make start and end
        TempRoom startRoom, endRoom;
        //TempRoom[] tempArray = rooms.values().toArray(new TempRoom[0]);
        //Pair<TempRoom> furthestApart = new Pair<>(tempArray[0], tempArray[1]);
        Pair<TempRoom> furthestApart = getFurthestApart(new ArrayList<>(rooms.values()));
        startRoom = furthestApart.itemA();
        endRoom = furthestApart.itemB();

        //phase 3 mark side rooms
        List<Direction> mainRoute = Algorithms.aStar(startRoom, endRoom);
        List<Door> mainItemableDoors = new ArrayList<>();
        Map<Door, Set<TempRoom>> roomsAccessibleBeforeDoor = new LinkedHashMap<>();
        TempRoom mainPathWalker = startRoom;

        for(Direction d: mainRoute){
            Door door = mainPathWalker.getDoor(d);
            mainPathWalker.isOnMainRoute = true;
            roomsAccessibleBeforeDoor.put(door,filterNonMainRouteOnly(Algorithms.floodFill(mainPathWalker,d)));
            mainPathWalker = (TempRoom)door.getOtherRoom(mainPathWalker);
            if(roomsAccessibleBeforeDoor.get(door).size() > 0)
                mainItemableDoors.add(door);
        }
        endRoom.isOnMainRoute = true;

        //phase 4 place conventional items
        int numKeys = mainItemableDoors.size()/3;
        for(int i = 0; i <= numKeys - 1; i++){
            int doorIndex = (i * 3);
            Item key = mainItemableDoors.get(doorIndex).getQuestion().constructKeyItem();
            mainItemableDoors.get(doorIndex).setKeyItem(key);
            Set<TempRoom> possibleRooms = roomsAccessibleBeforeDoor.get(mainItemableDoors.get(doorIndex));
            possibleRooms.toArray(new TempRoom[0])[rnd.nextInt(possibleRooms.size())].addItem(key);
        }

        //phase 5 place skeleton keys
        int numSkeletonKeys = (int)Math.ceil(mainItemableDoors.size()/6.0);
        Set<TempRoom> possibleRooms = filterNonMainRouteOnly(rooms.values());
        for(int i = 0; i < numSkeletonKeys; i++){
            TempRoom cur = possibleRooms.toArray(new TempRoom[0])[rnd.nextInt(possibleRooms.size())];
            possibleRooms.remove(cur);
            cur.addItem(new SkeletonKey());
        }

        return new World(startRoom, endRoom, randomSeed);
    }

    private static <T extends Room> Set<T> filterNonMainRouteOnly(Collection<T> in){
        Set<T> out = new HashSet<>();
        for(T t:in){
            if(!((TempRoom)t).isOnMainRoute)
                out.add(t);
        }
        return out;
    }

    private Pair<TempRoom> getFurthestApart(List<TempRoom> allRooms) {
        Pair<TempRoom> farthest = new Pair<>(allRooms.get(0), allRooms.get(1));
        int maxDistance = -1;

        for(int i = 0; i < allRooms.size()-1; i++){
            for(int j = i + 1; j < allRooms.size(); j++){
                int curDistance = Algorithms.aStar(allRooms.get(i), allRooms.get(j)).size();
                if(maxDistance == -1 || curDistance > maxDistance){
                    farthest = new Pair<>(allRooms.get(i), allRooms.get(j));
                    maxDistance = curDistance;
                }
            }
        }
        return farthest;
    }

    //returns the unused allocation
    private int generateRooms(
            int roomAllocation,
            Point roomPos,
            TempRoom lastRoom,
            Map<Point, TempRoom> existingRooms,
            Set<Point> reserved) {
        if (roomAllocation > 0) {
            roomAllocation--;
            TempRoom newRoom = new TempRoom(roomPos);
            existingRooms.put(roomPos, newRoom);
            if (lastRoom != null) {
                Direction toLastRoom = getDirection(roomPos, lastRoom.getPosition());
                newRoom.setRoomConnection(toLastRoom, lastRoom, toLastRoom.opposite(), expendQuestion());
            }
            Map<Direction, Integer> potentialDirections = getPotentialDirections(newRoom, existingRooms, reserved, maxCorridorLength);
            List<Direction> toExplore = new ArrayList<>(potentialDirections.keySet());
            if(toExplore.isEmpty() || roomAllocation == 0)
                return roomAllocation;

            int[] allocations = allocateSum(roomAllocation, toExplore.size(), rnd);
            int[] distances = new int[toExplore.size()];
            for (int i = 0; i < allocations.length; i++) {
                Direction d = toExplore.get(i);
                distances[i] = rnd.nextInt(potentialDirections.get(d)) + 1;
                if(allocations[i] > 0 && potentialDirections.get(d) > 0) {
                    Point walker = roomPos.getAdjacent(d);
                    for (int j = 0; j < distances[i]; j++) {
                        reserved.add(walker);
                        walker = walker.getAdjacent(d);
                    }
                }
            }

            int amountRemaining = 0;
            for (int i = 0; i < allocations.length; i++) {
                Direction d = toExplore.get(i);
                amountRemaining += generateRooms(
                        allocations[i] + amountRemaining,
                        roomPos.getAdjacent(d, distances[i]),
                        newRoom,
                        existingRooms,
                        reserved
                );
            }
            return amountRemaining;
        }
        return 0;
    }

    private static int[] allocateSum(int sum, int size, Random rnd) {
        int[] result = new int[size];
        while (sum > 0) {
            int toAllocate = rnd.nextInt(sum / size + 1) + 1;
            result[rnd.nextInt(size)] += toAllocate;
            sum -= toAllocate;
        }
        return result;
    }

    private Question expendQuestion() {
        return questions.remove(rnd.nextInt(questions.size()));
    }

    private static Map<Direction, Integer> getPotentialDirections(TempRoom sourceRoom, Map<Point, TempRoom> allRooms, Set<Point> reserved, int maxCorridor) {
        Map<Direction, Integer> potentialDirections = new LinkedHashMap<>();
        for (Direction direction : Direction.values()) {
            int roomDistance = 0;
            if (sourceRoom.getDoor(direction) == null) {
                Point walker = sourceRoom.getPosition().getAdjacent(direction);
                do {
                    roomDistance++;
                    if (isOccupied(walker, allRooms, maxCorridor, direction, reserved)) {
                        roomDistance--;
                        break;
                    }
                    walker = walker.getAdjacent(direction);
                } while (roomDistance <= maxCorridor);
            }

            if (roomDistance > 0)
                potentialDirections.put(direction, roomDistance);
        }
        return potentialDirections;
    }

    private static boolean isOccupied(Point selectPoint, Map<Point, TempRoom> allRooms, int maxCorridor, Direction perpendicular, Set<Point> reserved){
        boolean leftOpen = false;
        for(int i = maxCorridor; i > 0; i--){
            Point current = selectPoint.getAdjacent(perpendicular.left(),i);
            Room r = allRooms.get(current);
            if(r != null){
                if(r.hasDoor(perpendicular.right()))
                    leftOpen = true;
                else if(r.hasDoor(perpendicular.left()))
                    leftOpen = false;
            }
        }

        boolean rightOpen = false;
        for(int i = maxCorridor; i > 0; i--){
            Point current = selectPoint.getAdjacent(perpendicular.right(),i);
            Room r = allRooms.get(current);
            if(r != null){
                if(r.hasDoor(perpendicular.left()))
                    rightOpen = true;
                else if(r.hasDoor(perpendicular.right()))
                    rightOpen = false;
            }
        }
        return rightOpen || leftOpen || allRooms.containsKey(selectPoint) || reserved.contains(selectPoint);
    }

    //Utility classes beyond this point
    private static class TempRoom extends Room {
        private boolean isOnMainRoute = false;

        private TempRoom(Point p) {
            super(p.x, p.y);
        }

        private Point getPosition() {
            return new Point(this.getXCoordinate(), this.getYCoordinate());
        }
    }

    private static Direction getDirection(Point source, Point destination) {
        if (source.x > destination.x)
            return west;
        if (source.x < destination.x)
            return east;
        if (source.y > destination.y)
            return north;
        return south;
    }

    private static class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private Point getAdjacent(Direction d) {
            return getAdjacent(d, 1);
        }

        private Point getAdjacent(Direction d, int distance) {
            return new Point(x + d.getXDelta() * distance, y + d.getYDelta() * distance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
