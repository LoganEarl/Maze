import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {
	private Image playerImage;
	private Room room;
	public int x = 0;
	public int y = 0;
	
	public Player(Room room) {
		this.room = room;
	}

	public Image getImage() {
		if (playerImage == null) {
			try {
				File pathToFile = new File("res/Character.png");
				playerImage = ImageIO.read(pathToFile);
				playerImage = playerImage.getScaledInstance(GraphicsPanel.scale, GraphicsPanel.scale, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return playerImage;
	}
}
