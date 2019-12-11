package maze.controller.events;

import maze.Direction;
import maze.controller.Controller;
import maze.controller.GameEventListener;
import maze.model.StubbedWorldBuilder;
import maze.model.World;
import maze.model.question.QuestionDataSource;
import maze.model.question.StubbedDataSource;
import maze.view.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;
import utils.ObjectPersister;
import utils.ResultProvider;
import utils.ResultReceiver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.FileUtils.DATA_DIRECTORY;

class LoadGameEventTest implements View, Controller {
    private LoadGameEvent event;
    private World assignedWorld;
    private List<PanelType> panelsCalled;

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
        assert panelsCalled.size() == 1 && panelsCalled.get(0) == PanelType.GRAPHICS;
        assert assignedWorld.getEntryRoom().getDoor(Direction.north).getQuestion() != null;
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
    public void switchToPanel(PanelType panel) {
        panelsCalled.add(panel);
    }

    @Override
    public MapDetailView getMapDetailView() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public QuestionDataSource getDataSource() {
        return new StubbedDataSource();
    }

    @Override
    public void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultProcessor, Object object) {

    }
}