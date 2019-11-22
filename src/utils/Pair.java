package utils;

import java.io.Serializable;

public class Pair<T extends Serializable> implements Serializable {
    private final T itemOne;
    private final T itemTwo;
    public Pair(T itemA, T itemB){
        if(itemA == null || itemB == null)
            throw new IllegalArgumentException("Cannot have null items");

        this.itemOne = itemA;
        this.itemTwo = itemB;
    }
    public T itemA(){
        return itemOne;
    }
    public T itemB(){
        return itemTwo;
    }
    public T otherItem(T item){
        if(item == null)
            throw new IllegalArgumentException("item cannot be null");
        if(item.equals(itemOne))
            return itemTwo;
        return itemOne;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(obj instanceof Pair){
            Pair that = (Pair)obj;
            return this.itemOne.equals(that.itemOne) && this.itemTwo.equals(that.itemTwo);
        }
        return false;
    }
}