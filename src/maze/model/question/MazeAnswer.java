package maze.model.question;

public class MazeAnswer implements Answer {
	private int id;
	private String answer;
	private boolean correct;
	
	public MazeAnswer(int id, String answer, boolean correct) {
		this.id = id;
		this.answer = answer;
		this.correct = correct;
	}
	
	public MazeAnswer(String answer, boolean correct) {
		this(0, answer, correct);
	}
	
	public String toString() {
		return answer + "[" + (correct ? "CORRECT" : "WRONG") + "]";
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getAnswer() {
		return this.answer;
	}

	@Override
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public boolean getCorrect() {
		return this.correct;
	}

	@Override
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
