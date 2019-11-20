package maze.view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import maze.controller.Controller;
import maze.model.question.MazeAnswer;
import maze.model.question.Question;
import maze.model.question.QuestionType;

public class QuestionPanel extends JPanel {
	private Controller controller;
	private Question question;
	
	private JLabel labelQuestion = new JLabel("<Question Goes Here>");
	private JTextField textFieldAns = new JTextField();
	private LinkedList<JButton> buttons = new LinkedList<JButton>();
	
	public QuestionPanel(Controller controller) {
		this.controller = controller;
		
		Color color = new Color(33, 33, 33);
		setBackground(color);
				
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridy = 0;
		add(labelQuestion, gc);
		
		gc.gridy = 1;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	
		labelQuestion.setText(question.getQuestion());
		
		buildAnswerButtons();
		constructPanel();
	}
	
	private void buildAnswerButtons() {
		buttons = new LinkedList<JButton>();
		
		for (MazeAnswer answer : question.getAnswers()) {
			JButton button = new JButton(answer.answer);
			buttons.addLast(button);
			button.setFocusPainted(false);
		}
	}
	
	private void constructPanel() {
		if (question.getType() == QuestionType.MULTIPLE) {

		} else if (question.getType() == QuestionType.TRUE_FALSE) {
			
		} else if (question.getType() == QuestionType.SHORT) {
			
		}
	}
}