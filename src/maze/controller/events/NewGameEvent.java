package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Player;
import maze.model.RandomWorldBuilder;
import maze.model.question.MasterKey;
import maze.model.question.Question;
import maze.model.question.sqlite.SQLiteQuestionDataSource;
import maze.view.PanelType;
import maze.model.World;
import maze.view.View;

import java.util.Set;

public class NewGameEvent implements GameEvent {
	private boolean masterKey;
	
	public NewGameEvent(boolean masterKey) {
		this.masterKey = masterKey;
	}
	
	@Override
    public void resolveTo(Controller controller, View view, World world) {
        view.switchToPanel(PanelType.LOADING);

		SQLiteQuestionDataSource questionDataSource = new SQLiteQuestionDataSource("questions.db");
		Set<Question> questions = questionDataSource.getAllQuestions();
        int seed = (int) (Math.random() * ((1000 - 1) + 1)) + 1;

        System.out.println("New World Seed: " + seed);
		World.Builder worldBuilder = new RandomWorldBuilder(12, questions, 3, seed);
		world = worldBuilder.build();
		
		if (masterKey) {
			Player player = world.getPlayer();
			player.addItem(new MasterKey());
		}

		controller.setWorld(world);

        view.switchToPanel(PanelType.GRAPHICS);
	}
}