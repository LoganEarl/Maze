package maze.controller.events;

import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.View;
import maze.view.Zoom;

public class ZoomEvent implements GameEvent {
	Zoom zoom;
	
	public ZoomEvent(Zoom zoom) {
		this.zoom = zoom;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		View.MapDetailView gp = view.getMapDetailView();
		if (gp != null) {
			gp.zoomTo(zoom);
		}
	}

}
