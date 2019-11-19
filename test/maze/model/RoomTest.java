package maze.model;

import maze.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static maze.model.question.Question.STUBBED_ITEM;
import static maze.model.question.Question.STUBBED_QUESTION;

class RoomTest {
    private Room testRoom1;
    private Room testRoom2;

    @BeforeEach
    void setUp(){
        testRoom1 = new Room(0,0);
        testRoom2 = new Room(1,0);
    }

    @Test
    void setRoomConnection() {
        assert(!testRoom1.hasDoor(Direction.east));
        Door testDoor = testRoom1.setRoomConnection(Direction.east,testRoom2, Direction.west, STUBBED_QUESTION);
        assert(testRoom1.hasDoor(Direction.east));
        assert(testRoom1.getDoor((Direction.east)) == testDoor);
        assert(testRoom2.getDoor((Direction.west)) == testDoor);
        assert(testDoor.getQuestion() == STUBBED_QUESTION);
    }

    @Test
    void addItem() {
        assert(testRoom1.getItems().size() == 0);
        testRoom1.addItem(STUBBED_ITEM);
        assert(testRoom1.getItems().size() == 1);
        assert(testRoom1.getItems().contains(STUBBED_ITEM));
    }

    @Test
    void hasDoor() {
    }

    @Test
    void getDoors() {
        assert(testRoom1.getDoors().isEmpty());
        Door testDoor = testRoom1.setRoomConnection(Direction.east,testRoom2, Direction.west, STUBBED_QUESTION);
        assert(testRoom1.getDoors().contains(testDoor));
    }

    @Test
    void getXCoordinate() {
        assert(testRoom1.getXCoordinate() == 0);
        assert(testRoom2.getXCoordinate() == 1);
    }

    @Test
    void getYCoordinate() {
        assert(testRoom1.getYCoordinate() == 0);
        assert(testRoom2.getYCoordinate() == 0);
    }
}