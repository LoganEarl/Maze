package maze.view.panel;
import maze.model.question.Question;
import maze.model.question.QuestionDataSource;
import maze.view.Panel;
import maze.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuestionSelectorPanel extends Panel implements ResultProvider, ActionListener, KeyListener {
	private GridBagConstraints gc;
	private ResultReceiver resultReceiver;
	private List<Question> questions;
	private QuestionDataSource dataSource;

	private JTextField textFieldSearch = new JTextField("");
	private JScrollPane scrollPane;
	private JTable tableQuestions;
	private JButton buttonContinue  = new JButton("Continue");
	private JButton buttonCancel  = new JButton("Cancel");
	
	public QuestionSelectorPanel(QuestionDataSource dataSource) {
		super(PanelType.QUESTION_SELECTOR);
		setBackground(ViewUtils.backgroundColor);

		this.dataSource = dataSource;

		buttonContinue.setFocusPainted(false);
		buttonCancel.setFocusPainted(false);

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		
		ViewUtils.insertComponent(this, gc, new JLabel(""),		0, 0, 1, 1,  90,  10);
		ViewUtils.insertComponent(this, gc, new JLabel(""),		1, 0, 1, 1, 280,  10);
		ViewUtils.insertComponent(this, gc, new JLabel(""),		2, 0, 1, 1, 390,  10);

		JLabel labelHeading = new JLabel("Select A Question");
		ViewUtils.insertComponent(this, gc, labelHeading,		0, 0, 3, 1, 800,  60);
		JLabel labelSearch = new JLabel("Search:");
		ViewUtils.insertComponent(this, gc, labelSearch,		0, 1, 1, 1,  90,  40);
		ViewUtils.insertComponent(this, gc, textFieldSearch,	1, 1, 2, 1, 690,  40);
		ViewUtils.insertComponent(this, gc, buttonContinue, 	0, 3, 2, 1, 390,  80);
		ViewUtils.insertComponent(this, gc, buttonCancel, 		2, 3, 1, 1, 390,  80);
	
		ViewUtils.componentSetFont(labelHeading, 48);
		labelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeading.setForeground(ViewUtils.blueColor);
		
		ViewUtils.componentSetFont(labelSearch, 24);
		labelSearch.setForeground(ViewUtils.blueColor);
		
		ViewUtils.componentSetFont(textFieldSearch, 24);
		textFieldSearch.addKeyListener(this);

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
		reloadQuestions();
		this.resultReceiver = resultReceiver;	
	}
	
	private void reloadQuestions() {
		if (scrollPane != null) remove(scrollPane);
		
		questions = new ArrayList<>(dataSource.getAllQuestions());

		String[] columnNames = {"Question"};
		
		LinkedList<String> filteredList = new LinkedList<>();
		
		for (Question question : questions) {
			if (textFieldSearch.getText() == null || textFieldSearch.getText().equals("") || question.getPrompt().contains(textFieldSearch.getText())) {
				filteredList.addLast(question.getPrompt());
			}
		}

		String[][] arrQuestions = new String[filteredList.size()][1];
		for (int i = 0; i < filteredList.size(); i++) {
			arrQuestions[i][0] = "" + filteredList.get(i);
		}
		
		tableQuestions = new JTable(arrQuestions, columnNames);
		tableQuestions.setRowHeight(36);
		tableQuestions.setTableHeader(null);
		tableQuestions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableQuestions.setDefaultEditor(Object.class, null);
		ViewUtils.componentSetFont(tableQuestions, 24);	
		scrollPane = new JScrollPane(tableQuestions);
		ViewUtils.insertComponent(this, gc, scrollPane, 0, 2, 3, 1, 800, 580);
		
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
		    JButton buttonClicked = ((JButton) e.getSource());
	    	
		    if (buttonClicked == buttonContinue) {
		    	Question toProcess = null;
		    	int selected = tableQuestions.getSelectedRow();
		    	if (selected >= 0) {
		    		String questionText = (String) tableQuestions.getValueAt(selected, 0);
		    		for (Question question : questions) {
		    			if (question.getPrompt().contentEquals(questionText)) {
		    				toProcess = question;
		    			}
		    		}
		    	}
		    	resultReceiver.processResult(toProcess);
			} else if (buttonClicked == buttonCancel) {
				resultReceiver.processResult(null);
		    }	
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		reloadQuestions();
	}
}