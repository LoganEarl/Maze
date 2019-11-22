package maze.controller.events;

import maze.controller.Controller;
import maze.model.Player;
import maze.model.StubbedStaticWorldBuilder;
import maze.model.World;
import maze.view.Panel;
import maze.view.View;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileUtils.DATA_DIRECTORY;

class SaveGameEventTest extends Controller implements View {
    private SaveGameEvent event;
    private World myWorld;
    private List<Panel> panelsCalled;
    private int numInitCalls;

    @BeforeEach
    void setUp() {
        event = new SaveGameEvent(0);
        panelsCalled = new ArrayList<>();
        myWorld = new StubbedStaticWorldBuilder().build();
        numInitCalls = 0;
    }

    @Test
    void resolveTo() {
        event.resolveTo(this,this, myWorld.getPlayer(), myWorld);
        assert(numInitCalls == 1);
        assert(panelsCalled.size() == 1 && panelsCalled.get(0) == Panel.GRAPHICS);
        assert(new File(DATA_DIRECTORY, SaveGameEvent.getSaveFileName(0)).exists());
    }

    @AfterEach
    void tearDown() {
        //noinspection ResultOfMethodCallIgnored
        new File(DATA_DIRECTORY, SaveGameEvent.getSaveFileName(0)).delete();
    }

    @Override
    public void switchToPanel(Panel panel) {
        panelsCalled.add(panel);
    }

    @Override
    public void initialize(Player player, World world) {
        numInitCalls++;
    }
}