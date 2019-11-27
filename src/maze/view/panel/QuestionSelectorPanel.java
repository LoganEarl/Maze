package maze.view.panel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import maze.controller.Controller;
import maze.model.question.Question;
import maze.model.question.SqLiteDatabase;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ResultProvider;
import maze.view.ResultReceiver;
import maze.view.ViewUtils;

public class QuestionSelectorPanel extends Panel implements ResultProvider, ActionListener, KeyListener {
	private Controller controller;
	private GridBagConstraints gc;
	private ResultReceiver resultReceiver;
	private List<Question> questions;

	private JLabel labelHeading = new JLabel("Select A Question");
	private JLabel labelSearch = new JLabel("Search:");
	private JTextField textFieldSearch = new JTextField("");
	private JScrollPane scrollPane;
	private JTable tableQuestions;
	private JButton buttonContinue  = new JButton("Continue");
	private JButton buttonCancel  = new JButton("Cancel");
	
	public QuestionSelectorPanel(Controller controller) {
		this.controller = controller;
		setBackground(ViewUtils.backgroundColor);
		
		buttonContinue.setFocusPainted(false);
		buttonCancel.setFocusPainted(false);

		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 10, 10);
		
		ViewUtils.insertComponent(this, gc, new JLabel(""),		0, 0, 1, 1,  90,  10);
		ViewUtils.insertComponent(this, gc, new JLabel(""),		1, 0, 1, 1, 280,  10);
		ViewUtils.insertComponent(this, gc, new JLabel(""),		2, 0, 1, 1, 390,  10);
		
		ViewUtils.insertComponent(this, gc, labelHeading,		0, 0, 3, 1, 800,  60);
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
	public PanelType getPanelType() {
		return PanelType.QUESTION_SELECTOR;
	}
	
	@Override
	public void getResult(ResultReceiver resultReceiver, Object object) {
		reloadQuestions();
		this.resultReceiver = resultReceiver;	
	}
	
	public void reloadQuestions() {
		if (scrollPane != null) remove(scrollPane);
		
		SqLiteDatabase db = new SqLiteDatabase("data/mazedb.sqlite3");
		questions = db.readAllRecords();
		
		String[] columnNames = {"Question"};
		
		LinkedList<String> filteredList = new LinkedList<>();
		
		for (Question question : questions) {
			if (textFieldSearch.getText() == null || textFieldSearch.getText().equals("") || question.getQuestion().contains(textFieldSearch.getText())) {
				filteredList.addLast(question.getQuestion());
			}
		}

		String arrQuestions[][] = new String[filteredList.size()][1];
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
		    			if (question.getQuestion().contentEquals(questionText)) {
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