package world.model;

import utils.Pair;
import world.question.Question;

public class Door {
    private Pair<Room> connectedRooms;
    private Question question;
    private boolean isLocked;
    private boolean isOpen;

    public Door(Pair<Room> connectedRooms, Question question) {
        this.connectedRooms = connectedRooms;
        this.question = question;
        this.isLocked = false;
        this.isOpen = false;
    }

    public Room getOtherRoom(Room sourceRoom){
        return connectedRooms.otherItem(sourceRoom);
    }

    public Pair<Room> getConnectedRooms(){
        return connectedRooms;
    }

    public Question getQuestion() {
        return question;
    }

    //if the door was locked due to failed attempts
    public boolean isLocked() {
        return isLocked;
    }

    //if the door's question was answered or an item was used to open it
    public boolean isOpen() {
        return isOpen;
    }
}
