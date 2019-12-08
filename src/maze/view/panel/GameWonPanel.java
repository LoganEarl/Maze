package maze.view.panel;

import maze.view.Panel;
import maze.controller.GameEvent;
import maze.controller.GameEventListener;
import maze.controller.events.SwitchPanelEvent;
import maze.view.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameWonPanel extends Panel implements ActionListener {
	private GameEventListener listener;
	private GridBagConstraints gc;

	private JButton buttonMainMenu  = new JButton("Go To Main Menu");
	
	public GameWonPanel(GameEventListener listener) {
		super(PanelType.GAME_WON);
		setBackground(ViewUtils.backgroundColor);
		this.listener = listener;

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);

		ViewUtils.insertComponent(this, gc, new JLabel(),		0, 1, 2, 1, 800,  700);
		ViewUtils.insertComponent(this, gc, buttonMainMenu, 	1, 2, 1, 1, 800,  80);

		buttonMainMenu.setFocusPainted(false);
		ViewUtils.componentSetFont(buttonMainMenu, 24);
		ViewUtils.componentColorBorder(buttonMainMenu, ViewUtils.blueColor);
		buttonMainMenu.addActionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(33, 33, 33));
		g.fillRect(0, 0, getWidth(), getHeight());
		File file = new File("res/win.png");
		try {
			Image image = ImageIO.read(file);
			g.drawImage(image, (this.getWidth() - image.getWidth(null)) / 2, (getHeight() - image.getHeight(null)) / 2 - 50, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
    	GameEvent event = new SwitchPanelEvent(PanelType.MAIN_MENU);	
    	listener.onGameEvent(event);    	
	}
}