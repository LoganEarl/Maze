package maze.controller.events;

import maze.controller.Controller;
import maze.controller.GameEventListener;
import maze.model.StubbedWorldBuilder;
import maze.model.World;
import maze.view.Panel;
import maze.view.View;
import maze.view.Zoom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;
import utils.ObjectPersister;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.FileUtils.DATA_DIRECTORY;

class LoadGameEventTest implements View, Controller {
    private LoadGameEvent event;
    private World myWorld;
    private World assignedWorld;
    private List<Panel> panelsCalled;

    @BeforeEach
    void setUp() {
        event = new LoadGameEvent(-2);

        ObjectPersister<World> persister = new ObjectPersister<>(DATA_DIRECTORY);
        World testWorld = new StubbedWorldBuilder().build();
        persister.saveObject(testWorld, SaveGameEvent.getSaveFileName(-2));

        panelsCalled = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATA_DIRECTORY, SaveGameEvent.getSaveFileName(-2)));
    }

    @Test
    void resolveTo(){
        event.resolveTo(this, this, null);
        assert assignedWorld != null;
        assert new File(DATA_DIRECTORY, SaveGameEvent.getSaveFileName(-2)).exists();
        assert panelsCalled.size() == 1 && panelsCalled.get(0) == Panel.GRAPHICS;
    }

    @Override
    public void setWorld(World world) {
        assignedWorld = world;
    }

    @Override
    public void setView(View view) {
    }

    @Override
    public GameEventListener getEventListener() {
        return null;
    }

    @Override
    public void switchToPanel(Panel panel) {
        panelsCalled.add(panel);
    }

    @Override
    public QuestionDetailView getQuestionDetailView() {
        return null;
    }

    @Override
    public MapDetailView getMapDetailView() {
        return null;
    }
}