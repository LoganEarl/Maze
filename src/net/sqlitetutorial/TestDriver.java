package net.sqlitetutorial;

import maze.model.question.MazeDBDriver;

public class TestDriver {
    public static void main(String[] args) throws Exception {
        //Connect.connect();
    	
    	//MazeConsole.driver();
    	MazeDBDriver.driver(args);
    }
}
