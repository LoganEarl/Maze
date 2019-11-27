package maze.model.question;

import java.util.stream.Stream;

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
    
    @Override
    public String toString() { 	
    	String name = this.name().replace("_", " ");
    	return Stream.of(name.split(" ")).map(w -> w.toUpperCase().charAt(0)+ w.toLowerCase().substring(1)).reduce((s, s2) -> s + " " + s2).orElse("");
    }
}
