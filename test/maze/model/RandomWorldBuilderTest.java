package maze.model;

import maze.model.question.DatabaseManager;
import maze.model.question.MazeDatabase;
import maze.model.question.Question;
import maze.model.question.SqLiteDatabase;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomWorldBuilderTest {
    private List<Question> questions;

    RandomWorldBuilderTest() {
        MazeDatabase db = DatabaseManager.openDatabase("data"); // new SqLiteDatabase("data/mazedb.sqlite3");
        questions = db.readAllRecords();
    }

    @Test
    void buildBaseTest() {
        World builtWorld = new RandomWorldBuilder(2, questions, 0, 1111).build();
        assert(builtWorld != null);
        assert(builtWorld.getEntryRoom() != null);
        assert(builtWorld.getExitRoom() != null);
        assert(builtWorld.getEntryRoom() != builtWorld.getExitRoom());
        assert(builtWorld.getPlayer() != null);
        assert(builtWorld.getPlayer().getCurrentRoom() == builtWorld.getEntryRoom());
        assert(builtWorld.baseRouteExists());
        assert(builtWorld.getAllRooms().size() == 2);
    }

    @Test
    void buildComplexTest(){
        Random r = new Random(System.currentTimeMillis());
        for(int i = 2; i < questions.size() / 1.5; i++){
            World builtWorld = new RandomWorldBuilder(2, questions, r.nextInt(5), r.nextLong()).build();
            assert(builtWorld != null);
            assert(builtWorld.getEntryRoom() != null);
            assert(builtWorld.getExitRoom() != null);
            assert(builtWorld.getEntryRoom() != builtWorld.getExitRoom());
            assert(builtWorld.getPlayer() != null);
            assert(builtWorld.getPlayer().getCurrentRoom() == builtWorld.getEntryRoom());
            assert(builtWorld.baseRouteExists());
            assert(builtWorld.getAllRooms().size() <= i);
        }
    }

    @Test
    void textExpectedExceptions(){
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(0, questions, 0, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(1, questions, 0, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(30000, questions, 0, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(2, questions, -1, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(2, null, -1, 1111));
    }
}