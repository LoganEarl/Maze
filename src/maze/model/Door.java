package maze.model;

import utils.Pair;
import maze.model.question.Question;

@SuppressWarnings("WeakerAccess")
public class Door {
    private Pair<Room> connectedRooms;
    private Question question;
    //locked due to repeated open attempts
    private boolean isLocked;
    //has been opened, either with an item or a question
    private boolean isOpen;
    private Item keyItem;

    public Door(Pair<Room> connectedRooms, Question question, Item keyItem) {
        this.connectedRooms = connectedRooms;
        this.question = question;
        this.isLocked = false;
        this.isOpen = false;
        this.keyItem = keyItem;
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

    //doors cannot be unlocked once locked
    public void lock(){
        this.isLocked = true;
    }

    //if the door's question was answered or an item was used to open it
    public boolean isOpen() {
        return isOpen;
    }

    public void open(){
        this.isOpen = true;
    }

    //returns the key item or null if no item exists for this room
    public Item getKeyItem(){
        return this.keyItem;
    }
}