package maze.view.panel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import maze.controller.Controller;
import maze.model.question.Answer;
import maze.model.question.MazeQuestion;
import maze.model.question.Question;
import maze.model.question.QuestionType;
import maze.model.question.SqLiteDatabase;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ResultProvider;
import maze.view.ResultReceiver;
import maze.view.ViewUtils;

public class QuestionEditorPanel extends Panel implements ResultProvider, ActionListener {
	private Controller controller;
	private ResultReceiver resultReceiver;
	private Question question;
	private QuestionType questionType = QuestionType.MULTIPLE;
	private GridBagConstraints gc;
	
	private JLabel labelQuestionType = new JLabel("Question Type");
	private JLabel labelQuestion = new JLabel("Question");
	private JLabel labelAnswers = new JLabel("Answers");
	
	private JComboBox<QuestionType> comboBoxQuestionType = new JComboBox<>();
	private JTextPane textFieldQuestion = new JTextPane();
	private JTable tableAnswers = new JTable(new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0));
	private JScrollPane scrollAnswers = new JScrollPane(tableAnswers);
	
	private JButton buttonAdd = new JButton("Add");
	private JButton buttonEdit = new JButton("Edit");
	private JButton buttonDelete = new JButton("Delete");
	private JButton buttonMoveUp = new JButton("Move Up");
	private JButton buttonMoveDown = new JButton("Move Down");
	private JButton buttonSetCorrect = new JButton("Set Correct");
	private JButton buttonSave = new JButton("Save");
	private JButton buttonCancel = new JButton("Cancel");	
	
	public QuestionEditorPanel(Controller controller) {
		this.controller = controller;
		setBackground(ViewUtils.backgroundColor);
				
		setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.NORTH;
		gc.insets = new Insets(10, 20, 10, 20);	
		
		insertAllComponents();
		initializeAllComponents();
	}
	
	@Override
	public PanelType getPanelType() {
		return PanelType.QUESTION_EDITOR;
	}
	
	private void insertAllComponents() {
		ViewUtils.insertComponent(this, gc, labelQuestion, 			0, 0, 1, 1, 600,  30);
		ViewUtils.insertComponent(this, gc, textFieldQuestion, 		0, 1, 1, 1, 600, 200);
		ViewUtils.insertComponent(this, gc, new JLabel(""),			0, 2, 1, 1, 600,  40);
		ViewUtils.insertComponent(this, gc, labelAnswers, 			0, 3, 1, 1, 600,  30);
		ViewUtils.insertComponent(this, gc, scrollAnswers, 			0, 4, 1, 3, 600, 200);
		ViewUtils.insertComponent(this, gc, new JLabel(""),			0, 7, 1, 1, 600,  40);
		ViewUtils.insertComponent(this, gc, buttonSave, 			0, 8, 1, 1, 600,  75);
		
		ViewUtils.insertComponent(this, gc, labelQuestionType, 		1, 0, 2, 1, 400,  30);
		ViewUtils.insertComponent(this, gc, comboBoxQuestionType, 	1, 1, 2, 1, 400,  50);
		ViewUtils.insertComponent(this, gc, buttonAdd,				1, 4, 1, 1, 180,  50);
		ViewUtils.insertComponent(this, gc, buttonEdit, 			1, 5, 1, 1, 180,  50);
		ViewUtils.insertComponent(this, gc, buttonDelete, 			1, 6, 1, 1, 180,  50);
		ViewUtils.insertComponent(this, gc, buttonCancel,		 	1, 8, 2, 1, 400,  75);
		
		ViewUtils.insertComponent(this, gc, buttonMoveUp,			2, 4, 1, 1, 180,  50);
		ViewUtils.insertComponent(this, gc, buttonMoveDown, 		2, 5, 1, 1, 180,  50);
		ViewUtils.insertComponent(this, gc, buttonSetCorrect, 		2, 6, 1, 1, 180,  50);
	}
	
	private void initializeAllComponents() {
		for (Component component : getComponents()) {
			if (component instanceof JComponent) {
				ViewUtils.componentSetFont((JComponent) component, 24);
			}
			if (component instanceof JButton) {
				JButton button = (JButton) component;			
				button.setFocusPainted(false);
				ViewUtils.componentColorBorder(button, ViewUtils.blueColor);
				button.addActionListener(this);
			}
		}
		
		//labelQuestion
		
		ViewUtils.componentColorBorder(textFieldQuestion, ViewUtils.blueColor);
		ViewUtils.componentColorBorder(comboBoxQuestionType, ViewUtils.blueColor);
		ViewUtils.componentColorBorder(scrollAnswers, ViewUtils.blueColor);
		
		labelQuestion.setForeground(ViewUtils.blueColor);
		labelQuestionType.setForeground(ViewUtils.blueColor);
		labelAnswers.setForeground(ViewUtils.blueColor);
		
		textFieldQuestion.setForeground(ViewUtils.blackColor);
		comboBoxQuestionType.setForeground(ViewUtils.blackColor);
		
		ViewUtils.componentSetFont(tableAnswers, 24);
		ViewUtils.componentSetFont(tableAnswers.getTableHeader(), 24);
		
		tableAnswers.setRowHeight(36);
		tableAnswers.setDefaultEditor(Object.class, null);
		tableAnswers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(500);
		tableAnswers.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		comboBoxQuestionType.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = comboBoxQuestionType.getSelectedIndex();
				if (selected >= 0) {
					if (!questionType.equals(comboBoxQuestionType.getItemAt(selected))) {
						questionType = comboBoxQuestionType.getItemAt(selected);
						resetInputValidation();
						questionTypeChanged();
					}
				}
			}
		});
		
		for (QuestionType questionType : QuestionType.values()) {
			comboBoxQuestionType.addItem(questionType);
		}
	}

	@Override
	public void getResult(ResultReceiver resultReceiver, Object object) {
		this.resultReceiver = resultReceiver;
		if (object instanceof Question) {
			this.question = (Question) object;
		} else {
			this.question = null;
		}
		reloadQuestion();
	}
	
	private void reloadQuestion() {
		if (question == null) {		
			textFieldQuestion.setText("");			
			comboBoxQuestionType.setSelectedIndex(0);
			comboBoxQuestionType.setEnabled(true);
			questionType = QuestionType.MULTIPLE;
		} else {
			textFieldQuestion.setText(question.getQuestion());
			for (int i = 0; i < comboBoxQuestionType.getItemCount(); i++) {
				if (comboBoxQuestionType.getItemAt(i).equals(question.getType())) {
					comboBoxQuestionType.setSelectedIndex(i);
				}
			}
			comboBoxQuestionType.setEnabled(false);
			questionType = question.getType();
		}
		
		questionTypeChanged();
		
		if (question != null) {
			DefaultTableModel model = (DefaultTableModel) tableAnswers.getModel();
			for (Answer answer : question.getAnswers()) {
				answerAdd(model, answer.getAnswer());
				if (answer.getCorrect()) {
					answerSetCorrect(model, tableAnswers.getRowCount() - 1);
				}
			}
		}
		
		resetInputValidation();
		revalidate();
	}
	
	private void questionTypeChanged() {		
		tableAnswers.removeAll();
		
		if (questionType == QuestionType.MULTIPLE) {
			DefaultTableModel model = new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0);
			tableAnswers.setModel(model);
			tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(500);
			tableAnswers.getColumnModel().getColumn(1).setPreferredWidth(100);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			tableAnswers.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			buttonAdd.setEnabled(true);
			buttonEdit.setEnabled(true);
			buttonDelete.setEnabled(true);
			buttonMoveUp.setEnabled(true);
			buttonMoveDown.setEnabled(true);
			buttonSetCorrect.setEnabled(true);
		} else if (questionType == QuestionType.TRUE_FALSE) {
			DefaultTableModel model = new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0);
			tableAnswers.setModel(model);
			tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(500);
			tableAnswers.getColumnModel().getColumn(1).setPreferredWidth(100);
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(JLabel.CENTER);
			tableAnswers.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
			model.addRow(new Object[]{"True", ""});
			model.addRow(new Object[]{"False", ""});
			buttonAdd.setEnabled(false);
			buttonEdit.setEnabled(false);
			buttonDelete.setEnabled(false);
			buttonMoveUp.setEnabled(false);
			buttonMoveDown.setEnabled(false);
			buttonSetCorrect.setEnabled(true);
		} else if (questionType == QuestionType.SHORT) {
			DefaultTableModel model = new DefaultTableModel(new Object[]{"Answer"}, 0);
			tableAnswers.setModel(model);
			tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(600);
			buttonAdd.setEnabled(true);
			buttonEdit.setEnabled(true);
			buttonDelete.setEnabled(true);
			buttonMoveUp.setEnabled(false);
			buttonMoveDown.setEnabled(false);
			buttonSetCorrect.setEnabled(false);
		}
		
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			DefaultTableModel model = (DefaultTableModel) tableAnswers.getModel();
			int selected = tableAnswers.getSelectedRow();
			
			if (button == buttonAdd) {
				answerAdd(model, null);
			} else if (button == buttonEdit) {
				answerEdit(model, selected);
			} else if (button == buttonDelete) {
				answerDelete(model, selected);
			} else if (button == buttonMoveUp) {
				answerMoveUp(model, selected);
			} else if (button == buttonMoveDown) {
				answerMoveDown(model, selected);
			} else if (button == buttonSetCorrect) {
				answerSetCorrect(model, selected);
			} else if (button == buttonSave) {
				saveQuestion();
			} else if (button == buttonCancel) {
				resultReceiver.processResult(null);
			}
		}
	}
	
	private void answerAdd(DefaultTableModel model, String answer) {
		if (answer == null) {
			answer = JOptionPane.showInputDialog("Enter Answer:");
		}
		if (answer != null && !answer.isEmpty()) {
			if (questionType == QuestionType.MULTIPLE) {
				model.addRow(new Object[]{answer, ""});
			} else if (questionType == QuestionType.SHORT) {
				model.addRow(new Object[]{answer});
			}				
		}
		if (model.getRowCount() == 4) {
			buttonAdd.setEnabled(false);
		}
	}
	
	private void answerEdit(DefaultTableModel model, int index) {
		if (index >= 0) {
			String answer = JOptionPane.showInputDialog("Enter Answer:", model.getValueAt(index, 0));
			if (answer != null && !answer.isEmpty()) {
				model.setValueAt(answer, index, 0);
			}
		}
	}

	private void answerDelete(DefaultTableModel model, int index) {
		if (index >= 0) {
			model.removeRow(index);
		}
		if (model.getRowCount() < 4) {
			buttonAdd.setEnabled(true);
		}
	}
	
	private void answerMoveUp(DefaultTableModel model, int index) {
		if (index > 0 && index < tableAnswers.getRowCount()) {
			model.moveRow(index, index, index - 1);
			tableAnswers.setRowSelectionInterval(index - 1, index - 1);
		}	
	}
	
	private void answerMoveDown(DefaultTableModel model, int index) {
		if (index >= 0 && index < tableAnswers.getRowCount() - 1) {
			model.moveRow(index, index, index + 1);
			tableAnswers.setRowSelectionInterval(index + 1, index + 1);
		}	
	}
	
	private void answerSetCorrect(DefaultTableModel model, int index) {
		if (index >= 0) {
			for (int i = 0; i < model.getRowCount(); i++) {
				if (i == index) {
					model.setValueAt("Yes", i, 1);
				} else {
					model.setValueAt("", i, 1);
				}
			}
		}
	}

	private void resetInputValidation() {
		colorValid(textFieldQuestion, true);
		colorValid(labelQuestion, true);
		colorValid(comboBoxQuestionType, true);
		colorValid(labelQuestionType, true);
		colorValid(scrollAnswers, true);
		colorValid(labelAnswers, true);
		labelAnswers.setText("Answers");

		revalidate();
	}
	
	private boolean inputValidation() {
		boolean valid = true;
		
		if (textFieldQuestion.getText().isEmpty()) {
			colorValid(textFieldQuestion, false);
			colorValid(labelQuestion, false);
			valid = false;
		}

		if (comboBoxQuestionType.getSelectedIndex() == -1) {
			colorValid(comboBoxQuestionType, false);
			colorValid(labelQuestionType, false);
			valid = false;
		}

		if (questionType == QuestionType.MULTIPLE) { 
			if (tableAnswers.getRowCount() < 3) {
				colorValid(scrollAnswers, false);
				colorValid(labelAnswers, false);
				labelAnswers.setText("Answers (3 - 4 Required)");
				valid = false;
			} else if (getCorrectIndex() == -1) {
				colorValid(scrollAnswers, false);
				colorValid(labelAnswers, false);
				labelAnswers.setText("Answers (Correct Not Set)");
				valid = false;
			} 
		} else if (questionType == QuestionType.TRUE_FALSE) {
			if (getCorrectIndex() == -1) {
				colorValid(scrollAnswers, false);
				colorValid(labelAnswers, false);
				labelAnswers.setText("Answers (Correct Not Set)");
				valid = false;
			} 
		} else if (questionType == QuestionType.SHORT) {
			if (tableAnswers.getRowCount() == 0) {
				colorValid(scrollAnswers, false);
				colorValid(labelAnswers, false);
				labelAnswers.setText("Answers (One Answer Minimum)");
				valid = false;
			}
		}
		
		return valid;
	}
	
	private void saveQuestion() {
		resetInputValidation();
		
		if (inputValidation()) {
			LinkedList<String> answers = new LinkedList<>();
			
			for (int i = 0; i < tableAnswers.getRowCount(); i++) {
				answers.add((String) tableAnswers.getValueAt(i, 0));
			}
			
			MazeQuestion q = new MazeQuestion(textFieldQuestion.getText(), answers, getCorrectIndex(), questionType);
			
			if (question != null && question.getId() > 0) {
				q.setId(question.getId());
			}
			
			resultReceiver.processResult(q);
		}
		
		revalidate();
	}
	
	private void colorValid(JComponent component, boolean valid) {
		if (component instanceof JLabel) {
			if (valid) {
				component.setForeground(ViewUtils.blueColor);
			} else {
				component.setForeground(ViewUtils.redColor);
			}
		} else {
			if (valid) {
				ViewUtils.componentColorBorder(component, ViewUtils.blueColor);
			} else {
				ViewUtils.componentColorBorder(component, ViewUtils.redColor);
			}
			component.setForeground(ViewUtils.blackColor);
		}
	}
	
	private int getCorrectIndex() {
		int index = -1;
		if (questionType != QuestionType.SHORT) {
			for (int i = 0; i < tableAnswers.getRowCount(); i++) {
				if (tableAnswers.getValueAt(i, 1).equals("Yes")) {
					index = i;
				}
			}
		}
		return index;
	}
}





























