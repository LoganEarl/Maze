package maze.model.question;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class MazeDBDriver {

	public static void driver(String[] args) throws Exception {

		// MazeDatabase db = new TextDatabase("QuestionDb.txt");

		// System.out.println("IMazeDB of type: " + db.getDbType());
		/*
		 * System.out.println("List elements : ");
		 * 
		 * for (Question q: db.readAllRecords()) System.out.println(q + " ");
		 */

		// MazeDatabase db = DatabaseManager.openDatabase("mazeApp");

		/*
		 * MazeQuestion q = new MazeQuestion();
		 * 
		 * q.question = "How are you?4"; q.answers.add(new MazeAnswer("Good", true));
		 * q.answers.add(new MazeAnswer("Bad", false)); q.answers.add(new
		 * MazeAnswer("whatever", false)); q = db.add(q);
		 * 
		 * if(q != null) { System.out.println(q.id); }
		 */

		MazeDatabase db = DatabaseManager.createDatabaseWithDefaultQuestions("mazeApp");
		List<Question> list = db.readAllRecords();

		for (Question g : list) {
			System.out.println(g);
		}

		// ImportDefault(db);
	}

	static void ImportDefault(MazeDatabase db) throws FileNotFoundException, Exception {
		QuestionImporter defaults = QuestionImporter.getDefaultQuestions();

		for (Question q : defaults.getQuestions()) {
			if (db.insert(q))
				System.out.println("Inserted: " + q);
			else
				System.out.println("Existed: " + q);

		}

	}

}
