package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.model.Player;
import maze.model.World;
import maze.view.Panel;
import maze.view.View;
import utils.ObjectPersister;

public class SaveGameEvent implements GameEvent {
    private int saveSlotNumber;

    public SaveGameEvent(int saveSlotNumber){
        this.saveSlotNumber = saveSlotNumber;
    }

    @Override
    public void resolveTo(Controller controller, View view, Player player, World world) {
        ObjectPersister<World> persister = new ObjectPersister<>("/data/");
        boolean success = persister.saveObject(world, getSaveFileName(saveSlotNumber));
        if(!success) System.out.println("Failed to save world of slot " + saveSlotNumber);
        view.switchToPanel(Panel.GRAPHICS);
    }

    static String getSaveFileName(int saveSlotNumber){
        return "Slot" + saveSlotNumber + ".world";
    }
}
