package maze.view;

import maze.controller.GameEventListener;
import maze.controller.MazeController;
import maze.controller.events.MainMenuEvent;
import maze.model.question.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionManagerPanel extends JPanel implements ActionListener {
	private GameEventListener eventListener;
	private Question question;
	private GridBagConstraints gc;
	
	private JLabel labelQuestionType = new JLabel("Question Type");
	private JLabel labelQuestion = new JLabel("Question");
	private JLabel labelAnswers = new JLabel("Answers");
	private JLabel labelSpacer1 = new JLabel(" ");
	private JLabel labelSpacer2 = new JLabel(" ");
	
	private JComboBox<String> comboBoxQuestionType = new JComboBox<>();
	private JTextField textFieldQuestion = new JTextField("Question Test Here");
	private JTable tableAnswers = new JTable();
	
	private JButton buttonAdd = new JButton("Add");
	private JButton buttonEdit = new JButton("Edit");
	private JButton buttonDelete = new JButton("Delete");
	private JButton buttonMoveUp = new JButton("Move Up");
	private JButton buttonMoveDown = new JButton("Move Down");
	private JButton buttonSetCorrect = new JButton("Set Correct");
	private JButton buttonSave = new JButton("Save");
	private JButton buttonCancel = new JButton("Cancel");	
	
	public QuestionManagerPanel(GameEventListener eventListener) {
		this.eventListener = eventListener;
		
		Color color = new Color(33, 33, 33);
		setBackground(color);
				
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(10, 20, 10, 20);	
		
		buildQuestionElements();
	}
	
	private void buildQuestionElements() {
		insertComponent(labelQuestion, 			0, 0, 1, 1, 600, 30);
		insertComponent(textFieldQuestion, 		1, 0, 1, 1, 600, 200);
		insertComponent(labelSpacer1,			2, 0, 1, 1, 600, 40);
		insertComponent(labelAnswers, 			3, 0, 1, 1, 600, 30);
		insertComponent(tableAnswers, 			4, 0, 3, 1, 600, 200);
		insertComponent(labelSpacer2,			7, 0, 1, 1, 600, 40);
		insertComponent(buttonSave, 			8, 0, 1, 1, 600, 75);
		
		insertComponent(labelQuestionType, 		0, 1, 1, 2, 400, 30);
		insertComponent(comboBoxQuestionType, 	1, 1, 1, 2, 400, 50);
		insertComponent(buttonAdd,				4, 1, 1, 1, 180, 50);
		insertComponent(buttonEdit, 			5, 1, 1, 1, 180, 50);
		insertComponent(buttonDelete, 			6, 1, 1, 1, 180, 50);
		insertComponent(buttonCancel,		 	8, 1, 1, 2, 400, 75);
		
		insertComponent(buttonMoveUp,			4, 2, 1, 1, 180, 50);
		insertComponent(buttonMoveDown, 		5, 2, 1, 1, 180, 50);
		insertComponent(buttonSetCorrect, 		6, 2, 1, 1, 180, 50);
	}
	
	private void insertComponent(JComponent component, int gridy, int gridx, int spany, int spanx, int sizey, int sizex) {
		gc.gridy = gridy;
		gc.gridx = gridx;
		gc.gridheight = spany;
		gc.gridwidth = spanx;
		
		ViewUtils.componentSetFont(component, 24);
		ViewUtils.componentSetSize(component, 400, 80);
		component.setPreferredSize(new Dimension(sizey, sizex));
		component.setFont(component.getFont().deriveFont(24.0f));
		add(component, gc);
		
		if (component instanceof JButton){
			JButton button = (JButton) component;			
			button.setFocusPainted(false);
			ViewUtils.componentColorBorder(button, new Color(63, 106, 145));
			button.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String command = button.getText();
		
		if (command.equals("Add")) {
			
		} else if (command.equals("Edit")) {
			
		} else if (command.equals("Delete")) {
			
		} else if (command.equals("Move Up")) {
			
		} else if (command.equals("Move Down")) {
			
		} else if (command.equals("Set Correct")) {
			
		} else if (command.equals("Save")) {
			
		} else if (command.equals("Cancel")) {
			MainMenuEvent event = new MainMenuEvent();
			eventListener.onGameEvent(event);
		} else {
			JOptionPane.showMessageDialog(null, "Invalid Command: " + button.getText());
		}
	}
}