package maze.controller;

import maze.model.Player;
import maze.model.World;
import maze.view.GraphicsPanel;
import maze.view.MainFrame;
import maze.view.View;

public class Controller implements GameEventListener {
	private View view;
	private Player player;
	private World world;
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void onGameEvent(GameEvent gameEvent) {
		gameEvent.resolveTo(this, view, player, world);
		view.initialize(player, world);
	}
}