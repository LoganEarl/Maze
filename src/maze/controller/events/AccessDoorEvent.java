package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.Player;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.Panel;
import maze.view.QuestionPanel;

public class AccessDoorEvent implements GameEvent {
	private Door door;
	
	public AccessDoorEvent(Door door) {
		this.door = door;
	}

	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		QuestionPanel questionPanel = mainFrame.getQuestionPanel();
		
		questionPanel.setQuestion(door.getQuestion());
		
		mainFrame.switchToPanel(Panel.QUESTION);
	}
}
