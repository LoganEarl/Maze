package maze.view;

import maze.controller.GameEventListener;
import maze.controller.MazeController;
import maze.controller.events.CancelDoorEvent;
import maze.controller.events.QuestionAnsweredEvent;
import maze.model.question.Question;
import maze.model.question.sqlite.BooleanQuestion;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static maze.model.question.sqlite.BooleanQuestion.TYPE_BOOLEAN;
import static maze.model.question.sqlite.MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE;
import static maze.model.question.sqlite.ShortResponseQuestion.TYPE_SHORT_RESPONSE;

public class QuestionPanel extends JPanel implements ActionListener, View.QuestionDetailView {
	private GameEventListener listener;
	private Question question;
	private GridBagConstraints gc;
	
	private JTextPane textPaneQuestion = new JTextPane();
	private JTextField textFieldAnswer = new JTextField();
	private JLabel[] columnSizer = new JLabel[10];
	
	private JButton[] buttonAnswer = new JButton[4];
	private JButton buttonSubmit = new JButton("Submit");
	private JButton buttonUseItem = new JButton("Use Item / Just Open");
	private JButton buttonCancel = new JButton("Cancel");
	
	private JButton selectedButton;
	
	private Color unselectedColor = new Color(100, 100, 100);
	private Color selectedColor = new Color(80, 180, 80);
	
	public QuestionPanel(GameEventListener listener) {
		this.listener = listener;
		
		Color color = new Color(33, 33, 33);
		setBackground(color);
		
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(20, 0, 20, 0);	
		
		SizeColumns();
		
		for (int i = 0; i < buttonAnswer.length; i++) {
			buttonAnswer[i] = new JButton("");
		}
		
		placeMainComponents();
		initializeMainComponents();
		
		placeAnswerButtonComponents();
		initializeAnswerButtonComponents();
	}
	
	private void SizeColumns() {
		for (int i = 0; i < columnSizer.length; i++) {
			columnSizer[i] = new JLabel("");
			gc.gridy = 0;
			gc.gridx = i;
			gc.gridheight = 1;
			gc.gridwidth = 1;
			ViewUtils.componentSetSize(columnSizer[i], 100, 1);
			add(columnSizer[i], gc);
		}
	}
	
	private void placeMainComponents() {
		gc.gridy = 0;
		gc.gridx = 0;
		gc.gridheight = 1;
		gc.gridwidth = 11;
		add(textPaneQuestion, gc);
		
		gc.gridy = 1;
		gc.gridx = 0;
		gc.gridheight = 2;
		gc.gridwidth = 11;
		add(textFieldAnswer, gc);
		
		gc.gridy = 3;
		gc.gridx = 0;
		gc.gridheight = 1;
		gc.gridwidth = 3;
		add(buttonSubmit, gc);
		
		gc.gridy = 3;
		gc.gridx = 4;
		gc.gridheight = 1;
		gc.gridwidth = 3;
		add(buttonUseItem, gc);
		
		gc.gridy = 3;
		gc.gridx = 8;
		gc.gridheight = 1;
		gc.gridwidth = 3;
		add(buttonCancel, gc);
	}
	
	private void initializeMainComponents() {
		ViewUtils.componentColorBorder(textPaneQuestion, unselectedColor);
		ViewUtils.componentSetSize(textPaneQuestion, 1100, 200);
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
		StyleConstants.setSpaceAbove(attributes, 20);
		StyleConstants.setFontFamily(attributes, "Default");
		StyleConstants.setFontSize(attributes, 36);
		textPaneQuestion.setParagraphAttributes(attributes, true);
		textPaneQuestion.setEditable(false);

		ViewUtils.componentColorBorder(textFieldAnswer, unselectedColor);
		ViewUtils.componentSetSize(textFieldAnswer, 1100, 300);
		ViewUtils.componentSetFont(textFieldAnswer, 36);
		
		ViewUtils.componentColorBorder(buttonSubmit, unselectedColor);
		ViewUtils.componentSetSize(buttonSubmit, 300, 100);
		ViewUtils.componentSetFont(buttonSubmit, 24);
		buttonSubmit.setFocusPainted(false);
		buttonSubmit.addActionListener(this);
		
		ViewUtils.componentColorBorder(buttonUseItem, unselectedColor);
		ViewUtils.componentSetSize(buttonUseItem, 300, 100);
		ViewUtils.componentSetFont(buttonUseItem, 24);
		buttonUseItem.setFocusPainted(false);
		buttonUseItem.addActionListener(this);

		ViewUtils.componentColorBorder(buttonCancel, unselectedColor);
		ViewUtils.componentSetSize(buttonCancel, 300, 100);
		ViewUtils.componentSetFont(buttonCancel, 24);
		buttonCancel.setFocusPainted(false);
		buttonCancel.addActionListener(this);
	}
	
