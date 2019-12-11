package maze.model.question;

import maze.model.Item;

import java.util.Objects;

public class StubbedDoorItem implements Item {
    private String name;

    public StubbedDoorItem(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean answersQuestion(Question q) {
        return q.isCorrect(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StubbedDoorItem doorKey = (StubbedDoorItem) o;
        return name.equals(doorKey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
