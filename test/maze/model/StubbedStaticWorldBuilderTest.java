package maze.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StubbedStaticWorldBuilderTest {
    private StubbedStaticWorldBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new StubbedStaticWorldBuilder();
    }

    @Test
    void build() {
        World builtWorld = builder.build();
        assert(builtWorld != null);
        assert(builtWorld.getEntryRoom() != null);
        assert(builtWorld.getExitRoom() != null);
        assert(builtWorld.getEntryRoom() != builtWorld.getExitRoom());
        assert(builtWorld.getPlayer() != null);
        assert(builtWorld.getPlayer().getCurrentRoom() == builtWorld.getEntryRoom());
        assert(builtWorld.baseRouteExists());
    }
}