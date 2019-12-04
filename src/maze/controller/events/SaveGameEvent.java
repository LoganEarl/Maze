package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.World;
import maze.view.PanelType;
import maze.view.View;
import utils.ObjectPersister;

import static utils.FileUtils.DATA_DIRECTORY;

public class SaveGameEvent implements GameEvent {
    private int saveSlotNumber;

    public SaveGameEvent(int saveSlotNumber){
        this.saveSlotNumber = saveSlotNumber;
    }

    @Override
    public void resolveTo(Controller controller, View view, World world) {
        ObjectPersister<World> persister = new ObjectPersister<>(DATA_DIRECTORY);
        boolean success = persister.saveObject(world, getSaveFileName(saveSlotNumber));
        if(!success) System.out.println("Failed to save world of slot " + saveSlotNumber);
        view.switchToPanel(PanelType.GRAPHICS);
    }

    static String getSaveFileName(int saveSlotNumber){
        return "Slot" + saveSlotNumber + ".world";
    }
}
