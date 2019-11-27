package maze.view;

import maze.model.World;
import maze.model.question.Question;

public interface View {
    void switchToPanel(Panel panel);
    QuestionDetailView getQuestionDetailView();
    MapDetailView getMapDetailView();

    interface QuestionDetailView {
        Question getQuestion();
        void setQuestion(Question question);
    }

    interface MapDetailView {
        void zoomTo(Zoom zoom);
        void setWorld(World world);
    }
}
