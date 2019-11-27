package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.PanelType;
import maze.view.panel.QuestionPanel;

public class AccessDoorEvent implements GameEvent {
	private Door door;
	
	public AccessDoorEvent(Door door) {
		this.door = door;
	}

	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		QuestionPanel questionPanel = (QuestionPanel) mainFrame.getPanel(PanelType.QUESTION);
		
		questionPanel.setQuestion(door.getQuestion());
		
		mainFrame.switchToPanel(PanelType.QUESTION);
	}
}
