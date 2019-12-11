package maze.controller;

import maze.model.World;
import maze.view.View;

public interface GameEvent {
	void resolveTo(Controller controller, View view, World world);
}
