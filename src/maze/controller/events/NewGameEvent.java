package maze.controller.events;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
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

		SqLiteDatabase db = new SqLiteDatabase("data/mazedb.sqlite3");
		List<Question> questions = db.readAllRecords();
		World.Builder worldBuilder = new RandomWorldBuilder(20, questions,1,1111);
		world = worldBuilder.build();

		player = new Player(world.getEntryRoom());
		
		controller.setPlayer(player);
		controller.setWorld(world);
		
	    SwingUtilities.invokeLater(() -> mainFrame.switchToPanel(Panel.GRAPHICS));
	}
}
