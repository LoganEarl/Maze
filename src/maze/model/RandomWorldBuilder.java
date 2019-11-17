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
        rnd = new Random(randomSeed);
        this.numRooms = numRooms;
        this.questions = questions;
        this.maxCorridorLength = maxCorridorLength;
    }

    @Override
    public World build() {
        //phase 1 generate rooms
        Map<Point, TempRoom> rooms = new HashMap<>();
        generateRooms(numRooms, new Point(0, 0), null, rooms, 0);


        return null;
    }

    private Pair<TempRoom> getFurthestApart(List<TempRoom> allRooms) {

    }

    //returns the unused allocation
    private int generateRooms(int roomAllocation, Point roomPos, TempRoom lastRoom, Map<Point, TempRoom> existingRooms, int jumpsFromCenter) {
        if (roomAllocation > 0) {
            roomAllocation--;
            TempRoom newRoom = new TempRoom(roomPos, jumpsFromCenter);
            existingRooms.put(roomPos, newRoom);
            if (lastRoom != null) {
                Direction toLastRoom = getDirection(roomPos, lastRoom.getPosition());
                lastRoom.setRoomConnection(toLastRoom, lastRoom, toLastRoom.opposite(), expendQuestion());
            }
            Map<Direction, Integer> potentialDirections = getPotentialDirections(newRoom, existingRooms, maxCorridorLength);
            List<Direction> toExplore = new ArrayList<>(potentialDirections.keySet());
            Collections.shuffle(toExplore, rnd);
            int[] allocations = allocateSum(roomAllocation, toExplore.size(), rnd);
            int amountRemaining = 0;
            for (int i = 0; i < allocations.length; i++) {
                Direction d = toExplore.get(i);
                int distance = rnd.nextInt(potentialDirections.get(d) + 1);
                amountRemaining += generateRooms(
                        allocations[i] + amountRemaining,
                        roomPos.getAdjacent(d, distance),
                        newRoom,
                        existingRooms,
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

    private static Map<Direction, Integer> getPotentialDirections(TempRoom sourceRoom, Map<Point, TempRoom> allRooms, int maxCorridor) {
        Map<Direction, Integer> potentialDirections = new HashMap<>();
        for (Direction direction : Direction.values()) {
            int distance = 0;
            if (sourceRoom.getDoor(direction) != null) {
                for (Point walker = sourceRoom.getPosition().getAdjacent(direction);
                     !allRooms.containsKey(walker) && distance <= maxCorridor;
                     walker = walker.getAdjacent(direction), distance++)
                    ;
                //consider not allowing crossing corridors
            }
            if (distance > 0)
                potentialDirections.put(direction, distance);
        }
        return potentialDirections;
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
