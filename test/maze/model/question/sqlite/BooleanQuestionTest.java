package maze.model.question.sqlite;

import maze.model.question.DoorKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BooleanQuestionTest {
    private BooleanQuestion question1;
    private BooleanQuestion question2;

    @BeforeEach
    void setup(){
        question1 = new BooleanQuestion(10, true, "The answer is true", "key10");
        question2 = new BooleanQuestion(11, false, "The answer is false", "key11");
    }

    @Test
    void getID() {
        assert(question1.getID() == 10);
        assert(question2.getID() == 11);
    }

    @Test
    void getPrompt() {
        assert(question1.getPrompt().equals("The answer is true"));
        assert(question2.getPrompt().equals("The answer is false"));
    }

    @Test
    void getCorrectAnswer() {
        assert(question1.getCorrectAnswer().equals(BooleanQuestion.TEXT_TRUE));
        assert(question2.getCorrectAnswer().equals(BooleanQuestion.TEXT_FALSE));
    }

    @Test
    void getQuestionType() {
        assert(question1.getQuestionType().equals(BooleanQuestion.TYPE_BOOLEAN));
        assert(question2.getQuestionType().equals(BooleanQuestion.TYPE_BOOLEAN));
    }

    @Test
    void getPossibleAnswers() {
        List<String> answers1 = new ArrayList<>(List.of(question1.getPossibleAnswers()));
        assert(answers1.size() == 2);
        assert(answers1.contains(BooleanQuestion.TEXT_TRUE));
        assert(answers1.contains(BooleanQuestion.TEXT_FALSE));

        List<String> answers2 = new ArrayList<>(List.of(question2.getPossibleAnswers()));
        assert(answers2.size() == 2);
        assert(answers2.contains(BooleanQuestion.TEXT_TRUE));
        assert(answers2.contains(BooleanQuestion.TEXT_FALSE));
    }

    @Test
    void constructKeyItem() {
        assert(question1.constructKeyItem().equals(new DoorKey("key10")));
    }

    @Test
    void isCorrect() {
    }

    @Test
    void testIsCorrect() {
    }

    @Test
    void removeFromDatabase() {
    }

    @Test
    void updateInDatabase() {
    }

    @Test
    void existsInDatabase() {
    }
}