package maze.model.question;

import java.util.ArrayList;
import java.util.List;

import maze.model.Item;

public interface Question {
    boolean isCorrect(String answer);
    boolean isCorrect(Item keyItem);
    QuestionInfo getInfo();
    String getCorrectAnswer();
    Item constructKeyItem();
    
    
    //-- sveta's particulars --
    void setId(int id);
    int getId();
    String getQuestion();
    List<Answer> getAnswers();
    List<String> getKeywords();
    QuestionType getType();  
    

    interface QuestionInfo {
        //Or something to the effect of this.
        String getPromptText();
        String getQuestionType();
    }

}
