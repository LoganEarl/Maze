package maze.view;

import maze.Direction;
import maze.controller.Controller;
import maze.controller.events.MoveEvent;
import maze.controller.events.PauseMenuEvent;
import maze.controller.events.ZoomEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
	private Controller controller;
	private MainMenuPanel mainMenuPanel;
	private LoadingPanel loadingPanel;
	private GraphicsPanel graphicsPanel;
	private QuestionPanel questionPanel;
	private QuestionMenuPanel questionMenuPanel;
	private QuestionManagerPanel questionManagerPanel;

	public MainFrame(Controller controller) {
		super("DAbuggers Maze");
		this.controller = controller;

		mainMenuPanel = new MainMenuPanel(controller);
		loadingPanel = new LoadingPanel();
		graphicsPanel = new GraphicsPanel();
		questionPanel = new QuestionPanel(controller);
		questionMenuPanel = new QuestionMenuPanel(controller);
		questionManagerPanel = new QuestionManagerPanel(controller);

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
		add(questionPanel, gc);
		add(questionMenuPanel, gc);
		add(questionManagerPanel, gc);
		
		switchToPanel(Panel.MAINMENU);

		bindKeys();
	}
	
	public void bindKeys() {
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_W, "MoveNorth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.north);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_A, "MoveWest", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.west);
			controller.onGameEvent(moveEvent);
		});

		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_S, "MoveSouth", (e) -> {
			MoveEvent moveEvent = new MoveEvent(Direction.south);
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
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ADD, "ZoomIn", (e) -> {
			ZoomEvent event = new ZoomEvent(Zoom.in);
			controller.onGameEvent(event);
		});
		
		KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_SUBTRACT, "ZoomOut", (e) -> {
			ZoomEvent event = new ZoomEvent(Zoom.out);
			controller.onGameEvent(event);
		});
	}

	public GraphicsPanel getGraphicsPanel() {
		return graphicsPanel;
	}
	
	public QuestionPanel getQuestionPanel() {
		return questionPanel;
	}

	public void switchToPanel(Panel panel) {
		mainMenuPanel.setVisible(panel == Panel.MAINMENU);
		loadingPanel.setVisible(panel == Panel.LOADING);
		graphicsPanel.setVisible(panel == Panel.GRAPHICS);
		questionPanel.setVisible(panel == Panel.QUESTION);
		questionMenuPanel.setVisible(panel == Panel.QUESTION_MENU);
		questionManagerPanel.setVisible(panel == Panel.QUESTION_MANAGER);
	}

}
