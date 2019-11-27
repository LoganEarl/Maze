package maze.controller.events;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
import maze.model.question.Question;
import maze.model.question.SqLiteDatabase;
import maze.view.MainFrame;
import maze.view.PanelType;
import maze.model.World;

import java.util.List;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.switchToPanel(PanelType.LOADING);

		SqLiteDatabase db = new SqLiteDatabase("data/mazedb.sqlite3");
		List<Question> questions = db.readAllRecords();
		int rand = (int) (Math.random() * ((1000 - 1) + 1)) + 1;

		World.Builder worldBuilder = new RandomWorldBuilder(18, questions,1, rand);
		world = worldBuilder.build();
 
		controller.setWorld(world);
		
		System.out.println("New World Seed: " + rand);
		
	    SwingUtilities.invokeLater(() -> mainFrame.switchToPanel(PanelType.GRAPHICS));
	}
}