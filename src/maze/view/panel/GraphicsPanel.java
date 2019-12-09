package maze.view.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import maze.Direction;
import maze.controller.Controller;
import maze.view.*;
import maze.model.Door;
import maze.model.Player;
import maze.model.Room;
import maze.model.World;

public class GraphicsPanel extends Panel implements View.MapDetailView, MouseWheelListener {
	private Controller controller;
    private ResourceManager resManager;

    public GraphicsPanel(Controller controller) {
        super(PanelType.GRAPHICS);
        this.controller = controller;

        setBackground(ViewUtils.backgroundColor);
        resManager = new ResourceManager();
        this.addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        draw(g);
    }

    private void draw(Graphics graphics) {
    	World drawnWorld = controller.getWorld();

        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.setColor(ViewUtils.backgroundColor);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        if (drawnWorld != null) {
            Player player = drawnWorld.getPlayer();

            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;

            Room curRoom = player.getCurrentRoom();
            int curScale = resManager.getScale();

            for (Room room : drawnWorld.getAllRooms()) {
                int posX = (int) (centerX + (room.getXCoordinate() - curRoom.getXCoordinate() - 0.5) * curScale);
                int posY = (int) (centerY + (room.getYCoordinate() - curRoom.getYCoordinate() - 0.5) * curScale);

                if (room == drawnWorld.getEntryRoom()) {
                    graphics.drawImage(resManager.getImage("Entry.png"), posX, posY, null);
                } else if (room == drawnWorld.getExitRoom()) {
                    graphics.drawImage(resManager.getImage("Exit.png"), posX, posY, null);
                } else {
                    graphics.drawImage(resManager.getImage("Floor.png"), posX, posY, null);
                }
                graphics.drawImage(resManager.getImage("Columns.png"), posX, posY, null);

                for (Direction direction : Direction.values()) {
                    graphics.drawImage(getDoorImage(room, player, direction), posX, posY, null);

                    if (room.hasDoor(direction)) {
                        Door door = room.getDoor(direction);
                        Room other = door.getOtherRoom(room);
                        
						if (room.getItems().size() > 0) {
							graphics.drawImage(resManager.getImage("Sack.png"), posX, posY, null);
						}
						
                        int room1X = room.getXCoordinate();
                        int room2X = other.getXCoordinate();
                        int room1Y = room.getYCoordinate();
                        int room2Y = other.getYCoordinate();

                        if (Math.abs(room1X - room2X) > 1 && direction == Direction.east) {
                            for (int x = Integer.signum(room1X - room2X); x != room1X - room2X; x += Integer.signum(room1X - room2X)) {
                                graphics.drawImage(resManager.getImage("Hall_Horizontal.png"), posX - x * curScale, posY, null);
                            }
                            graphics.drawImage(getDoorImage(room, player, direction), posX - (room1X - room2X + 1) * curScale, posY, null);
                            graphics.drawImage(getDoorImage(other, player, direction.opposite()), posX - Integer.signum(room1X - room2X) * curScale, posY, null);
                        }

                        if (Math.abs(room1Y - room2Y) > 1 && direction == Direction.north) {
                            for (int y = Integer.signum(room1Y - room2Y); y != room1Y - room2Y; y += Integer.signum(room1Y - room2Y)) {
                                graphics.drawImage(resManager.getImage("Hall_Vertical.png"), posX, posY - y * curScale, null);
                            }
                            graphics.drawImage(getDoorImage(room, player, direction), posX, posY - (room1Y - room2Y - 1) * curScale, null);
                            graphics.drawImage(getDoorImage(other, player, direction.opposite()), posX, posY - Integer.signum(room1Y - room2Y) * curScale, null);
                        }
                    }
                }

            }

            int posX = centerX - curScale / 2;
            int posY = centerY - curScale / 2;
            graphics.drawImage(resManager.getImage("Player_" + player.getFacing().name() + "_stand.png"), posX, posY, null);
        }
    }

    private Image getDoorImage(Room room, Player player, Direction direction) {
        String key = direction.name();

        if (room.hasDoor(direction)) {
            key = "Door_" + key;
            Door door = room.getDoor(direction);

            if (door.isOpen()) {
                key += "_Open";
            } else if (door.getKeyItem() != null && player.hasItem(door.getKeyItem())) {
            	key += "_Star";
            } else if (door.isLocked() && door.getKeyItem() != null) {
                key += "_Blocked";
            } else if (door.isLocked()) {
            	key += "_Locked";
            } else {
                key += "_Closed";
            }
        } else {
            key = "Wall_" + key;
        }

        key += ".png";

        return resManager.getImage(key);
    }

    public void zoomTo(Zoom zoom) {
        if (zoom == Zoom.in) {
            resManager.adjustScale(1);
        } else {
            resManager.adjustScale(-1);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            resManager.adjustScale(1);
        } else {
            resManager.adjustScale(-1);
        }
        repaint();
    }
}
