package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.PanelType;
import maze.view.View;

public class QuestionManagerEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, View view, World world) {
		view.switchToPanel(PanelType.QUESTION_SELECTOR);
	}
}
