package maze.model.question;

public interface Answer {
	// gets record id of this answer
	int getId();

	// sets record id of this answer
	void setId(int id);

	// gets answer of this answer
	String getAnswer();

	// sets answer of this answer
	void setAnswer(String answer);

	// gets flag indicating if this answer is correct
	boolean getCorrect();

	// sets flag indicating if this record is correct
	void setCorrect(boolean correct);
}
