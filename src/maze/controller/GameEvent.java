package maze.controller;

import maze.model.Player;
import maze.model.World;
import maze.view.MainFrame;

public interface GameEvent {
	public void resolveTo(Controller controller, MainFrame mainFrame, Player player, World world);
}
