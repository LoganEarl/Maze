package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
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