	private void placeAnswerButtonComponents() {
		gc.gridy = 1;
		gc.gridx = 0;
		gc.gridheight = 1;
		gc.gridwidth = 5;
		add(buttonAnswer[0], gc);
		
		gc.gridy = 1;
		gc.gridx = 6;
		gc.gridheight = 1;
		gc.gridwidth = 5;
		add(buttonAnswer[1], gc);
		
		gc.gridy = 2;
		gc.gridx = 0;
		gc.gridheight = 1;
		gc.gridwidth = 5;
		add(buttonAnswer[2], gc);
		
		gc.gridy = 2;
		gc.gridx = 6;
		gc.gridheight = 1;
		gc.gridwidth = 5;
		add(buttonAnswer[3], gc);
	}
	
	private void initializeAnswerButtonComponents() {
		for (JButton button : buttonAnswer) {
			ViewUtils.componentColorBorder(button, unselectedColor);
			ViewUtils.componentSetSize(button, 500, 100);
			ViewUtils.componentSetFont(button, 24);
			button.setFocusPainted(false);
			button.addActionListener(this);
		}
	}

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
			ViewUtils.componentColorBorder(button, unselectedColor);
		}

		if (TYPE_MULTIPLE_CHOICE.equals(question.getQuestionType())) {
			for (int i = 0; i < question.getPossibleAnswers().length; i++) {
				buttonAnswer[i].setText(question.getPossibleAnswers()[i]);
				buttonAnswer[i].setVisible(true);
			}
		} else if (TYPE_BOOLEAN.equals(question.getQuestionType())) {
			buttonAnswer[0].setText(BooleanQuestion.TEXT_TRUE);
			buttonAnswer[1].setText(BooleanQuestion.TEXT_FALSE);
			buttonAnswer[0].setVisible(true);
			buttonAnswer[1].setVisible(true);
		} else if (TYPE_SHORT_RESPONSE.equals(question.getQuestionType())) {
			textFieldAnswer.setVisible(true);
			textFieldAnswer.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton buttonClicked = ((JButton) e.getSource());

	    if (buttonClicked == buttonSubmit) {
	    	if (TYPE_SHORT_RESPONSE.equals(question.getQuestionType()) && !textFieldAnswer.getText().isEmpty()) {
	    		QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, textFieldAnswer.getText());
	    		listener.onGameEvent(event);
	    	} else if (selectedButton != null) {
	    		QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, selectedButton.getText());
				listener.onGameEvent(event);
	    	} else {
	    		JOptionPane.showMessageDialog(null, "Select An Answer");
	    	}  	
	    } else if (buttonClicked == buttonUseItem) {
	    	QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, question.getCorrectAnswer());
			listener.onGameEvent(event);
	    } else if (buttonClicked == buttonCancel) {
	    	CancelDoorEvent event = new CancelDoorEvent();
			listener.onGameEvent(event);
		} else {
			for (JButton button : buttonAnswer) {
				if (button != buttonClicked) {
					ViewUtils.componentColorBorder(button, unselectedColor);
				} else {
					ViewUtils.componentColorBorder(button, selectedColor);
					selectedButton = button;
				}
			}
	    }	
	}
}