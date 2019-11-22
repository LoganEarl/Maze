package maze.controller;

import maze.model.Player;
import maze.model.World;
import maze.view.MainFrame;
import maze.view.View;

public interface GameEvent {
	void resolveTo(Controller controller, View mainFrame, Player player, World world);
}
