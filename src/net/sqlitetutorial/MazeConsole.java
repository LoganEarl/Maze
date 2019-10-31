package net.sqlitetutorial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MazeConsole {
	
	//here is a comment
	public static void driver() throws Exception {
		String name = "maze2.txt";
		
		//char[][] maze = loadMaze(name);
		char[][] maze = generateMaze(3, 3);
		
		print(maze);
		
		boolean foundPath = findPath(maze, 0, 0);
		
		if(foundPath)
		{
			System.out.println("The Maze has a path from start to exit");
			print(maze);
		}
		else
		{
			System.out.println("Maze has no solutions");
		}
		
		
	}
	
	static Random rand = new Random(); 
	
	public static char[][] generateMaze(int x, int y)
	{
		char[][] maze = new char[x][y];
		
		for(int i = 0; i < maze.length; i++)
		{
			for(int j = 0; j < maze[0].length; j++)
			{
				double k = rand.nextDouble();
				
				maze[i][j] = '.';
				
				if(k > 0.75) {
					maze[i][j] = '#';
				}		
			}
		}
		
		maze[0][0] = 'S';
		maze[x-1][y-1] = 'G';
		
		return maze;
	}

	public static char[][] loadMaze(String fileName) throws Exception{
		//dementions 4x4
		//*www
		//*w**
		//***w
		//wwww
		char[][] maze = null;
		
		ArrayList<String> lines = MazeConsole.readAllLines(fileName);
		
		String[] dim = lines.get(0).split(" ");
		if(dim.length != 2)
			throw new Exception("Dimentions not found");
		
		int x = Integer.parseInt(dim[0]);
		int y = Integer.parseInt(dim[1]);
		
		maze = new char[x][y];
		
		lines.remove(0);

		for(int i = 0; i < maze.length; i++)
		{
			String line = lines.get(i);
			
			for(int j = 0; j < maze[i].length; j++)
			{
				maze[i][j] = line.charAt(j);
			}
		}
		
		return maze;
		
	}
	
	public static ArrayList<String> readAllLines(String filename) throws FileNotFoundException
	{
		ArrayList<String> list = new ArrayList<String>();
		
		Scanner scanner = new Scanner(new File(filename));
		
		while(scanner.hasNext())
		{
			String var = scanner.nextLine();
			list.add(var);
		}
		
		scanner.close();
		
		return list;
	}
	
	public static boolean isValid(char[][] maze, int x, int y) {
		
		boolean isValid = true;
		
		if(x < 0 || y < 0)
			isValid = false;
		
		if(x >= maze.length || y >= maze[0].length)
			isValid = false;
		
		//System.out.printf("[%d, %d](%d, %d) = %s\n", x, y, maze.length, maze[0].length, isValid);
		return isValid;
	}
	public static boolean findPath(char[][] maze, int x, int y)
	{
		if(!isValid(maze, x, y))
			return false;
		
		char ch = maze[x][y];
		if(ch == 'S' || ch == '.')
		{
		
		}
		else if(ch == '#')
		{
			return false;
		}
		else if(ch == 'x')
		{
			return false;
		}
		else if(ch == 'G')
			return true;
		
		maze[x][y] = 'x';
		
		boolean found = findPath(maze, x-1, y);
		
		if(!found) 
			found = findPath(maze, x, y-1);
		
		if(!found)
			found = findPath(maze, x+1, y);
		
		if(!found)
			found = findPath(maze, x, y+1);
		
		if(found)
			maze[x][y] = '*';
		
		
		return found;
	}
	
	public static void print(char[][] maze)
	{
		for(int i = 0; i < maze.length; i++)
		{
			
			for(int j = 0; j < maze[i].length; j++)
			{
				System.out.print(maze[i][j]);
			}
			
			System.out.println();
		}
	}
	
}
