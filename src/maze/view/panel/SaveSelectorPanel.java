package maze.view.panel;
import maze.view.Panel;
import utils.ResultProvider;
import utils.ResultReceiver;
import maze.controller.GameEvent;
import maze.controller.events.LoadGameEvent;
import maze.controller.events.SaveGameEvent;
import maze.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.FileUtils.DATA_DIRECTORY;

public class SaveSelectorPanel extends Panel implements ResultProvider, ActionListener {
	private GridBagConstraints gc;
	private ResultReceiver resultReceiver;
	private SelectionFor selectionFor;

	private JLabel labelHeading = new JLabel("");
	private JButton[] buttonSaveSlot = new JButton[4];
	private JButton buttonCancel  = new JButton("Cancel");
	
	private enum SelectionFor {
		SAVE,
		LOAD
	}
	
	public SaveSelectorPanel() {
		super(PanelType.SAVE_SELECTOR);
		setBackground(ViewUtils.backgroundColor);

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(30, 10, 30, 10);
		
		for (int i = 0; i < buttonSaveSlot.length; i++) {
			buttonSaveSlot[i] = new JButton("Slot " + (i + 1));
		}
		
		ViewUtils.insertComponent(this, gc, labelHeading,		0, 0, 4, 1, 820,  60);
		ViewUtils.insertComponent(this, gc, buttonSaveSlot[0], 	0, 1, 1, 1, 190,  190);
		ViewUtils.insertComponent(this, gc, buttonSaveSlot[1], 	1, 1, 1, 1, 190,  190);
		ViewUtils.insertComponent(this, gc, buttonSaveSlot[2], 	2, 1, 1, 1, 190,  190);
		ViewUtils.insertComponent(this, gc, buttonSaveSlot[3], 	3, 1, 1, 1, 190,  190);
		ViewUtils.insertComponent(this, gc, buttonCancel, 		0, 2, 4, 1, 820,  80);
	
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
	public void getResult(ResultReceiver resultReceiver, Object object) {
		this.resultReceiver = resultReceiver;	
		if (object == SaveGameEvent.class) {
			selectionFor = SelectionFor.SAVE;
			labelHeading.setText("Select Slot To Save Game");
		} else if (object == LoadGameEvent.class) {
			selectionFor = SelectionFor.LOAD;
			labelHeading.setText("Select Slot To Load Game");
		}
		checkForSaves();
	}
	
	private void checkForSaves() {
		for (int i = 0; i < buttonSaveSlot.length; i++) {
			String saveGameFile = SaveGameEvent.getSaveFileName(i + 1);
			Path path = Paths.get(DATA_DIRECTORY, saveGameFile);
			File file = new File(path.toString());
			if (file.exists()) {
				buttonSaveSlot[i].setText("<html><center>" + "Slot" + (i + 1) + "<br>" + "(Save Exists)" + "</center></html>");
				buttonSaveSlot[i].setEnabled(true);
			} else {
				buttonSaveSlot[i].setText("<html><center>" + "Slot" + (i + 1) + "<br>" + "(Empty)" + "</center></html>");
				buttonSaveSlot[i].setEnabled(selectionFor == SelectionFor.SAVE);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
		    JButton buttonClicked = ((JButton) e.getSource());  
		    if (buttonClicked == buttonCancel) {
		    	resultReceiver.processResult(null);
		    } else {
				for (int i = 0; i < buttonSaveSlot.length; i++) {
					if (buttonClicked == buttonSaveSlot[i]) {
						GameEvent event = null;
						if (selectionFor == SelectionFor.SAVE) {
							event = new SaveGameEvent(i + 1);
						} else {
							event = new LoadGameEvent(i + 1);
						}
						resultReceiver.processResult(event);
					}
				}
		    }
		}
	}
}