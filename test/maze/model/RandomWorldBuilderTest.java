package maze.model;

import maze.model.question.Question;
import maze.model.question.sqlite.SQLiteQuestionDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileUtils.DATA_DIRECTORY;

class RandomWorldBuilderTest {
    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DATA_DIRECTORY + FILE_NAME;

    private Set<Question> questions;

    @BeforeEach
    void setUp() {
        FileUtils.copyFile(new File(DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
        SQLiteQuestionDataSource dataSource = new SQLiteQuestionDataSource(FILE_NAME);
        questions = dataSource.getAllQuestions();
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATABASE_FILE));
    }

    @Test
    void buildBaseTest() {
        World builtWorld = new RandomWorldBuilder(2, questions, 2, 1111).build();
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
        for(int corridorLen = 0; corridorLen <= 5; corridorLen++) {
            for (int seed = 0; seed < 1000; seed++) {
                for (int i = 2; i < questions.size() / 1.5; i++) {
                    System.out.printf("Testing Seed:%d Length:%d Rooms:%d\n",seed, corridorLen, i);
                    World builtWorld = null;
                    try {
                        builtWorld = new RandomWorldBuilder(i, questions, corridorLen, seed).build();
                    }catch (IllegalArgumentException e){
                        assert false;
                    }
                    assert (builtWorld != null);
                    assert (builtWorld.getEntryRoom() != null);
                    assert (builtWorld.getExitRoom() != null);
                    assert (builtWorld.getEntryRoom() != builtWorld.getExitRoom());
                    assert (builtWorld.getPlayer() != null);
                    assert (builtWorld.getPlayer().getCurrentRoom() == builtWorld.getEntryRoom());
                    assert (builtWorld.baseRouteExists());
                    //assert (builtWorld.getAllRooms().size() <= i);
                }
            }
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