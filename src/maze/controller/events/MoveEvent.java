package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.Direction;
import maze.model.Door;
import maze.model.Player;
import maze.model.Room;
import maze.model.World;
import view.MainFrame;

public class MoveEvent implements GameEvent {
	private Direction direction;
	
	public MoveEvent(Direction direction) {
		this.direction = direction;
	}

	@Override
	public void resolveTo(Controller controller, MainFrame mainFrame, Player player, World world) {
		if (direction == Direction.east || direction == Direction.west) {
			player.setFacing(direction);
		};
		
		Room curRoom = player.getCurrentRoom();
		
		if (curRoom.hasDoor(direction)) {
			Door door = curRoom.getDoor(direction);
			if (door.isOpen()) {
				player.move(direction);
			} else {
				door.open();
			}
		}
	}
}
