package maze.controller.events;

import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
import maze.model.World;
import maze.model.question.Question;
import maze.model.question.sqlite.SQLiteQuestionDataSource;
import maze.view.Panel;
import maze.view.View;

import java.util.Set;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, View view, World world) {
		view.switchToPanel(Panel.LOADING);

		SQLiteQuestionDataSource questionDataSource = new SQLiteQuestionDataSource("questions.db");
		Set<Question> questions = questionDataSource.getAllQuestions();
		World.Builder worldBuilder = new RandomWorldBuilder(12, questions,1,
				((int) (Math.random() * 1000) + 1));
		world = worldBuilder.build();

		controller.setWorld(world);
		
	    view.switchToPanel(Panel.GRAPHICS);
	}
}