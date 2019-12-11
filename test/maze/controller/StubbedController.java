package maze.controller;

import maze.model.World;
import maze.model.question.QuestionDataSource;
import maze.view.View;

public class StubbedController implements Controller {
    @Override
    public void setWorld(World world) {
        //do nothing
    }

    @Override
    public void setView(View view) {
        //do nothing
    }

    @Override
    public GameEventListener getEventListener() {
        return new StubbedGameEventListener();
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
        return null;
    }
}
