package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.PanelType;

public class SwitchPanelEvent implements GameEvent {
	private PanelType panelType;
	
	public SwitchPanelEvent(PanelType panelType) {
		this.panelType = panelType;
	}
	
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		mainFrame.switchToPanel(panelType);	
	}
}
