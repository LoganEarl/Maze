package maze.controller.events;

import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.RandomWorldBuilder;
import maze.model.question.Question;
import maze.model.question.sqlite.SQLiteQuestionDataSource;
import maze.view.MainFrame;
import maze.view.Panel;
import maze.model.World;

import java.util.Set;

public class NewGameEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.switchToPanel(Panel.LOADING);

		SQLiteQuestionDataSource questionDataSource = new SQLiteQuestionDataSource("questions.db");
		Set<Question> questions = questionDataSource.getAllQuestions();
		World.Builder worldBuilder = new RandomWorldBuilder(4, questions,1, ((int) (Math.random() * ((1000 - 1) + 1)) + 1));
		world = worldBuilder.build();

		controller.setWorld(world);
		
	    SwingUtilities.invokeLater(() -> mainFrame.switchToPanel(Panel.GRAPHICS));
	}
}