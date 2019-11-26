package maze.view;

import maze.controller.Controller;
import maze.controller.GameEvent;
import maze.controller.events.NewGameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionMenuPanel extends JPanel {
	private Controller controller;
	
	public QuestionMenuPanel(Controller controller) {
		this.controller = controller;
		
		Color color = new Color(50,100,100);
		setBackground(color);
		
		JButton buttonNewGame  = new JButton("Add New Question");
		JButton buttonLoadGame  = new JButton("Edit Existing Question");
		
		buttonNewGame.setFocusPainted(false);
		buttonLoadGame.setFocusPainted(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		add(buttonNewGame, gc);
		
		gc.gridy = 1;
		add(buttonLoadGame, gc);
		
		buttonNewGame.addActionListener(new ActionListener() {		
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
}