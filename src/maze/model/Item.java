package maze.model;

import java.io.Serializable;

public interface Item extends Serializable {
    String getName();

    //skeleton keys are special cases that can open any door.
    class SkeletonKey implements Item {
        public static final String ITEM_NAME = "Skeleton Key";
        @Override
        public String getName() {
            return ITEM_NAME;
        }
    }
}
