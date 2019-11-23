package maze.controller.events;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
import maze.model.question.Question;
import maze.model.question.SqLiteDatabase;
import maze.view.MainFrame;
import maze.view.Panel;
import maze.model.World;

import java.util.List;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.switchToPanel(Panel.LOADING);

		SqLiteDatabase db = new SqLiteDatabase("data/mazedb.sqlite3");
		List<Question> questions = db.readAllRecords();
		World.Builder worldBuilder = new RandomWorldBuilder(18, questions,1, ((int) (Math.random() * ((1000 - 1) + 1)) + 1));
		world = worldBuilder.build();

		controller.setWorld(world);
		
	    SwingUtilities.invokeLater(() -> mainFrame.switchToPanel(Panel.GRAPHICS));
	}
}