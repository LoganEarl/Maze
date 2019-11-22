package maze.model.question;

import maze.model.Item;

public class MazeItem implements Item {
    private String name;

    public MazeItem(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
