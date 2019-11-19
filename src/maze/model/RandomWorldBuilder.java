package maze.model;

import maze.Direction;
import maze.model.question.Question;
import utils.Pair;

import java.util.*;

import static maze.Direction.*;

public class RandomWorldBuilder implements World.Builder {
    private int numRooms;
    private Random rnd;
    private List<Question> questions;
    private int maxCorridorLength;

    public RandomWorldBuilder(int numRooms, List<Question> questions, int maxCorridorLength, long randomSeed) {
        if(questions.size() < numRooms * 1.5)
            throw new IllegalArgumentException("Not enough questions");
        rnd = new Random(randomSeed);
        this.numRooms = numRooms;
        this.questions = questions;
        this.maxCorridorLength = maxCorridorLength;
    }

    @Override
    public World build() {
        //phase 1 generate rooms
        Map<Point, TempRoom> rooms = new HashMap<>();
        generateRooms(numRooms, new Point(0, 0), null, rooms, new HashSet<>(), 0);

        //phase 2 make start and end
        Room startRoom, endRoom;
        TempRoom[] tempArray = rooms.values().toArray(new TempRoom[0]);
        Pair<TempRoom> furthestApart = new Pair<>(tempArray[0], tempArray[1]);
        //Pair<TempRoom> furthestApart = getFurthestApart(new ArrayList<>(rooms.values()));
        startRoom = furthestApart.itemA();
        endRoom = furthestApart.itemB();

        //phase 3 mark side rooms


        //phase 4 place conventional items

        //phase 5 place sk

        return new World(startRoom, endRoom);
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
            Set<Point> reserved,
            int jumpsFromCenter) {
        if (roomAllocation > 0) {
            roomAllocation--;
            TempRoom newRoom = new TempRoom(roomPos, jumpsFromCenter);
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
                if(allocations[i] > 0) {
                    Direction d = toExplore.get(i);
                    distances[i] = rnd.nextInt(potentialDirections.get(d)) + 1;
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
                        reserved,
                        jumpsFromCenter + 1);
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
        Map<Direction, Integer> potentialDirections = new HashMap<>();
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
            }else
                roomDistance = 0;

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
        private int jumpsFromCenter;

        private TempRoom(Point p, int jumpsFromCenter) {
            super(p.x, p.y);
            this.jumpsFromCenter = jumpsFromCenter;
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
            return south;
        return north;
    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private double distanceTo(Point p){
            return Math.sqrt(Math.pow(x-p.x,2) + Math.pow(y-p.y,2));
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
