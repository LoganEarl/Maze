package maze.model.question;

import maze.model.Item;

public interface Question {
    int getID();
    String getPrompt();
    String getCorrectAnswer();
    String[] getPossibleAnswers();
    String getQuestionType();
    Item constructKeyItem();
    boolean isCorrect(String answer);
    boolean isCorrect(Item key);
}
