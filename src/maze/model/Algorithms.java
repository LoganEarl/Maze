package maze.model;

import maze.Direction;
import maze.model.Room;

import java.util.*;

public class Algorithms {
    public static List<Direction> aStar(Room source, Room destination) {
        Map<Room, List<Direction>> shortestPaths = new HashMap<>();
        Queue<Room> toExplore = new PriorityQueue<>(20, new Comparator<>() {
            @Override
            public int compare(Room o1, Room o2) {
                return distance(o1,destination).compareTo(distance(o2,destination));
            }

            private Double distance(Room o1, Room o2) {
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
                Room nextRoom = currentRoom.getDoor(d).getOtherRoom(currentRoom);
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
