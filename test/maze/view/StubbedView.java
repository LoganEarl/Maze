package maze.view;

public class StubbedView implements View {
    @Override
    public void switchToPanel(Panel panel) {
        //do nothing
    }

    @Override
    public QuestionDetailView getQuestionDetailView() {
        return new StubbedQuestionDetailView();
    }

    @Override
    public MapDetailView getMapDetailView() {
        return new StubbedMapDetailView();
    }
}
