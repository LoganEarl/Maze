package maze.model.question.sqlite;

import maze.model.Item;
import maze.model.question.DoorKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseManager;
import utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BooleanQuestionTest {
    private BooleanQuestion question1;
    private BooleanQuestion question2;

    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DatabaseManager.DATA_DIRECTORY + FILE_NAME;


    @BeforeEach
    void setup() {
        question1 = new BooleanQuestion(10, true, "The answer is true", "key10");
        question2 = new BooleanQuestion(11, false, "The answer is false", "key11");

        FileUtils.copyFile(new File(DatabaseManager.DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATABASE_FILE));
    }

    @Test
    void getID() {
        assert (question1.getID() == 10);
        assert (question2.getID() == 11);
    }

    @Test
    void getPrompt() {
        assert (question1.getPrompt().equals("The answer is true"));
        assert (question2.getPrompt().equals("The answer is false"));
    }

    @Test
    void getCorrectAnswer() {
        assert (question1.getCorrectAnswer().equals(BooleanQuestion.TEXT_TRUE));
        assert (question2.getCorrectAnswer().equals(BooleanQuestion.TEXT_FALSE));
    }

    @Test
    void getQuestionType() {
        assert (question1.getQuestionType().equals(BooleanQuestion.TYPE_BOOLEAN));
        assert (question2.getQuestionType().equals(BooleanQuestion.TYPE_BOOLEAN));
    }

    @Test
    void getPossibleAnswers() {
        List<String> answers1 = new ArrayList<>(Arrays.asList(question1.getPossibleAnswers()));
        assert (answers1.size() == 2);
        assert (answers1.contains(BooleanQuestion.TEXT_TRUE));
        assert (answers1.contains(BooleanQuestion.TEXT_FALSE));

        List<String> answers2 = new ArrayList<>(Arrays.asList(question2.getPossibleAnswers()));
        assert (answers2.size() == 2);
        assert (answers2.contains(BooleanQuestion.TEXT_TRUE));
        assert (answers2.contains(BooleanQuestion.TEXT_FALSE));
    }

    @Test
    void constructKeyItem() {
        assert (question1.constructKeyItem().equals(new DoorKey("key10")));
    }

    @Test
    void isCorrect() {
        assert question1.isCorrect(BooleanQuestion.TEXT_TRUE);
        assert !question1.isCorrect(BooleanQuestion.TEXT_FALSE);
        assert !question1.isCorrect("Terrrrruueeee!!");
        assert question2.isCorrect(BooleanQuestion.TEXT_FALSE);
        assert !question2.isCorrect(BooleanQuestion.TEXT_TRUE);
        assert !question2.isCorrect("Terrrrruueeee!!");
    }

    @Test
    void testIsCorrect() {
        Item item1 = question1.constructKeyItem();
        Item item2 = question2.constructKeyItem();
        assert (question1.isCorrect(item1));
        assert (!question1.isCorrect(item2));
        assert (question2.isCorrect(item2));
        assert (!question2.isCorrect(item1));
    }

    @Test
    void testDatabaseFunctions() {
        assert question1.updateInDatabase(FILE_NAME);
        assert question1.existsInDatabase(FILE_NAME);
        assert question1.removeFromDatabase(FILE_NAME);
        assert !question1.existsInDatabase(FILE_NAME);
    }
}