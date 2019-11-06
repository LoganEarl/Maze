import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Room {
	private static Image defaultRoomImage;
	private Image roomImage;
	private int x;
	private int y;

	public Room(int x, int y) {
		this.x = x;
		this.y = y;
		getImage();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Image getImage() {
		if (defaultRoomImage == null) {
			try {
				File pathToFile = new File("res/RoomFloor.jpg");
				defaultRoomImage = ImageIO.read(pathToFile);
				defaultRoomImage = defaultRoomImage.getScaledInstance(GraphicsPanel.scale, GraphicsPanel.scale, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (roomImage == null) {
			roomImage = defaultRoomImage;
		}
		
		return roomImage;
	}
}
