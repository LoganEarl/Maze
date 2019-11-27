package maze.controller.events;

import maze.Zoom;
import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.PanelType;
import maze.view.panel.GraphicsPanel;

public class ZoomEvent implements GameEvent {
	Zoom zoom;
	
	public ZoomEvent(Zoom zoom) {
		this.zoom = zoom;
	}

	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		GraphicsPanel gp = (GraphicsPanel) mainFrame.getPanel(PanelType.GRAPHICS);
		if (gp != null) {
			gp.zoomGraphics(zoom);
		}
	}

}
