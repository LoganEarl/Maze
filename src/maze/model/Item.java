package maze.model;

import maze.model.question.Question;

import java.io.Serializable;

public interface Item extends Serializable {
    String getName();
    boolean answersQuestion(Question q);
}
