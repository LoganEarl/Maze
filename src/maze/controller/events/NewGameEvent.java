package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
import maze.model.question.Question;
import maze.model.question.SqLiteDatabase;
import maze.view.Panel;
import maze.model.Player;
import maze.model.World;
import maze.view.View;

import java.util.List;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, View view, Player player, World world) {
		view.switchToPanel(Panel.LOADING);

		SqLiteDatabase db = new SqLiteDatabase("data/mazedb.sqlite3");
		List<Question> questions = db.readAllRecords();
		World.Builder worldBuilder = new RandomWorldBuilder(18, questions,1,1111);
		world = worldBuilder.build();

		player = world.getPlayer();
		
		controller.setPlayer(player);
		controller.setWorld(world);

		view.switchToPanel(Panel.GRAPHICS);
	}
}
