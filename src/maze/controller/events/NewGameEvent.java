package maze.controller.events;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
import maze.model.question.DatabaseManager;
import maze.model.question.MazeDatabase;
import maze.model.question.Question;
import maze.model.question.SqLiteDatabase;
import maze.view.MainFrame;
import maze.view.Panel;
import maze.model.Player;
import maze.model.StubbedStaticWorldBuilder;
import maze.model.World;

import java.util.List;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, Player player, World world) {
		mainFrame.switchToPanel(Panel.LOADING);
		
		// SPK: If this variable set to true, every time database is recreated
		// from the predefined questions. It is handy especially during development,
		// because when we add more questions, modify or correct them, they are immediately
		// available in the gave. When we became more stable, we can think of some
		// other optimization if necessary.
		boolean      recreateDatabase = false;
		MazeDatabase db               = null;
		
		// SPK: Database manager requires directory name and then automatically
		// appends the db file name: "mazedb.sqlite3"
		String appDir = "data";

		if( recreateDatabase ) {
			// SPK: The following method does the following:
			// 	1. Deletes database if exists
			// 	2. Creates database
			// 	3. Imports defaults question set
			try {
				db = DatabaseManager.createDatabaseWithDefaultQuestions(appDir);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				// Just in case...
				db = DatabaseManager.openDatabase(appDir); 
			}
			
		} else {
			// SPK: the open database will still create database if it does not exists,
			// but is not going to populate it with question.
			db = DatabaseManager.openDatabase(appDir); 
		}

		List<Question> questions = db.readAllRecords();
		World.Builder worldBuilder = new RandomWorldBuilder(18, questions,1,1111);
		world = worldBuilder.build();

		player = new Player(world.getEntryRoom());
		
		controller.setPlayer(player);
		controller.setWorld(world);
		
	    SwingUtilities.invokeLater(() -> mainFrame.switchToPanel(Panel.GRAPHICS));
	}
}
