package maze.model;

import maze.Direction;

import java.util.*;

public class Algorithms {
    @SuppressWarnings("unchecked")
    public static <T extends Room> Set<T> floodFill(T startRoom, Direction forbiddenStartDirection){
        Set<T> foundRooms = new LinkedHashSet<>();
        Queue<T> toExplore = new LinkedList<>();
        toExplore.add(startRoom);
        while(!toExplore.isEmpty()){
            T current = toExplore.remove();
            foundRooms.add(current);
            for(Door d: current) {
                T nextRoom = (T)d.getOtherRoom(current);
                if ((current != startRoom || current.getDoor(forbiddenStartDirection) != d) && !foundRooms.contains(nextRoom))
                    toExplore.add(nextRoom);
            }
        }
        return foundRooms;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Room> List<Direction> aStar(T source, T destination) {
        Map<T, List<Direction>> shortestPaths = new HashMap<>();
        Queue<T> toExplore = new PriorityQueue<>(20, new Comparator<>() {
            @Override
            public int compare(T o1, T o2) {
                return distance(o1,destination).compareTo(distance(o2,destination));
            }

            private Double distance(T o1, T o2) {
                return Math.sqrt(Math.pow(o1.getXCoordinate() - o2.getXCoordinate(), 2) +
                                Math.pow(o1.getYCoordinate() - o2.getYCoordinate(), 2));
            }
        });
        shortestPaths.put(source, Collections.emptyList());
        toExplore.add(source);

        while (!toExplore.isEmpty()) {
            Room currentRoom = toExplore.remove();
            List<Direction> myPath = shortestPaths.get(currentRoom);
            for (Direction d : currentRoom.getPossibleDirections()) {
                T nextRoom = (T)currentRoom.getDoor(d).getOtherRoom(currentRoom);
                if (!shortestPaths.containsKey(nextRoom) ||
                        (shortestPaths.get(nextRoom).size() > myPath.size() + 1) &&
                                (!shortestPaths.containsKey(destination) ||
                                        myPath.size() + 1 < shortestPaths.get(destination).size())) {
                    toExplore.add(nextRoom);
                    List<Direction> nextPath = new ArrayList<>(myPath);
                    nextPath.add(d);
                    shortestPaths.put(nextRoom, nextPath);
                }
            }
        }
        return shortestPaths.get(destination);
    }
}
