package maze.model;

import maze.Direction;
import maze.model.question.SkeletonKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static maze.Direction.*;

class WorldTest {
    private World world;

    @BeforeEach
    void setUp() {
        world = new StubbedWorldBuilder().build();
    }

    //assumes that the player is working properly
    @Test
    void currentRouteExists() {
        assert(world.currentRouteExists());
        assert(world.baseRouteExists());
        Room curRoom;

        world.getPlayer().forceMove(north);
        assert(world.getPlayer().getCurrentRoom() != world.getEntryRoom());
        assert(world.currentRouteExists());
        curRoom = world.getPlayer().getCurrentRoom();
        curRoom.getDoor(east).lock();

        assert(!world.currentRouteExists());
        curRoom.addItem(curRoom.getDoor(east).getQuestion().constructKeyItem());
        assert(world.currentRouteExists());

        curRoom = curRoom.getDoor(east).getOtherRoom(curRoom);
        curRoom.getDoor(south).lock();
        assert(!world.currentRouteExists());
        world.getPlayer().getCurrentRoom().addItem(new SkeletonKey());
        assert(world.currentRouteExists());
    }

    @Test
    void baseRouteExists() {
        assert(world.baseRouteExists());
        world.getEntryRoom().getDoor(north).lock();
        assert(!world.baseRouteExists());
        world.getEntryRoom().addItem(world.getEntryRoom().getDoor(north).getQuestion().constructKeyItem());
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