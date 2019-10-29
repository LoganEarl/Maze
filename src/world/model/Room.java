package world.model;

import utils.Pair;
import world.Direction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private int xCoordinate;
    private int yCoordinate;

    private Map<Direction,Door> doors;

    public Room(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        doors = new HashMap<>();
    }

    //makes a new connection between this room and another room
    public Door setRoomConnection(Direction entryDirection, Room targetRoom, Direction exitDirection){
        return null;
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

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }
}
