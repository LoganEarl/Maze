import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import world.Direction;

public class MainFrame extends JFrame {
	private MainMenuPanel mainMenuPanel;
	private LoadingPanel loadingPanel;
	private GamePanel gamePanel;

	public MainFrame(String title) {
		super(title);
		
		mainMenuPanel = new MainMenuPanel();
		loadingPanel = new LoadingPanel();
		gamePanel = new GamePanel();
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		
		add(mainMenuPanel, gc);
		add(loadingPanel, gc);
		add(gamePanel, gc);
		
		mainMenuPanel.setVisible(true);
		loadingPanel.setVisible(false);
		gamePanel.setVisible(false);
	}
	
	public GraphicsPanel getGraphisPanel() {
		return gamePanel.getGraphicsPanel();
	}
	
	public void switchToPanel(Panel panel) {
		mainMenuPanel.setVisible(panel == Panel.MAINMENU);
		loadingPanel.setVisible(panel == Panel.LOADING);
		gamePanel.setVisible(panel == Panel.GAME);
	}
	
	public void updateGameState(Player player, LinkedList<Room> visibleRooms) {
		GraphicsPanel graphicsPanel = gamePanel.getGraphicsPanel();
		
		graphicsPanel.redraw();
	}
}
