package maze.controller;

import maze.model.World;
import maze.view.MainFrame;

public interface GameEvent {
	public void resolveTo(Controller controller, MainFrame mainFrame, World world);
}
