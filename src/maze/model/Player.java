package maze.model;

import maze.Direction;

import java.util.HashSet;
import java.util.Set;

public class Player {
    private Room currentRoom;
    private Set<Item> items;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.items = new HashSet<>();
    }

    public boolean canReachRoom(Room destination){
        return false;
    }

    public boolean move(Direction direction){
        return false;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Set<Item> getItems() {
        return items;
    }
}
