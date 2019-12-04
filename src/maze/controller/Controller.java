package maze.controller;

import maze.model.World;
import maze.model.question.QuestionDataSource;
import maze.view.View;

public interface Controller {
    void setWorld(World world);
    void setView(View view);
    World getWorld();
    View getView();
    QuestionDataSource getDataSource();
    GameEventListener getEventListener();
}
