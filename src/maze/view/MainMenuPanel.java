package maze.view;

import maze.controller.GameEventListener;
import maze.controller.GameEvent;
import maze.controller.events.NewGameEvent;
import maze.controller.events.QuestionManagerEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainMenuPanel extends JPanel {
	private GameEventListener listener;
	
	public MainMenuPanel(GameEventListener listener) {
		this.listener = listener;
		
		Color color = new Color(33 ,33, 33);
		setBackground(color);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.insets = new Insets(350, 512, 50, 10);	
		addNewGameButton(gc);
		
		gc.insets = new Insets(10, 512, 50, 10);	
		addLoadGameButton(gc);
		addManageQuestionsButton(gc);
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
	
	private void addNewGameButton(GridBagConstraints gc) {
		JButton button = new JButton("New Game");
		button.setFocusPainted(false);
		ViewUtils.componentSetFont(button, 32);
		ViewUtils.componentSetSize(button, 400, 80);
		ViewUtils.componentColorBorder(button, new Color(63, 106, 145));

		gc.gridy = 2;
		add(button, gc);

		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {		
					@Override
					public void run() {
						GameEvent gameEvent = new NewGameEvent();
						listener.onGameEvent(gameEvent);
					}
				});
			}
		});	
	}
	
	private void addLoadGameButton(GridBagConstraints gc) {
		JButton button = new JButton("Load Game");
		button.setFocusPainted(false);
		ViewUtils.componentSetFont(button, 32);
		ViewUtils.componentSetSize(button, 400, 80);
		ViewUtils.componentColorBorder(button, new Color(63, 106, 145));

		gc.gridy = 3;
		add(button, gc);

		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {		
					@Override
					public void run() {
						JOptionPane.showMessageDialog(null, "TODO");
					}
				});
			}
		});	
	}
	
	private void addManageQuestionsButton(GridBagConstraints gc) {
		JButton button = new JButton("Manage Questions");
		button.setFocusPainted(false);
		ViewUtils.componentSetFont(button, 32);
		ViewUtils.componentSetSize(button, 400, 80);
		ViewUtils.componentColorBorder(button, new Color(63, 106, 145));

		gc.gridy = 4;
		add(button, gc);

		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {		
					@Override
					public void run() {
						QuestionManagerEvent event = new QuestionManagerEvent();
						listener.onGameEvent(event);
					}
				});
			}
		});	
	}
}
