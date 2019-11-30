package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.PanelType;
import maze.view.View;

public class SwitchPanelEvent implements GameEvent {
	private PanelType panelType;
	
	public SwitchPanelEvent(PanelType panelType) {
		this.panelType = panelType;
	}
	
	@Override
	public void resolveTo(Controller controller, View view, World world) {
		view.switchToPanel(panelType);
	}
}
