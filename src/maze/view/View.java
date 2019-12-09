package maze.view;

import utils.ResultPrompter;
import utils.ResultProvider;
import utils.ResultReceiver;

public interface View extends ResultPrompter {
    void switchToPanel(PanelType panel);
    MapDetailView getMapDetailView();

    interface MapDetailView {
        void zoomTo(Zoom zoom);
    }
}
