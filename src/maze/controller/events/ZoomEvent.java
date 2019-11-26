package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.GraphicsPanel;
import maze.view.MainFrame;
import maze.view.Zoom;

public class ZoomEvent implements GameEvent {
	Zoom zoom;
	
	public ZoomEvent(Zoom zoom) {
		this.zoom = zoom;
	}

	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, World world) {
		GraphicsPanel gp = mainFrame.getGraphicsPanel();
		if (gp != null) {
			gp.zoomGraphics(zoom);
		}
	}

}
