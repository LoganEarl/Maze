package world.model;

public class World {
    private Room entryRoom;
    private Room exitRoom;
    private Player player;

    World(Room entryRoom, Room exitRoom) {
        this.entryRoom = entryRoom;
        this.exitRoom = exitRoom;
        this.player = new Player(entryRoom);
    }

    public boolean routeExists(){
        return false;
    }

    public interface Builder {
        World build();
    }
}
