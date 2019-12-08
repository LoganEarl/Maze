package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Item;
import maze.model.Player;
import maze.model.World;
import maze.model.question.MasterKey;
import maze.view.View;

public class ItemUsedEvent implements GameEvent {
	private Item item;
	
	public ItemUsedEvent(Item item) {
		this.item = item;
	}

	@Override
	public void resolveTo(Controller controller, View view, World world) {
		Player player = world.getPlayer();
		if (!(item instanceof MasterKey)) {
			player.removeItem(item);
		}
	}
}
