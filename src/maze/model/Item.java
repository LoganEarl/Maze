package maze.model;

import maze.model.question.Question;

public interface Item {
    String getName();
    boolean answersQuestion(Question q);
}
