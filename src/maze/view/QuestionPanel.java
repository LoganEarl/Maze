package maze.view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import maze.controller.Controller;
import maze.controller.events.CancelDoorEvent;
import maze.controller.events.QuestionAnsweredEvent;
import maze.model.question.Question;
import maze.model.question.QuestionType;

public class QuestionPanel extends JPanel implements ActionListener {
	private Controller controller;
	private Question question;
	private GridBagConstraints gc;
	
	private JTextPane textPaneQuestion = new JTextPane();
	private JTextField textFieldAnswer = new JTextField();
	private JLabel columnSizer[] = new JLabel[10];
	
	private JButton buttonAnswer[] = new JButton[4];
	private JButton buttonSubmit = new JButton("Submit");
	private JButton buttonUseItem = new JButton("Use Item / Just Open");
	private JButton buttonCancel = new JButton("Cancel");
	
	private JButton selectedButton;
	
	private Color unselectedColor = new Color(100, 100, 100);
	private Color selectedColor = new Color(80, 180, 80);
	
	public QuestionPanel(Controller controller) {
		this.controller = controller;
		
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
	
	public void setQuestion(Question question) {
		this.question = question;
		
		textPaneQuestion.setText(question.getQuestion());
		textFieldAnswer.setVisible(false);
		selectedButton = null;
		for (JButton button : buttonAnswer) {
			button.setVisible(false);
			ViewUtils.componentColorBorder(button, unselectedColor);
		}

		if (question.getType() == QuestionType.MULTIPLE) {
			for (int i = 0; i < question.getAnswers().size(); i++) {
				buttonAnswer[i].setText(question.getAnswers().get(i).getAnswer());
				buttonAnswer[i].setVisible(true);
			}
		} else if (question.getType() == QuestionType.TRUE_FALSE) {
			buttonAnswer[0].setText("True");
			buttonAnswer[1].setText("False");
			buttonAnswer[0].setVisible(true);
			buttonAnswer[1].setVisible(true);
		} else if (question.getType() == QuestionType.SHORT) {
			textFieldAnswer.setVisible(true);
			textFieldAnswer.setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton buttonClicked = ((JButton) e.getSource());

	    if (buttonClicked == buttonSubmit) {
	    	if (question.getType() == QuestionType.SHORT && !textFieldAnswer.getText().isEmpty()) {
	    		QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, textFieldAnswer.getText());
	    		controller.onGameEvent(event);
	    	} else if (selectedButton != null) {
	    		QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, selectedButton.getText());
	    		controller.onGameEvent(event);
	    	} else {
	    		JOptionPane.showMessageDialog(null, "Select An Answer");
	    	}  	
	    } else if (buttonClicked == buttonUseItem) {
	    	QuestionAnsweredEvent event = new QuestionAnsweredEvent(question, question.getCorrectAnswer());
	    	controller.onGameEvent(event);
	    } else if (buttonClicked == buttonCancel) {
	    	CancelDoorEvent event = new CancelDoorEvent();
			controller.onGameEvent(event);
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