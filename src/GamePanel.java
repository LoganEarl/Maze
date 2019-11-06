import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import world.Direction;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GraphicsPanel graphicsPanel;

	public GamePanel() {
		Color color = new Color(200,100,100);
		setBackground(color);
		
		JButton buttonLoadGame  = new JButton("Cool");
		
		graphicsPanel = new GraphicsPanel();
		graphicsPanel.setSize(50, 50);
		graphicsPanel.setVisible(true);

		buttonLoadGame.setFocusPainted(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		add(graphicsPanel, gc);
		
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 0;
		add(buttonLoadGame, gc);
		
		buttonLoadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.move(Direction.north);
				graphicsPanel.repaint();
			}
		});
		
		addKeyBinding(this, KeyEvent.VK_W, "MoveNorth", (e) -> {
			Controller.move(Direction.north);
			graphicsPanel.repaint();
		});
		
		addKeyBinding(this, KeyEvent.VK_A, "MoveWest", (e) -> {
			Controller.move(Direction.west);
			graphicsPanel.repaint();
		});
		
		addKeyBinding(this, KeyEvent.VK_S, "MoveSouth", (e) -> {
			Controller.move(Direction.south);
			graphicsPanel.repaint();
		});
		
		addKeyBinding(this, KeyEvent.VK_D, "MoveEast", (e) -> {
			Controller.move(Direction.east);
			graphicsPanel.repaint();
		});
		
	}
	
	public static void addKeyBinding(JPanel panel, int keyCode, String id, ActionListener actionListener) {
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = panel.getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke(keyCode,  0, false), id);
		actionMap.put(id, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionListener.actionPerformed(e);				
			}
		});
	}

	public GraphicsPanel getGraphicsPanel() {
		return graphicsPanel;
	}
}
