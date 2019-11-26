package maze.model;

import maze.model.question.Question;
import maze.model.question.sqlite.SQLiteQuestionDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseManager;
import utils.FileUtils;

import java.io.File;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RandomWorldBuilderTest {
    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DatabaseManager.DATA_DIRECTORY + FILE_NAME;

    private Set<Question> questions;

    @BeforeEach
    void setUp() {
        FileUtils.copyFile(new File(DatabaseManager.DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
        SQLiteQuestionDataSource dataSource = new SQLiteQuestionDataSource(FILE_NAME);
        questions = dataSource.getAllQuestions();
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATABASE_FILE));
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
        for(int seed = 0; seed < 1000; seed++) {
            for (int i = 2; i < questions.size() / 1.5; i++) {
                World builtWorld = new RandomWorldBuilder(2, questions, i%5, seed).build();
                assert (builtWorld != null);
                assert (builtWorld.getEntryRoom() != null);
                assert (builtWorld.getExitRoom() != null);
                assert (builtWorld.getEntryRoom() != builtWorld.getExitRoom());
                assert (builtWorld.getPlayer() != null);
                assert (builtWorld.getPlayer().getCurrentRoom() == builtWorld.getEntryRoom());
                assert (builtWorld.baseRouteExists());
                assert (builtWorld.getAllRooms().size() <= i);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void textExpectedExceptions(){
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(0, questions, 0, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(1, questions, 0, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(30000, questions, 0, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(2, questions, -1, 1111));
        assertThrows(IllegalArgumentException.class, ()-> new RandomWorldBuilder(2, null, -1, 1111));
    }
}