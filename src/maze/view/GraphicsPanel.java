package maze.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

import maze.Direction;
import maze.Zoom;
import maze.model.Door;
import maze.model.Player;
import maze.model.Room;
import maze.model.World;

public class GraphicsPanel extends JPanel implements MouseWheelListener {
	private ResourceManager resManager;
	private World world;

	public GraphicsPanel() {
		super();
		try {
			resManager = new ResourceManager();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		this.addMouseWheelListener(this);
	}

	public void setWorld(World world) {
		this.world = world;
	}

	@Override
	protected void paintComponent(Graphics g) {
		draw(g);
	}

	public void draw(Graphics graphics) {
		graphics.clearRect(0, 0, getWidth(), getHeight());
		graphics.setColor(new Color(33, 33, 33));
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		if (world != null) {			
			Player player = world.getPlayer();
			
			int centerX = this.getWidth() / 2;
			int centerY = this.getHeight() / 2;

			Room curRoom = player.getCurrentRoom();
			int curScale = resManager.getScale();
			
//			System.out.println("Player: X:" + curRoom.getXCoordinate() + ", Y:" + curRoom.getYCoordinate());
//			
//			Door temp = curRoom.getDoor(Direction.north);
//			if (temp != null) System.out.println("Door North: X:" + temp.getOtherRoom(curRoom).getXCoordinate() + ", Y:" + temp.getOtherRoom(curRoom).getYCoordinate());
//			temp = curRoom.getDoor(Direction.south);
//			if (temp != null) System.out.println("Door South: X:" + temp.getOtherRoom(curRoom).getXCoordinate() + ", Y:" + temp.getOtherRoom(curRoom).getYCoordinate());
//			temp = curRoom.getDoor(Direction.east);
//			if (temp != null) System.out.println("Door East: X:" + temp.getOtherRoom(curRoom).getXCoordinate() + ", Y:" + temp.getOtherRoom(curRoom).getYCoordinate());
//			temp = curRoom.getDoor(Direction.west);
//			if (temp != null) System.out.println("Door West: X:" + temp.getOtherRoom(curRoom).getXCoordinate() + ", Y:" + temp.getOtherRoom(curRoom).getYCoordinate());			

			if (world != null) {
				for (Room room : world.getAllRooms()) {
					int posX = (int) (centerX + (room.getXCoordinate() - curRoom.getXCoordinate() - 0.5) * curScale);
					int posY = (int) (centerY + (room.getYCoordinate() - curRoom.getYCoordinate() - 0.5) * curScale);
					
					//System.out.println("X:" + room.getXCoordinate() + ", Y:" + room.getYCoordinate());
					
					graphics.drawImage(resManager.getImage("Floor.png"), posX, posY, null);
					graphics.drawImage(resManager.getImage("Columns.png"), posX, posY, null);

					for (Direction direction : Direction.values()) {
						graphics.drawImage(getDoorImage(room, direction), posX, posY, null);
						
						if (room.hasDoor(direction)) {
							Door door = room.getDoor(direction);
							Room other = door.getOtherRoom(room);
							
							int room1X = room.getXCoordinate();
							int room2X = other.getXCoordinate();
							int room1Y = room.getYCoordinate();
							int room2Y = other.getYCoordinate();
							
							if (Math.abs(room1X - room2X) > 1 && direction == Direction.east) {
								for (int x = Integer.signum(room1X - room2X); x != room1X - room2X; x += Integer.signum(room1X - room2X)) {
									graphics.drawImage(resManager.getImage("Hall_Horizontal.png"), posX - x * curScale, posY, null);
								}
								graphics.drawImage(getDoorImage(room, direction), posX - (room1X - room2X + 1) * curScale, posY, null);
								graphics.drawImage(getDoorImage(other, direction.opposite()), posX - Integer.signum(room1X - room2X) * curScale, posY, null);
							}
							
							if (Math.abs(room1Y - room2Y) > 1 && direction == Direction.north) {
								for (int y = Integer.signum(room1Y - room2Y); y != room1Y - room2Y; y += Integer.signum(room1Y - room2Y)) {
									graphics.drawImage(resManager.getImage("Hall_Vertical.png"), posX, posY - y * curScale, null);
								}
								graphics.drawImage(getDoorImage(room, direction), posX, posY - Integer.signum(room1Y - room2Y) * curScale, null);
								graphics.drawImage(getDoorImage(other, direction.opposite()), posX, posY - Integer.signum(room1Y - room2Y) * curScale, null);
							}
						}
					}
					
				}
			}
			
			int posX = centerX - curScale / 2;
			int posY = centerY - curScale / 2;
			graphics.drawImage(resManager.getImage("Player_" + player.getFacing().name() + "_stand.png"), posX, posY, null);
			
			// graphics.drawImage(getFog(), this.getWidth() / 2 - scale * 11 / 2, this.getHeight() / 2 - scale * 11 / 2, null);
		}
	}

	public Image getDoorImage(Room room, Direction direction) {
		String key = direction.name();

		if (room.hasDoor(direction)) {
			key = "Door_" + key;
			Door door = room.getDoor(direction);
			
			if (door.isOpen()) {
				key += "_Open";
			} else {
				key += "_Closed";
			}
		} else {
			key = "Wall_" + key;
		}

		key += ".png";

		return resManager.getImage(key);
	}
	
	public void zoomGraphics(Zoom zoom) {
		if (zoom == Zoom.in) {
			resManager.adjustScale(64);
		} else {
			resManager.adjustScale(-64);
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			resManager.adjustScale(64);
		} else {
			resManager.adjustScale(-64);
		}
		repaint();
	}
}
