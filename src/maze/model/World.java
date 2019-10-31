package maze.model;

import java.util.HashSet;
import java.util.Set;

public class World {
    private Room entryRoom;
    private Room exitRoom;
    private Player player;

    World(Room entryRoom, Room exitRoom) {
        this.entryRoom = entryRoom;
        this.exitRoom = exitRoom;
        this.player = new Player(entryRoom);
    }

    public boolean currentRouteExists(){
        return canPathTo(player.getCurrentRoom(), exitRoom, new HashSet<>(), new HashSet<>(player.getItems()));
    }

    public boolean baseRouteExists(){
        return canPathTo(entryRoom, exitRoom, new HashSet<>(), new HashSet<>());
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
                }
            }
        }

        //class C calls: explore as far as we can, even using any skeleton keys we might have


        return false;
    }

    public interface Builder {
        World build();
    }
}
