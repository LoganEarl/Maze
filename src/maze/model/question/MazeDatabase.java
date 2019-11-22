package maze.model.question;

import java.util.List;
import java.util.Random;

public interface MazeDatabase {

	Question getNextQuestion(Random rand);

	List<Question> readAllRecords();

	boolean insert(Question q);

	boolean delete(int questionId);

	boolean update(Question q);

}