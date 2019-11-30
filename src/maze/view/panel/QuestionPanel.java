package maze.view.panel;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import maze.controller.GameEventListener;
import maze.controller.events.QuestionAnsweredEvent;
import maze.controller.events.SwitchPanelEvent;
import maze.model.question.Question;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.View;
import maze.view.ViewUtils;

import static maze.model.question.sqlite.BooleanQuestion.TYPE_BOOLEAN;
import static maze.model.question.sqlite.MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE;
import static maze.model.question.sqlite.ShortResponseQuestion.TYPE_SHORT_RESPONSE;

public class QuestionPanel extends Panel implements ActionListener, View.QuestionDetailView {
	private GameEventListener listener;
	private Question question;
	private GridBagConstraints gc;
	
	private JTextPane textPaneQuestion = new JTextPane();
	private JTextField textFieldAnswer = new JTextField();

	private JButton[] buttonAnswer = new JButton[4];
	private JButton buttonSubmit = new JButton("Submit");
	private JButton buttonUseItem = new JButton("Use Item / Just Open");
	private JButton buttonCancel = new JButton("Cancel");
	
	private JButton selectedButton;
	
	public QuestionPanel(GameEventListener listener) {
		super(PanelType.QUESTION);
		this.listener = listener;
		
		Color color = new Color(33, 33, 33);
		setBackground(color);
		
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(20, 0, 20, 0);	
		
		for (int i = 0; i < buttonAnswer.length; i++) {
			buttonAnswer[i] = new JButton("");
		}
		
		SizeColumns();
		insertAllComponents();
		initializeAllComponents();
	}
	
	private void SizeColumns() {
		for (int i = 0; i < 11; i++) {
			ViewUtils.insertComponent(this, gc, new JLabel(""), i, 0, 1, 1, 100, 1);
		}
	}
	
	private void insertAllComponents() {
		ViewUtils.insertComponent(this, gc, textPaneQuestion, 		0, 0, 11, 1, 1100, 200);
		ViewUtils.insertComponent(this, gc, textFieldAnswer, 		0, 1, 11, 2, 1100, 200);	
		ViewUtils.insertComponent(this, gc, buttonAnswer[0], 		0, 1,  5, 1,  500, 100);
		ViewUtils.insertComponent(this, gc, buttonAnswer[1], 		6, 1,  5, 1,  500, 100);
		ViewUtils.insertComponent(this, gc, buttonAnswer[2], 		0, 2,  5, 1,  500, 100);
		ViewUtils.insertComponent(this, gc, buttonAnswer[3], 		6, 2,  5, 1,  500, 100);	
		ViewUtils.insertComponent(this, gc, buttonSubmit, 			0, 3,  3, 1,  300, 100);
		ViewUtils.insertComponent(this, gc, buttonUseItem, 			4, 3,  3, 1,  300, 100);
		ViewUtils.insertComponent(this, gc, buttonCancel, 			8, 3,  3, 1,  300, 100);
	}
	
	private void initializeAllComponents() {
		ViewUtils.componentColorBorder(textPaneQuestion, ViewUtils.unselectedColor);
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
		StyleConstants.setSpaceAbove(attributes, 20);
		StyleConstants.setFontFamily(attributes, "Default");
		StyleConstants.setFontSize(attributes, 36);
		textPaneQuestion.setParagraphAttributes(attributes, true);
		textPaneQuestion.setEditable(false);

		ViewUtils.componentColorBorder(textFieldAnswer, ViewUtils.unselectedColor);
		textFieldAnswer.setHorizontalAlignment(JTextField.CENTER);
		ViewUtils.componentSetFont(textFieldAnswer, 36);
		
		for (Component component : getComponents()) {
			if (component instanceof JButton) {
				JButton button = (JButton) component;			
				button.setFocusPainted(false);
				ViewUtils.componentSetFont(button, 24);
				ViewUtils.componentColorBorder(button, ViewUtils.unselectedColor);
				button.addActionListener(this);
			}
		}
	}

	@Override
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
		
		textPaneQuestion.setText(question.getPrompt());
		textFieldAnswer.setVisible(false);
		selectedButton = null;
		for (JButton button : buttonAnswer) {
			button.setVisible(false);
			ViewUtils.componentColorBorder(button, ViewUtils.unselectedColor);
		}

		switch (question.getQuestionType()) {
			case TYPE_MULTIPLE_CHOICE:
				for (int i = 0; i < question.getPossibleAnswers().length; i++) {
					buttonAnswer[i].setText(question.getPossibleAnswers()[i]);
					buttonAnswer[i].setVisible(true);
				}
				break;
			case TYPE_BOOLEAN:
				buttonAnswer[0].setText("True");
				buttonAnswer[1].setText("False");
				buttonAnswer[0].setVisible(true);
				buttonAnswer[1].setVisible(true);
				break;
			case TYPE_SHORT_RESPONSE:
				textFieldAnswer.setVisible(true);
				textFieldAnswer.setText("");
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton buttonClicked = ((JButton) e.getSource());

	    if (buttonClicked == buttonSubmit) {
	    	if (question.getQuestionType().equals(TYPE_SHORT_RESPONSE) && !textFieldAnswer.getText().isEmpty()) {
	    		QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, textFieldAnswer.getText());
	    		listener.onGameEvent(event);
	    	} else if (selectedButton != null) {
	    		QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, selectedButton.getText());
	    		listener.onGameEvent(event);
	    	}  	
	    } else if (buttonClicked == buttonUseItem) {
	    	QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, question.getCorrectAnswer());
	    	listener.onGameEvent(event);
	    } else if (buttonClicked == buttonCancel) {
	    	SwitchPanelEvent event = new SwitchPanelEvent(PanelType.GRAPHICS);
			listener.onGameEvent(event);
		} else {
			for (JButton button : buttonAnswer) {
				if (button != buttonClicked) {
					ViewUtils.componentColorBorder(button, ViewUtils.unselectedColor);
				} else {
					ViewUtils.componentColorBorder(button, ViewUtils.selectedColor);
					selectedButton = button;
				}
			}
	    }	
	}
}