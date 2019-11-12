package maze.model;

import maze.Direction;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class Player {
    private Room currentRoom;
    private Set<Item> items;
    private Direction direction = Direction.north;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.items = new HashSet<>();
    }

    //moves the player only if the door in that direction is open
    public boolean move(Direction direction){
        Door toMove = currentRoom.getDoor(direction);
        if(toMove != null && toMove.isOpen()){
            currentRoom = toMove.getOtherRoom(currentRoom);
            return true;
        }
        return false;
    }

    //will move the player regardless of obstacles
    public void forceMove(Direction direction){
        Door toMove = currentRoom.getDoor(direction);
        if(toMove != null) currentRoom = toMove.getOtherRoom(currentRoom);
    }

    public void setFacing(Direction direction){
        this.direction = direction;
    }

    public Direction getFacing(){
        return direction;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}
