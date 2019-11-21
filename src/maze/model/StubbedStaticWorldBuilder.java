package maze.model;

import maze.model.question.Question;

import static maze.Direction.*;

@SuppressWarnings("WeakerAccess")
public class StubbedStaticWorldBuilder implements World.Builder {
    private Room entryRoom;
    private Room exitRoom;

    public StubbedStaticWorldBuilder(){
        entryRoom = new Room(-1,0);

        exitRoom = new Room(1,0);
    }

    @Override
    public World build() {
        Room ul = new Room(-1, 1);
        Room um = new Room(0,1);
        Room lm = new Room(0,-1);
        Room lr = new Room(1,-1);

        Room ur = new Room(1,1);
        Room ll = new Room(-1,-1);

        //this will change later
        Question myQuestion = Question.STUBBED_QUESTION;

        //set core path to end
        entryRoom.setRoomConnection(north,ul,south,myQuestion);
        ul.setRoomConnection(east,um,west,myQuestion);
        um.setRoomConnection(south,lm,north,myQuestion);
        lm.setRoomConnection(east,lr,west,myQuestion);
        lr.setRoomConnection(north, exitRoom, south, myQuestion);

        //set extra rooms
        um.setRoomConnection(east,ur,west,myQuestion);
        lm.setRoomConnection(west, ll, east, myQuestion);

        return new World(entryRoom, exitRoom);
    }
}
