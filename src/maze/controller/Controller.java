package maze.controller;

import maze.model.World;
import maze.view.View;

public interface Controller {
    void setWorld(World world);
    void setView(View view);
    GameEventListener getEventListener();
}
