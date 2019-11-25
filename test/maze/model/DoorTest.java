package maze.model;

import maze.Direction;
import maze.model.question.Question;
import maze.model.question.TestingQuestions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Pair;

import static maze.model.question.TestingQuestions.*;

class DoorTest {
    private Room testRoom1;
    private Room testRoom2;
    private Door testDoor;

    @BeforeEach
    void setUp() {
        testRoom1 = new Room(0,0);
        testRoom2 = new Room(1,0);
        testDoor = testRoom1.setRoomConnection(Direction.east,testRoom2,Direction.west, questions[0]);
    }

    @Test
    void getOtherRoom() {
        assert(testDoor.getOtherRoom(testRoom1) == testRoom2);
        assert(testDoor.getOtherRoom(testRoom2) == testRoom1);
        assert(testDoor.getOtherRoom(new Room(1,1)) == testRoom1);
    }

    @Test
    void getConnectedRooms() {
        assert(testDoor.getConnectedRooms().equals(new Pair<>(testRoom1, testRoom2)));
    }

    @Test
    void getQuestion() {
        assert(testDoor.getQuestion() == questions[0]);
    }

    @Test
    void isLocked() {
        assert(!testDoor.isLocked());
        testDoor.lock();
        assert(testDoor.isLocked());
    }

    @Test
    void isOpen() {
        assert(!testDoor.isOpen());
        testDoor.open();
        assert(testDoor.isOpen());
    }
}