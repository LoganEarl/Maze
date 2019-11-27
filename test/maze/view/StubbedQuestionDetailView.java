package maze.view;

import maze.model.question.Question;
import maze.model.question.StubbedQuestion;

import static maze.model.question.TestingQuestions.questions;

public class StubbedQuestionDetailView implements View.QuestionDetailView {
    @Override
    public Question getQuestion() {
        return questions[0];
    }

    @Override
    public void setQuestion(Question question) {
        //do nothing
    }
}
