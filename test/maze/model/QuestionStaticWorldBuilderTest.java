package maze.model;

import maze.model.question.Question;
import maze.model.question.QuestionImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class QuestionStaticWorldBuilderTest {
    private QuestionStaticWorldBuilder builder;
    private List<Question> questions = null;

    private static final long randomSeed = 1111;

    @BeforeEach
    void setUp() throws Exception {
    	questions = QuestionImporter.getDefaultQuestions().getQuestions();
        builder = new QuestionStaticWorldBuilder(questions, randomSeed);
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