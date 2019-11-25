package maze.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StubbedWorldBuilderTest {
    private StubbedWorldBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new StubbedWorldBuilder();
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