package maze.model.question;

import maze.model.Item;

public class SkeletonKey implements Item {
    public static final String SKELETON_KEY_NAME = "Skeleton Key";

    @Override
    public String getName() {
        return SKELETON_KEY_NAME;
    }

    @Override
    public boolean answersQuestion(Question q) {
        return true;
    }
}
