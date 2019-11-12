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


interface Answer
{
	// gets record id of this answer
	int getId();
	//sets record id of this answer
	void setId(int id);
	//gets answer of this answer
	String getAnswer();
	//sets answer of this answer
	void setAnswer(String answer);
	// gets flag indicating if this answer is correct
	boolean getCorrect();
	//sets flag indicating if this record is correct
	void setCorrect(boolean correct);
}

enum QuestionType {
	MULTIPLE(0),
	TRUE_FALSE(1),
	SHORT(2);
	
	private final int value;
    private QuestionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
