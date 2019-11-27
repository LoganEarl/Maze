package maze.model.question.sqlite;

import maze.model.Item;
import maze.model.question.DoorKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.File;

import static utils.FileUtils.DATA_DIRECTORY;

class ShortResponseQuestionTest {
    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DATA_DIRECTORY + FILE_NAME;

    private ShortResponseQuestion question;
    private static final String[] answers = new String[]{"key0", "key2", "key4"};

    @BeforeEach
    void setUp() {
        question = new ShortResponseQuestion(42, answers, "The keywords are key0, key2, and key4", "keyItem");

        FileUtils.copyFile(new File(DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATABASE_FILE));
    }

    @Test
    void getID() {
        assert question.getID() == 42;
    }

    @Test
    void getPrompt() {
        assert question.getPrompt().equals("The keywords are key0, key2, and key4");
    }

    @Test
    void getCorrectAnswer() {
        assert question.getCorrectAnswer().equals("key0");
    }

    @Test
    void getPossibleAnswers() {
        assert question.getPossibleAnswers().length == 0;
    }

    @Test
    void getQuestionType() {
        assert question.getQuestionType().equals(ShortResponseQuestion.TYPE_SHORT_RESPONSE);
    }

    @Test
    void constructKeyItem() {
        assert question.constructKeyItem().equals(new DoorKey("keyItem"));
    }

    @Test
    void isCorrect() {
        assert question.isCorrect("hello there this is the key0");
        assert !question.isCorrect("hello there this is thekey0");
        assert question.isCorrect("hello there this is the key2");
        assert !question.isCorrect("hello there this is thekey2");
        assert question.isCorrect("hello there this is the key4");
        assert !question.isCorrect("hello there this is thekey4");

        assert question.isCorrect("key0");
        assert question.isCorrect("key2");
        assert question.isCorrect("key4");

        assert question.isCorrect("key0 hello there");
        assert question.isCorrect("key2 hello there");
        assert question.isCorrect("key4 hello there");

        assert question.isCorrect("key0 key2 key4");
    }

    @Test
    void testIsCorrect() {
        Item i = question.constructKeyItem();
        assert question.isCorrect(i);
        assert !question.isCorrect(new DoorKey("blah blah"));
    }

    @Test
    void testDatabaseFunctions() {
        assert question.updateInDatabase(FILE_NAME);
        assert question.existsInDatabase(FILE_NAME);
        assert question.removeFromDatabase(FILE_NAME);
        assert !question.existsInDatabase(FILE_NAME);
    }
}