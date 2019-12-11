package maze.model.question;

import java.util.*;

import static maze.model.question.TestingQuestions.questions;

public class StubbedDataSource implements QuestionDataSource {
    @Override
    public Set<Question> getAllQuestions() {
        return new HashSet<>(Arrays.asList(questions));
    }

    @Override
    public Question getQuestionWithID(int questionID) {
        for(Question q: questions) {
            if(q.getID() == questionID)
                return q;
        }
        return null;
    }

    @Override
    public int getNextQuestionID() {
        return questions.length;
    }

    @Override
    public boolean exists(String itemName) {
        return false;
    }

    @Override
    public void update(Question q) {
        //do nothing
    }

    @Override
    public void delete(Question q) {
        //do nothing
    }

    @Override
    public boolean exists(Question q) {
        return false;
    }

    @Override
    public java.util.Iterator<Question> iterator() {
        return new Iterator(Arrays.asList(questions));
    }
}
