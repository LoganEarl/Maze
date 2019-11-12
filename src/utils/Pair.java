package utils;

public class Pair<T> {
    private final T itemOne;
    private final T itemTwo;
    public Pair(T itemOne, T itemTwo){
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
    }
    public T itemA(){
        return itemOne;
    }
    public T itemB(){
        return itemTwo;
    }
    public T otherItem(T item){
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