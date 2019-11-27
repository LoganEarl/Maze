package maze.controller;

import maze.model.World;
import maze.view.MainFrame;
import maze.view.PanelType;
import maze.view.panel.GraphicsPanel;

public class Controller implements GameEventListener {
	private MainFrame mainFrame;
	private World world;
	
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return world;
	}
	
	@Override
	public void onGameEvent(GameEvent gameEvent) {
		gameEvent.resolveTo(this, mainFrame, world);
		GraphicsPanel graphicsPanel = (GraphicsPanel) mainFrame.getPanel(PanelType.GRAPHICS);
		graphicsPanel.repaint();
	}
}