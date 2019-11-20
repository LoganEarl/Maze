package maze.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class KeyBinder {
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
	
	public static void removeKeyBinding(JPanel panel, String id) {
		ActionMap actionMap = panel.getActionMap();
		actionMap.remove(id);
	}
}
