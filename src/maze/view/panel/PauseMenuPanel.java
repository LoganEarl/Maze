package maze.view.panel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import maze.controller.GameEvent;
import maze.controller.GameEventListener;
import maze.controller.events.LoadGameEvent;
import maze.controller.events.ResultEvent;
import maze.controller.events.SaveGameEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ViewUtils;
import utils.ResultReceiver;

public class PauseMenuPanel extends Panel implements ResultReceiver, ActionListener {
	private GameEventListener listener;
	private GridBagConstraints gc;
	
	private JLabel labelHeading = new JLabel("Pause Menu");
	private JButton buttonContinue = new JButton("Continue");
	private JButton buttonSaveGame = new JButton("Save Game");
	private JButton buttonLoadGame = new JButton("Load Game");
	private JButton buttonControls = new JButton("View Controls");
	private JButton buttonQuit = new JButton("Quit To Main Menu");
	
	public PauseMenuPanel(GameEventListener listener) {
		super(PanelType.PAUSE_MENU);
		this.listener = listener;
		setBackground(ViewUtils.backgroundColor);
		
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(20, 0, 20, 0);
		
		insertAllComponents();
		initializeAllComponents();
	}

	private void insertAllComponents() {
		ViewUtils.insertComponent(this, gc, labelHeading, 		0, 0, 1, 1, 600, 100);
		ViewUtils.insertComponent(this, gc, buttonContinue, 	0, 1, 1, 1, 400,  80);
		ViewUtils.insertComponent(this, gc, buttonSaveGame, 	0, 2, 1, 1, 400,  80);
		ViewUtils.insertComponent(this, gc, buttonLoadGame, 	0, 3, 1, 1, 400,  80);
		ViewUtils.insertComponent(this, gc, buttonControls, 	0, 4, 1, 1, 400,  80);
		ViewUtils.insertComponent(this, gc, buttonQuit, 		0, 5, 1, 1, 400,  80);
	}
	
	private void initializeAllComponents() {
		ViewUtils.componentSetFont(labelHeading, 48);
		labelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeading.setForeground(ViewUtils.blueColor);
		
		for (Component component : getComponents()) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;			
				button.setFocusPainted(false);
				ViewUtils.componentSetFont(button, 24);
				ViewUtils.componentColorBorder(button, ViewUtils.blueColor);
				button.addActionListener(this);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton buttonClicked = ((JButton) e.getSource());

	    if (buttonClicked == buttonContinue) {
	    	GameEvent gameEvent = new SwitchPanelEvent(PanelType.GRAPHICS);
			listener.onGameEvent(gameEvent);
	    } else if (buttonClicked == buttonSaveGame) {
	    	GameEvent gameEvent = new ResultEvent(SaveSelectorPanel.class, this, SaveGameEvent.class);
	    	listener.onGameEvent(gameEvent);
	    } else if (buttonClicked == buttonLoadGame) {
	    	GameEvent gameEvent = new ResultEvent(SaveSelectorPanel.class, this, LoadGameEvent.class);
	    	listener.onGameEvent(gameEvent);
		} else if (buttonClicked == buttonQuit) {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.MAIN_MENU);
			listener.onGameEvent(event);
		} else if (buttonClicked == buttonControls) {
	    	GameEvent gameEvent = new ResultEvent(ControlsPanel.class, this, null);
	    	listener.onGameEvent(gameEvent);
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Command: " + buttonClicked.getText());
	    }		
	}

	@Override
	public void processResult(Object object) {
		if (object instanceof GameEvent) {
			GameEvent gameEvent = (GameEvent) object;
			listener.onGameEvent(gameEvent);
		} else {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.PAUSE_MENU);
			listener.onGameEvent(event);
		}
	}
}
