package maze.controller.events;

import maze.controller.StubbedController;
import maze.model.StubbedWorldBuilder;
import maze.model.World;
import maze.view.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static utils.FileUtils.DATA_DIRECTORY;

class SaveGameEventTest implements View {
    private SaveGameEvent event;
    private World myWorld;
    private List<PanelType> panelsCalled;

    @BeforeEach
    void setUp() {
        event = new SaveGameEvent(-1);
        panelsCalled = new ArrayList<>();
        myWorld = new StubbedWorldBuilder().build();
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATA_DIRECTORY, SaveGameEvent.getSaveFileName(-1)));
    }

    @Test
    void resolveTo() {
        event.resolveTo(new StubbedController(),this, myWorld);
        assert panelsCalled.size() == 1 && panelsCalled.get(0) == PanelType.GRAPHICS;
        assert new File(DATA_DIRECTORY, SaveGameEvent.getSaveFileName(-1)).exists();
    }

    @Override
    public void switchToPanel(PanelType panel) {
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

    @Override
    public void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultProcessor, Object object) {

    }
}