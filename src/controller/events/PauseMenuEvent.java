package controller.events;

import controller.Controller;
import controller.GameEvent;
import maze.model.Player;
import maze.model.World;
import view.MainFrame;
import view.Panel;

public class PauseMenuEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, Player player, World world) {
		mainFrame.switchToPanel(Panel.MAINMENU);
		
	}
}
