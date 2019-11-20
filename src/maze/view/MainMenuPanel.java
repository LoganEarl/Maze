package maze.view;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.controller.events.QuestionManagerEvent;
import maze.controller.events.NewGameEvent;

public class MainMenuPanel extends JPanel {
	private Controller controller;
	
	public MainMenuPanel(Controller controller) {
		this.controller = controller;
		
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
		JButton button  = new JButton("New Game");		
		formatButton(button, new Color(63, 106, 145));

		gc.gridy = 2;
		add(button, gc);

		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {		
					@Override
					public void run() {
						GameEvent gameEvent = new NewGameEvent();
						controller.onGameEvent(gameEvent);
					}
				});
			}
		});	
	}
	
	private void addLoadGameButton(GridBagConstraints gc) {
		JButton button  = new JButton("Load Game");
		formatButton(button, new Color(63, 106, 145));

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
		JButton button  = new JButton("Manage Questions");
		formatButton(button, new Color(63, 106, 145));

		gc.gridy = 4;
		add(button, gc);

		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {		
					@Override
					public void run() {
						QuestionManagerEvent event = new QuestionManagerEvent();
						controller.onGameEvent(event);
					}
				});
			}
		});	
	}
	
	private void formatButton(JButton button, Color color) {
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(400, 80));
		button.setFont(button.getFont().deriveFont(32.0f));
		button.setBorder(BorderFactory.createLineBorder(color, 5));
		button.setForeground(color);
	}
}
