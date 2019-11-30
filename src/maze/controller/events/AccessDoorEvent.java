package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.World;
import maze.view.PanelType;
import maze.view.View;

public class AccessDoorEvent implements GameEvent {
	private Door door;
	
	AccessDoorEvent(Door door) {
		this.door = door;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		View.QuestionDetailView questionPanel = view.getQuestionDetailView();
		questionPanel.setQuestion(door.getQuestion());

		view.switchToPanel(PanelType.QUESTION);
	}
}
