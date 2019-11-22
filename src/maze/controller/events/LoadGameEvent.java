package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Player;
import maze.model.World;
import maze.view.Panel;
import maze.view.View;
import utils.ObjectPersister;

import static utils.FileUtils.DATA_DIRECTORY;

public class LoadGameEvent implements GameEvent {
    private int loadSlotNumber;

    public LoadGameEvent(int loadSlotNumber) {
        this.loadSlotNumber = loadSlotNumber;
    }

    @Override
    public void resolveTo(Controller controller, View view, Player player, World world) {
        ObjectPersister<World> persister = new ObjectPersister<>(DATA_DIRECTORY);
        world = persister.loadObject(SaveGameEvent.getSaveFileName(loadSlotNumber));
        if(world != null){
            player = world.getPlayer();

            controller.setPlayer(player);
            controller.setWorld(world);

            view.switchToPanel(Panel.GRAPHICS);
        }
    }
}
