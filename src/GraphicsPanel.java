import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import world.Direction;

public class GraphicsPanel extends JPanel {
	public static final int scale = 96;
	private Image fog;
	private Player player;
	private LinkedList<Room> rooms;

	public void initialize(Player player, LinkedList<Room> rooms) {
		this.player = player;
		this.rooms = rooms;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics graphics) {
		Color color = new Color(33, 33, 33);
		setBackground(color);
		
		if (player != null && rooms != null) {
			for (Room room : rooms) {
				if (Math.abs(room.getX() - player.x) <= 5 && Math.abs(room.getY() - player.y) <= 5) {
					int posX = this.getWidth() / 2 + room.getX() * scale - scale / 2 - player.x * scale;
					int posY = this.getHeight() / 2 + room.getY() * scale - scale / 2 - player.y * scale;
					graphics.drawImage(room.getImage(), posX, posY, null);
				}
			}
		}
		graphics.drawImage(player.getImage(), this.getWidth() / 2 - scale / 2 , this.getHeight() / 2 - scale / 2, null);
		graphics.drawImage(getFog(), this.getWidth() / 2 - scale * 11 / 2 , this.getHeight() / 2 - scale * 11 / 2, null);
		
	}
	
	void redraw() {
		repaint();
	}
	
	private Image getFog() {
		if (fog == null) {
			try {
				File pathToFile = new File("res/Fog.png");
				fog = ImageIO.read(pathToFile);
				fog = fog.getScaledInstance(GraphicsPanel.scale * 11, GraphicsPanel.scale * 11, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fog;
	}
}
