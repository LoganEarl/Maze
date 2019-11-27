package maze.controller.events;

import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.Panel;
import maze.view.View;

public class MainMenuEvent implements GameEvent {
	@Override
	public void resolveTo(Controller controller, View view, World world) {
		view.switchToPanel(Panel.MAINMENU);
	}
}
