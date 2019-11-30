package maze.view;

public class StubbedView implements View {
    @Override
    public QuestionDetailView getQuestionDetailView() {
        return new StubbedQuestionDetailView();
    }

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
