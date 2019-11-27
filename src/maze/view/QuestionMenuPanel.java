package maze.view;

import maze.controller.GameEventListener;
import maze.controller.MazeController;
import maze.controller.GameEvent;
import maze.controller.events.NewGameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionMenuPanel extends JPanel {
	public QuestionMenuPanel(GameEventListener listener) {
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
		
		buttonNewGame.addActionListener(e -> SwingUtilities.invokeLater(() -> {
			GameEvent gameEvent = new NewGameEvent();
			listener.onGameEvent(gameEvent);
		}));
	}
}