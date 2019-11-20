package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.view.MainFrame;
import maze.view.Panel;
import maze.model.World;

public class QuestionManagerEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.switchToPanel(Panel.QUESTION_MANAGER);
	}
}
