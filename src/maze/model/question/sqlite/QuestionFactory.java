package maze.model.question.sqlite;

import maze.model.question.Question;

import static maze.model.question.sqlite.BooleanQuestion.TYPE_BOOLEAN;
import static maze.model.question.sqlite.MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE;
import static maze.model.question.sqlite.ShortResponseQuestion.TYPE_SHORT_RESPONSE;

public class QuestionFactory {
    public Question createQuestion(int id, String prompt, String correctAnswer, String[] possibleAnswers, String itemName, String questionType){
        switch (questionType){
            case TYPE_MULTIPLE_CHOICE:
                return new MultipleChoiceQuestion(id,
                        correctAnswer,
                        possibleAnswers,
                        prompt,
                        itemName);
            case TYPE_BOOLEAN:
                return new BooleanQuestion(id,
                        correctAnswer.toLowerCase().equals(BooleanQuestion.TEXT_TRUE.toLowerCase()),
                        prompt,
                        itemName);
            case TYPE_SHORT_RESPONSE:
                return new ShortResponseQuestion(
                        id,
                        possibleAnswers,
                        prompt,
                        itemName);
        }
        throw new IllegalArgumentException("Incorrect question type:" + questionType);
    }
}
