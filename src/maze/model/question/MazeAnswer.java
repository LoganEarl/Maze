package maze.model.question;

public class MazeAnswer {
    public int id;
    public String answer;
    public boolean correct;

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
}
