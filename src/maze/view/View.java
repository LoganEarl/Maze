package maze.view;

import maze.model.question.Question;

public interface View extends ResultPrompter {
    void switchToPanel(PanelType panel);
    void promptForResult(Class<? extends ResultProvider> resultProvider, ResultReceiver resultProcessor, Object object);
    QuestionDetailView getQuestionDetailView();
    MapDetailView getMapDetailView();

    interface QuestionDetailView {
        Question getQuestion();
        void setQuestion(Question question);
    }

    interface MapDetailView {
        void zoomTo(Zoom zoom);
    }
}
