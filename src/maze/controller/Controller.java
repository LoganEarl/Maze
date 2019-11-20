package maze.controller;

import maze.model.World;
import maze.view.GraphicsPanel;
import maze.view.MainFrame;

public class Controller implements GameEventListener {
	private MainFrame mainFrame;
	private World world;
	
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	@Override
	public void onGameEvent(GameEvent gameEvent) {
		gameEvent.resolveTo(this, mainFrame, world);
		GraphicsPanel graphicsPanel = mainFrame.getGraphicsPanel();
		graphicsPanel.setWorld(this.world);
		graphicsPanel.repaint();
	}
}