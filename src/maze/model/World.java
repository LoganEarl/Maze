package maze.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class World implements Serializable {
    private Room entryRoom;
    private Room exitRoom;
    private Player player;
    private long seed = -1;

    World(Room entryRoom, Room exitRoom) {
        this.entryRoom = entryRoom;
        this.exitRoom = exitRoom;
        this.player = new Player(entryRoom);
    }

    World(Room entryRoom, Room exitRoom, long seed){
        this(entryRoom, exitRoom);
        this.seed = seed;
    }

    public Set<Door> getAllDoors(){
        Set<Room> allRooms = getAllRooms();
        Set<Door> allDoors = new HashSet<>();
        for(Room r: allRooms)
            allDoors.addAll(r.getDoors());
        return allDoors;
    }

    public Set<Room> getAllRooms(){
        Set<Room> visited = new HashSet<>();
        LinkedList<Room> toExplore = new LinkedList<>();
        toExplore.add(entryRoom);
        toExplore.add(exitRoom);

        while(!toExplore.isEmpty()){
            Room cur = toExplore.pop();
            visited.add(cur);
            for(Door d: cur) {
                Room next = d.getOtherRoom(cur);
                if (!visited.contains(next))
                    toExplore.add(next);
            }
        }
        return visited;
    }

    //will path from the player's current position to the exit, making sure that it is possible to solve the maze
    //from where the player is currently and with the player's current items (and any it might find)
    public boolean currentRouteExists(){
        return canPathTo(player.getCurrentRoom(), exitRoom, new HashSet<>(), new HashSet<>(player.getItems()), new HashSet<>());
    }

    //will path from the entry point to the exit point, checking if passage is possible. Only use to validate
    //a freshly minted world, as it is not guaranteed to work once the player starts futzing with items

    public boolean baseRouteExists(){
        return canPathTo(entryRoom, exitRoom, new HashSet<>(), new HashSet<>(),new HashSet<>());
    }

    //this algorithm makes the assumption that using a question-specific item is never a bad idea.
    //IE, one item cannot open two different doors, and a question will never appear more than once
    private boolean canPathTo(Room source, Room destination, Set<Room> visited, Set<Item> usableItems, Set<Item> usedItems){
        if(source == destination)
            return true;

        Set<Room> myVisited = new HashSet<>(visited);
        Set<Item> foundItems = new HashSet<>(source.getItems());
        Set<Item> myUsedItems = new HashSet<>(usedItems);
        foundItems.addAll(usableItems);
        myVisited.add(source);

        //class A calls: explore as far as we can without using any items
        for(Door exit: source){
            Room nextRoom = exit.getOtherRoom(source);
            if(!myVisited.contains(nextRoom))
                if(!exit.isLocked() || exit.isOpen())
                    if(canPathTo(nextRoom, exitRoom, myVisited, foundItems, new HashSet<>()))
                        return true;
        }

        myVisited = new HashSet<>(visited);
        myVisited.add(source);
        
        //class B calls: explore as far as we can without using skeleton keys
        for(Door exit: source){
            Room nextRoom = exit.getOtherRoom(source);
            if(!myVisited.contains(nextRoom)){
                if(!exit.isLocked() || exit.isOpen()){
                    if(canPathTo(nextRoom, exitRoom, myVisited, foundItems, myUsedItems))
                        return true;
                }else if(foundItems.contains(exit.getKeyItem()) && !myUsedItems.contains(exit.getKeyItem())){
                    myUsedItems.add(exit.getKeyItem());
                    foundItems.remove(exit.getKeyItem());
                    if(canPathTo(nextRoom, exitRoom, myVisited, foundItems, myUsedItems))
                        return true;
                }
            }
        }

        myVisited = new HashSet<>(visited);
        myVisited.add(source);

        //class C calls: explore as far as we can, even using any skeleton keys we might have
        for(Door exit: source){
            Room nextRoom = exit.getOtherRoom(source);
            if(!myVisited.contains(nextRoom)){
                if(!exit.isLocked() || exit.isOpen()){
                    if(canPathTo(nextRoom, exitRoom, myVisited, foundItems, myUsedItems))
                        return true;
                }else{
                    for(Item i:foundItems){
                        if(i.answersQuestion(exit.getQuestion()) && !usedItems.contains(i)){
                            Set<Item> newUsedItems = new HashSet<>(usedItems);
                            newUsedItems.add(i);
                            if(canPathTo(nextRoom, exitRoom, myVisited, foundItems, newUsedItems))
                                return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public Room getEntryRoom() {
        return entryRoom;
    }

    public Room getExitRoom() {
        return exitRoom;
    }

    public Player getPlayer() {
        return player;
    }

    public long getSeed(){
        return this.seed;
    }

    public interface Builder {
        World build();
    }
}
