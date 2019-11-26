package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.Panel;

public class QuestionManagerEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.switchToPanel(Panel.QUESTION_MANAGER);
	}
}
