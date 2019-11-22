package maze.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;

import maze.controller.Controller;
import maze.controller.events.*;
import maze.Direction;
import maze.model.Player;
import maze.model.World;

public class MainFrame extends JFrame implements View {
	private MainMenuPanel mainMenuPanel;
	private LoadingPanel loadingPanel;
	private GraphicsPanel graphicsPanel;

	public MainFrame(Controller controller) {
		super("Trivia Maze");

		mainMenuPanel = new MainMenuPanel(controller);
		loadingPanel = new LoadingPanel();
		graphicsPanel = new GraphicsPanel();

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;

		add(mainMenuPanel, gc);
		add(loadingPanel, gc);
		add(graphicsPanel, gc);

		mainMenuPanel.setVisible(true);
		loadingPanel.setVisible(false);
		graphicsPanel.setVisible(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 900);
		setVisible(true);

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_W, "MoveNorth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.south);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_A, "MoveWest", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.west);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_S, "MoveSouth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.north);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_D, "MoveEast", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.east);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ESCAPE, "PauseMenu", (e) -> {
			PauseMenuEvent event = new PauseMenuEvent();
			controller.onGameEvent(event);
		});
	}

	public void initialize(Player player, World world) {
		graphicsPanel.initialize(player, world);
		graphicsPanel.repaint();
	}

	public void switchToPanel(Panel panel) {
		SwingUtilities.invokeLater(() -> {
			mainMenuPanel.setVisible(panel == Panel.MAINMENU);
			loadingPanel.setVisible(panel == Panel.LOADING);
			graphicsPanel.setVisible(panel == Panel.GRAPHICS);
		});
	}
}
