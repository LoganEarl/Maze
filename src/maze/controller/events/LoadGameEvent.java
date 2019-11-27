package maze.controller.events;

import maze.controller.Controller;
import maze.controller.MazeController;
import maze.controller.GameEvent;
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
    public void resolveTo(Controller controller, View view, World world) {
        ObjectPersister<World> persister = new ObjectPersister<>(DATA_DIRECTORY);
        World loaded = persister.loadObject(SaveGameEvent.getSaveFileName(loadSlotNumber));
        if(loaded != null){
            controller.setWorld(loaded);
            view.switchToPanel(Panel.GRAPHICS);
        }
    }
}
