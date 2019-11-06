import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainMenuPanel extends JPanel {
	public MainMenuPanel() {
		Color color = new Color(100,100,100);
		setBackground(color);
		
		JButton buttonNewGame  = new JButton("New Game");
		JButton buttonLoadGame  = new JButton("Load Game");
		
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
						Controller.newGame();
					}
				});
			}
		});		
	}
}
