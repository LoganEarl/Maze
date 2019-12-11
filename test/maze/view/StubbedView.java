package maze.view;

import utils.ResultProvider;
import utils.ResultReceiver;

public class StubbedView implements View {
    @Override
    public MapDetailView getMapDetailView() {
        return new StubbedMapDetailView();
    }

    @Override
    public void switchToPanel(PanelType panel) {

    }

    @Override
    public void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultProcessor, Object object) {

    }
}
