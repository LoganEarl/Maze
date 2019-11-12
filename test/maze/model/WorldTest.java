package maze.model;

import maze.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static maze.model.question.Question.SKELETON_KEY;
import static maze.model.question.Question.STUBBED_ITEM;

class WorldTest {
    private World world;

    @BeforeEach
    void setUp() {
        world = new StubbedStaticWorldBuilder().build();
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

    @Test
    void getAllRooms() {
        Set<Room> visited = new HashSet<>();
        LinkedList<Room> toExplore = new LinkedList<>();
        toExplore.add(world.getEntryRoom());
        toExplore.add(world.getExitRoom());

        while(!toExplore.isEmpty()){
            Room cur = toExplore.pop();
            visited.add(cur);
            for(Door d: cur) {
                Room next = d.getOtherRoom(cur);
                if (!visited.contains(next))
                    toExplore.add(next);
            }
        }

        assert(world.getAllRooms().containsAll(visited));
    }
}