package maze.model;

import maze.Direction;
import maze.model.question.Question;
import utils.Pair;
import java.util.*;

@SuppressWarnings("WeakerAccess")
public class Room implements Iterable<Door>{
    private int xCoordinate;
    private int yCoordinate;
    private Set<Item> items;

    private Map<Direction,Door> doors;

    public Room(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        items = new HashSet<>();
        doors = new HashMap<>();
    }

    //makes a new connection between this room and another room. Will not overwrite existing door connections
    Door setRoomConnection(Direction entryDirection, Room targetRoom, Direction exitDirection, Question question) throws IllegalArgumentException{
        if(doors.containsKey(entryDirection) || targetRoom.doors.containsKey(exitDirection))
            throw new IllegalArgumentException("There is already a room connection using those exists");
        Door newDoor = new Door(new Pair<>(this, targetRoom), question, question.constructKeyItem());
        doors.put(entryDirection, newDoor);
        targetRoom.doors.put(exitDirection, newDoor);
        return newDoor;
    }

    void addItem(Item item){
        items.add(item);
    }

    public Set<Item> getItems() {
        return items;
    }

    public boolean hasDoor(Direction doorDirection){
        return doors.containsKey(doorDirection);
    }

    public Door getDoor(Direction direction){
        if(doors.containsKey(direction))
            return doors.get(direction);
        return null;
    }

    public Collection<Door> getDoors(){
        return doors.values();
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    @Override
    public Iterator<Door> iterator() {
        return new DoorIterator();
    }

    private class DoorIterator implements Iterator<Door> {
        private Iterator<Direction> directionIterator;

        private DoorIterator(){
            directionIterator = doors.keySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return directionIterator.hasNext();
        }

        @Override
        public Door next() {
            return doors.get(directionIterator.next());
        }
    }
}
