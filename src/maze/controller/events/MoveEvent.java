package maze.controller.events;

import maze.Direction;
import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.Player;
import maze.model.Room;
import maze.model.World;
import maze.view.View;

public class MoveEvent implements GameEvent {
    private Direction direction;

    public MoveEvent(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void resolveTo(Controller controller, View view, World world) {
        Player player = world.getPlayer();
        Room room = player.getCurrentRoom();
        Door door = room.getDoor(direction);

        player.setFacing(direction);

        if (door != null) {
            if (door.isOpen()) {
                player.move(direction);
            } else if (!door.isLocked()) {
                AccessDoorEvent event = new AccessDoorEvent(door);
                controller.getEventListener().onGameEvent(event);
            }
        }
    }
}