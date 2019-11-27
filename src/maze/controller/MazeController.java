package maze.controller;

import maze.model.World;
import maze.view.View;

public class MazeController implements GameEventListener, Controller {
	private View view;
	private World world;
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	public GameEventListener getEventListener() {
		return this;
	}

	@Override
	public void onGameEvent(GameEvent gameEvent) {
		gameEvent.resolveTo(this, view, world);
		View.MapDetailView mapView = view.getMapDetailView();
		if(mapView != null)
			mapView.setWorld(world);
	}
}