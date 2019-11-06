import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import world.Direction;

public class Controller {
	private static MainFrame frame;
	private static GraphicsPanel graphicsPanel;
	private static Player player;
	private static LinkedList<Room> rooms;
	
	public static void main(String[] args) {		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame = new MainFrame("Trivia Maze");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(1280, 900);
				frame.setVisible(true);
			}
		});
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static LinkedList<Room> getRooms() {
		return rooms;
	}
	
	public static void newGame() {
		rooms = new LinkedList<Room>();
		for (int x = -5; x <= 5; x++) {
			for (int y = -5; y <= 5; y++) {
				Room room = new Room(x, y);
				rooms.add(room);
			}		
		}
		
		player = new Player(rooms.getFirst());
		
		graphicsPanel = frame.getGraphisPanel();
		graphicsPanel.initialize(player, rooms);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.switchToPanel(Panel.GAME);
			}
		});	
	}
	
	public static void move(Direction direction) {
		if (direction == Direction.north) {
			player.y -= 1;
		} else if (direction == Direction.south) {
			player.y += 1;
		} else if (direction == Direction.east) {
			player.x += 1;
		} else if (direction == Direction.west) {
			player.x -= 1;
		}
		graphicsPanel.repaint();
	}
}