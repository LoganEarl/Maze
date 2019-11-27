package maze.view.panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ViewUtils;
import maze.controller.events.NewGameEvent;

public class MainMenuPanel extends Panel implements ActionListener {
	private Controller controller;
	private GridBagConstraints gc;
	
	private JButton buttonNewGame = new JButton("New Game");
	private JButton buttonLoadGame = new JButton("Load Game");
	private JButton buttonManageQuestions = new JButton("Manage Questions");
	
	public MainMenuPanel(Controller controller) {
		this.controller = controller;
		setBackground(ViewUtils.backgroundColor);
		
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(20, 0, 20, 0);
		
		insertAllComponents();
		initializeAllComponents();
	}
	
	@Override
	public PanelType getPanelType() {
		return PanelType.MAIN_MENU;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(33, 33, 33));
		g.fillRect(0, 0, getWidth(), getHeight());
		File file = new File("res/player.png");
		try {
			Image image = ImageIO.read(file);
			g.drawImage(image, this.getWidth() / 2 - 800, getHeight() / 2 - 512, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		file = new File("res/title.png");
		try {
			Image image = ImageIO.read(file);
			g.drawImage(image, this.getWidth() / 2 - 50, getHeight() / 2 - 350, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insertAllComponents() {
		ViewUtils.insertComponent(this, gc, new JLabel(" "), 		0, 0, 1, 1, 520, 300);
		ViewUtils.insertComponent(this, gc, buttonNewGame, 			1, 1, 1, 1, 400,  80);
		ViewUtils.insertComponent(this, gc, buttonLoadGame, 		1, 2, 1, 1, 400,  80);
		ViewUtils.insertComponent(this, gc, buttonManageQuestions, 	1, 3, 1, 1, 400,  80);
	}
	
	private void initializeAllComponents() {
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

	    if (buttonClicked == buttonNewGame) {
	    	GameEvent gameEvent = new NewGameEvent();
			controller.onGameEvent(gameEvent);
	    } else if (buttonClicked == buttonLoadGame) {
	    	JOptionPane.showMessageDialog(null, "TODO");
		} else if (buttonClicked == buttonManageQuestions) {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.QUESTION_MENU);
			controller.onGameEvent(event);
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Command: " + buttonClicked.getText());
	    }		
	}
}
