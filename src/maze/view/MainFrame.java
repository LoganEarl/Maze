package maze.view;

import maze.Direction;
import maze.controller.GameEventListener;
import maze.controller.events.MoveEvent;
import maze.controller.events.PauseMenuEvent;
import maze.controller.events.ZoomEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame implements View {
    private GameEventListener listener;
    private MainMenuPanel mainMenuPanel;
    private LoadingPanel loadingPanel;
    private GraphicsPanel graphicsPanel;
    private QuestionPanel questionPanel;
    private QuestionMenuPanel questionMenuPanel;
    private QuestionManagerPanel questionManagerPanel;

    public MainFrame(GameEventListener listener) {
        super("DAbuggers Maze");
        this.listener = listener;

        mainMenuPanel = new MainMenuPanel(listener);
        loadingPanel = new LoadingPanel();
        graphicsPanel = new GraphicsPanel();
        questionPanel = new QuestionPanel(listener);
        questionMenuPanel = new QuestionMenuPanel(listener);
        questionManagerPanel = new QuestionManagerPanel(listener);

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

    private void bindKeys() {
        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_W, "MoveNorth", (e) -> {
            MoveEvent moveEvent = new MoveEvent(Direction.north);
            listener.onGameEvent(moveEvent);
        });

        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_A, "MoveWest", (e) -> {
            MoveEvent moveEvent = new MoveEvent(Direction.west);
            listener.onGameEvent(moveEvent);
        });

        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_S, "MoveSouth", (e) -> {
            MoveEvent moveEvent = new MoveEvent(Direction.south);
            listener.onGameEvent(moveEvent);
        });

        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_D, "MoveEast", (e) -> {
            MoveEvent moveEvent = new MoveEvent(Direction.east);
            listener.onGameEvent(moveEvent);
        });

        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ESCAPE, "PauseMenu", (e) -> {
            PauseMenuEvent event = new PauseMenuEvent();
            listener.onGameEvent(event);
        });

        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_ADD, "ZoomIn", (e) -> {
            ZoomEvent event = new ZoomEvent(Zoom.in);
            listener.onGameEvent(event);
        });

        KeyBinder.addKeyBinding(graphicsPanel, KeyEvent.VK_SUBTRACT, "ZoomOut", (e) -> {
            ZoomEvent event = new ZoomEvent(Zoom.out);
            listener.onGameEvent(event);
        });
    }

    public QuestionPanel getQuestionDetailView() {
        return questionPanel;
    }

    public MapDetailView getMapDetailView() {
    	return graphicsPanel;
	}

    public void switchToPanel(Panel panel) {
        SwingUtilities.invokeLater(()->{
			mainMenuPanel.setVisible(panel ==Panel.MAINMENU);
			loadingPanel.setVisible(panel ==Panel.LOADING);
			graphicsPanel.setVisible(panel ==Panel.GRAPHICS);
			questionPanel.setVisible(panel ==Panel.QUESTION);
			questionMenuPanel.setVisible(panel ==Panel.QUESTION_MENU);
			questionManagerPanel.setVisible(panel ==Panel.QUESTION_MANAGER);
		});
    }

}
