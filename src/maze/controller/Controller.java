package maze.controller;

import maze.model.Player;
import maze.model.World;
import maze.view.GraphicsPanel;
import maze.view.MainFrame;

public class Controller implements GameEventListener {
	private MainFrame mainFrame;
	private Player player;
	private World world;
	
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void onGameEvent(GameEvent gameEvent) {
		gameEvent.resolveTo(this, mainFrame, player, world);
		GraphicsPanel graphicsPanel = mainFrame.getGraphicsPanel();
		graphicsPanel.initialize(player, world);
		graphicsPanel.repaint();
	}
}