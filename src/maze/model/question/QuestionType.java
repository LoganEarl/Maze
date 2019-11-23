package maze.model.question;

public enum QuestionType {
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
