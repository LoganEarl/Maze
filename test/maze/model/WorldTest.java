package maze.model;

import maze.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static maze.model.question.Question.SKELETON_KEY;
import static maze.model.question.Question.STUBBED_ITEM;
import static org.junit.jupiter.api.Assertions.*;

class WorldTest {
    private World world;

    @BeforeEach
    void setUp() {
        world = new StarterWorldBuilder().build();
    }

    //assumes that the player is working properly
    @Test
    void currentRouteExists() {
        assert(world.currentRouteExists());
        assert(world.baseRouteExists());
        Room curRoom;

        world.getPlayer().forceMove(Direction.north);
        assert(world.getPlayer().getCurrentRoom() != world.getEntryRoom());
        assert(world.currentRouteExists());
        curRoom = world.getPlayer().getCurrentRoom();
        curRoom.getDoor(Direction.east).lock();
        assert(!world.currentRouteExists());
        curRoom.addItem(STUBBED_ITEM);
        assert(world.currentRouteExists());

        curRoom = curRoom.getDoor(Direction.east).getOtherRoom(curRoom);
        curRoom.getDoor(Direction.south).lock();
        assert(!world.currentRouteExists());
        world.getPlayer().getCurrentRoom().addItem(SKELETON_KEY);
        assert(world.currentRouteExists());
    }

    @Test
    void baseRouteExists() {
        assert(world.baseRouteExists());
        world.getEntryRoom().getDoor(Direction.north).lock();
        assert(!world.baseRouteExists());
        world.getEntryRoom().addItem(STUBBED_ITEM);
        assert(world.baseRouteExists());
    }
}