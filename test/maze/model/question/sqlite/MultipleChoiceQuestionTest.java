package maze.model.question.sqlite;

import maze.model.Item;
import maze.model.question.DoorKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseManager;
import utils.FileUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceQuestionTest {
    private MultipleChoiceQuestion[] questions = new MultipleChoiceQuestion[4];
    private String[] answers = {"a0", "a1", "a2", "a3"};
    private String[] keyNames = {"k0", "k1", "k2", "k3"};

    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DatabaseManager.DATA_DIRECTORY + FILE_NAME;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < questions.length; i++)
            questions[i] = new MultipleChoiceQuestion(i, answers[i], answers, "The answer is " + answers[i], keyNames[i]);

        FileUtils.copyFile(new File(DatabaseManager.DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATABASE_FILE));
    }

    @Test
    void getID() {
        for (int i = 0; i < questions.length; i++)
            assert questions[i].getID() == i;
    }

    @Test
    void getPrompt() {
        for (int i = 0; i < questions.length; i++) {
            assert questions[i].getPrompt().equals("The answer is " + answers[i]);
            assert !questions[i].getPrompt().equals("The answer is " + answers[(i + 1) % 4]);
        }
    }

    @Test
    void getCorrectAnswer() {
        for (int i = 0; i < questions.length; i++) {
            assert questions[i].getCorrectAnswer().equals(answers[i]);
            assert !questions[i].getCorrectAnswer().equals(answers[(i + 1) % 4]);
        }
    }

    @Test
    void getPossibleAnswers() {
        for (MultipleChoiceQuestion question : questions)
            assert question.getPossibleAnswers() == answers;
    }

    @Test
    void getQuestionType() {
        for (MultipleChoiceQuestion question : questions)
            assert question.getQuestionType().equals(MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE);
    }

    @Test
    void constructKeyItem() {
        for (int i = 0; i < questions.length; i++)
            assert (questions[i].constructKeyItem().equals(new DoorKey(keyNames[i])));
    }

    @Test
    void isCorrect() {
        for (int i = 0; i < questions.length; i++) {
            assert questions[i].isCorrect(answers[i]);
            assert !questions[i].isCorrect(answers[(i + 1) % 4]);
            assert !questions[i].isCorrect(answers[(i + 2) % 4]);
            assert !questions[i].isCorrect(answers[(i + 3) % 4]);
        }
    }

    @Test
    void testIsCorrect() {
        for (int i = 0; i < questions.length; i++) {
            Item item = questions[i].constructKeyItem();
            assert questions[i].isCorrect(item);
            assert !questions[(i + 1) % 4].isCorrect(item);
            assert !questions[(i + 2) % 4].isCorrect(item);
            assert !questions[(i + 3) % 4].isCorrect(item);
        }
    }

    @Test
    void testDatabaseFunctions() {
        for (MultipleChoiceQuestion q : questions) {
            assert q.updateInDatabase(FILE_NAME);
            assert q.existsInDatabase(FILE_NAME);
            assert q.removeFromDatabase(FILE_NAME);
            assert !q.existsInDatabase(FILE_NAME);
        }
    }
}