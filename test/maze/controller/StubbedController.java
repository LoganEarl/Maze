package maze.controller;

import maze.model.World;
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
}
