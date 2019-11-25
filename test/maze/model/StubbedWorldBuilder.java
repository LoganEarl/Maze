package maze.model;

import maze.model.question.Question;
import maze.model.question.TestingQuestions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static maze.Direction.*;

public class StubbedWorldBuilder implements World.Builder {
    private Room entryRoom;
    private Room exitRoom;

    private List<Question> questions;

    public StubbedWorldBuilder() {
        this.questions = new ArrayList<>(List.of(TestingQuestions.questions));
        entryRoom = new Room(-1, 0);
        exitRoom = new Room(1, 0);
    }

    @Override
    public World build() {
        Room ul = new Room(-1, 1);
        Room um = new Room(0, 1);
        Room lm = new Room(0, -1);
        Room lr = new Room(1, -1);

        Room ur = new Room(1, 1);
        Room ll = new Room(-1, -1);

        //set core path to end
        entryRoom.setRoomConnection(north, ul, south, takeNextQuestion());
        ul.setRoomConnection(east, um, west, takeNextQuestion());
        um.setRoomConnection(south, lm, north, takeNextQuestion());
        lm.setRoomConnection(east, lr, west, takeNextQuestion());
        lr.setRoomConnection(north, exitRoom, south, takeNextQuestion());

        //set extra rooms
        um.setRoomConnection(east, ur, west, takeNextQuestion());
        lm.setRoomConnection(west, ll, east, takeNextQuestion());

        return new World(entryRoom, exitRoom);
    }

    private Question takeNextQuestion() {
        return questions.remove(0);
    }
}
