package maze.view.panel;
import maze.controller.GameEventListener;
import maze.controller.events.ResultEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.model.question.Question;
import maze.model.question.QuestionDataSource;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ResultReceiver;
import maze.view.ViewUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionMenuPanel extends Panel implements ResultReceiver, ActionListener {
	private GameEventListener listener;
	private QuestionDataSource dataSource;
	private ResultStep resultStep;
	
	private enum ResultStep {
		NEW_QUESTION,
		EDIT_QUESTION,
		DELETE_QUESTION,
		SAVE_EDIT_QUESTION,
		COMPLETE
	}

	private JButton buttonNew  = new JButton("Add New");
	private JButton buttonEdit  = new JButton("Edit Existing");
	private JButton buttonDelete  = new JButton("Delete Existing");
	private JButton buttonCancel  = new JButton("Cancel");
	
	public QuestionMenuPanel(GameEventListener listener, QuestionDataSource dataSource) {
		super(PanelType.QUESTION_MENU);
		this.listener = listener;
		this.dataSource = dataSource;

		setBackground(ViewUtils.backgroundColor);
		
		buttonNew.setFocusPainted(false);
		buttonEdit.setFocusPainted(false);
		buttonCancel.setFocusPainted(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(20, 0, 20, 0);

		JLabel labelHeading = new JLabel("Question Manager Menu");
		ViewUtils.insertComponent(this, gc, labelHeading,	0, 0, 1, 1, 800, 80);
		ViewUtils.insertComponent(this, gc, buttonNew, 		0, 1, 1, 1, 400, 80);
		ViewUtils.insertComponent(this, gc, buttonEdit, 	0, 2, 1, 1, 400, 80);
		ViewUtils.insertComponent(this, gc, buttonDelete, 	0, 3, 1, 1, 400, 80);
		ViewUtils.insertComponent(this, gc, buttonCancel, 	0, 4, 1, 1, 400, 80);
		
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
	public void actionPerformed(ActionEvent e) {
	    JButton buttonClicked = ((JButton) e.getSource());
	    
	    if (buttonClicked == buttonNew) {
	    	resultStep = ResultStep.NEW_QUESTION;
	    	ResultEvent event = new ResultEvent(QuestionEditorPanel.class, this, null);
	    	listener.onGameEvent(event);
	    } else if (buttonClicked == buttonEdit) {
	    	resultStep = ResultStep.EDIT_QUESTION;
	    	ResultEvent event = new ResultEvent(QuestionSelectorPanel.class, this, null);
	    	listener.onGameEvent(event);
	    } else if (buttonClicked == buttonDelete) {
	    	resultStep = ResultStep.DELETE_QUESTION;
	    	ResultEvent event = new ResultEvent(QuestionSelectorPanel.class, this, null);
	    	listener.onGameEvent(event);
		} else if (buttonClicked == buttonCancel) {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.MAIN_MENU);
			listener.onGameEvent(event);
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Command: " + buttonClicked.getText());
	    }		
	}

	@Override
	public void processResult(Object object) {
		if (object != null) {
			if (object instanceof Question) {
				Question question = (Question) object;
				if (resultStep == ResultStep.NEW_QUESTION) {
					resultStep = ResultStep.COMPLETE;
					dataSource.update(question);
				} else if (resultStep == ResultStep.EDIT_QUESTION) {
					resultStep = ResultStep.SAVE_EDIT_QUESTION;
			    	ResultEvent event = new ResultEvent(QuestionEditorPanel.class, this, question);
			    	listener.onGameEvent(event);
				} else if (resultStep == ResultStep.DELETE_QUESTION) {
					resultStep = ResultStep.COMPLETE;
					dataSource.delete(question);
				} else if (resultStep == ResultStep.SAVE_EDIT_QUESTION) {
					resultStep = ResultStep.COMPLETE;
					dataSource.update(question);
				}
				if (resultStep == ResultStep.COMPLETE) {
					SwitchPanelEvent event = new SwitchPanelEvent(PanelType.QUESTION_MENU);
					listener.onGameEvent(event);
				}
			}
		} else {
			SwitchPanelEvent event = new SwitchPanelEvent(PanelType.QUESTION_MENU);
			listener.onGameEvent(event);
		}	
	}
}