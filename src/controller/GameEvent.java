package controller;

import maze.model.Player;
import maze.model.World;
import view.MainFrame;

public interface GameEvent {
	public void resolveTo(Controller controller, MainFrame mainFrame, Player player, World world);
}
