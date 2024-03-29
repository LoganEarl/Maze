package maze.model;

import maze.Direction;
import maze.model.question.Question;
import maze.model.question.SkeletonKey;
import maze.model.question.TestingQuestions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private World testWorld;
    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testWorld = new StubbedWorldBuilder().build();
        testPlayer = testWorld.getPlayer();
    }

    @AfterEach
    void tearDown() {
        testWorld = null;
        testPlayer = null;
    }

    @Test
    void move() {
        testPlayer.getCurrentRoom().getDoor(Direction.north).lock();
        if ((testPlayer.move(Direction.north)))
            assert false;
        testPlayer.getCurrentRoom().getDoor(Direction.north).open();
        if(!testPlayer.move(Direction.north))
            assert false;
        Room starterRoom = testWorld.getEntryRoom();
        assert(testPlayer.getCurrentRoom() == starterRoom.getDoor(Direction.north).getOtherRoom(starterRoom));
    }

    @Test
    void forceMove() {
        Room starterRoom = testWorld.getEntryRoom();
        Room secondRoom = starterRoom.getDoor(Direction.north).getOtherRoom(starterRoom);
        testPlayer.forceMove(Direction.north);
        assert(testPlayer.getCurrentRoom() == secondRoom);
        secondRoom.getDoor(Direction.south).lock();
        testPlayer.forceMove(Direction.south);
        assert(testPlayer.getCurrentRoom() ==starterRoom);
    }

    @Test
    void getCurrentRoom() {
        Room starterRoom = testWorld.getEntryRoom();
        Room secondRoom = starterRoom.getDoor(Direction.north).getOtherRoom(starterRoom);
        assert(testPlayer.getCurrentRoom() == starterRoom);
        testPlayer.forceMove(Direction.north);
        assert(testPlayer.getCurrentRoom() == secondRoom);
    }

    @Test
    void items() {
        assert(testPlayer.getItems().size() == 0);
        Item i0 = TestingQuestions.questions[0].constructKeyItem();
        Item i1 = TestingQuestions.questions[1].constructKeyItem();
        testPlayer.addItem(i0);
        testPlayer.addItem(i1);
        assert(testPlayer.getItems().contains(i0));
        testPlayer.removeItem(i0);
        assert(!testPlayer.getItems().contains(i0));
        assert(testPlayer.getItems().contains(i1));
    }
}