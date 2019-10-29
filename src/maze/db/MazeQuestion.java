package maze.db;

import java.util.ArrayList;
import java.util.List;

public class MazeQuestion implements IQuestion {

	public String question;
	public List<String> answers = new ArrayList<String>();
	public int answerIndex;
	public List<String> keywords = new ArrayList<String>();
	public QuestionType type = QuestionType.MULTIPLE;
	
	
	@Override
	public QuestionType getType() {
		return this.type;
	}

	@Override
	public String getQuestion() {
		return this.question;
	}

	@Override
	public List<String> getAnswers() {
		
		return this.answers;
	}

	@Override
	public int getCorrectAnswerIndex() {
		return this.answerIndex;
	}

	@Override
	public List<String> getKeyWords() {
		return this.keywords;
	}
	
	public String toString()
	{
		return "Q: " + question +"\n  A: " + this.answers.get(answerIndex) + "\n";
	}

}
