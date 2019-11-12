package maze;

public enum Direction {
    north, south, east, west;
	
	public Direction opposite() {
	    return
	        this == north ? south :
        	this == south ? north :
	        this == east  ? west :
	                        east;
	}
}
