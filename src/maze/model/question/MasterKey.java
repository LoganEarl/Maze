package maze.model.question;

import maze.model.Item;

public class MasterKey implements Item {
    public static final String MASTER_KEY_NAME = "Master Key";

    @Override
    public String getName() {
        return MASTER_KEY_NAME;
    }

    @Override
    public boolean answersQuestion(Question q) {
        return true;
    }
}
