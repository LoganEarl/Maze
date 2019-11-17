package maze;

public enum Direction {
    north(0,-1),
	south(0,1),
	east(1,0),
	west(-1,0);

    private int xDelta, yDelta;
    Direction(int xDelta, int yDelta) {
    	this.xDelta = xDelta;
    	this.yDelta = yDelta;
	}

	public Direction opposite() {
	    return
	        this == north ? south :
        	this == south ? north :
	        this == east  ? west :
	                        east;
	}

	public int getXDelta() {
		return xDelta;
	}

	public int getYDelta() {
		return yDelta;
	}
}
