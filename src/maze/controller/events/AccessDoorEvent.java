package maze.controller.events;

import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.World;
import maze.view.Panel;
import maze.view.View;

public class AccessDoorEvent implements GameEvent {
	private Door door;
	
	public AccessDoorEvent(Door door) {
		this.door = door;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		View.QuestionDetailView questionPanel = view.getQuestionDetailView();
		questionPanel.setQuestion(door.getQuestion());

		view.switchToPanel(Panel.QUESTION);
	}
}
