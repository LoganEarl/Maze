package world.model;

import world.Direction;

public class Player {
    private Room currentRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
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
}
