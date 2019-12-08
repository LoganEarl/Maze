package maze.controller.events;

import java.util.Set;

import maze.Direction;
import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Door;
import maze.model.Item;
import maze.model.Player;
import maze.model.Room;
import maze.model.World;
import maze.view.PanelType;
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
                room = player.getCurrentRoom();
                Set<Item> items = room.getItems();
                for (Item item : items) {
                	player.addItem(item);
                }
                items.clear();
            } else {
                GameEvent event = new AccessDoorEvent(door);
                controller.getEventListener().onGameEvent(event);
            }
        }
        
        if (world.getExitRoom() == room) {
        	GameEvent event = new SwitchPanelEvent(PanelType.GAME_WON);
        	controller.getEventListener().onGameEvent(event);
        }
    }
}