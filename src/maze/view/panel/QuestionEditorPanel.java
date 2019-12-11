package maze.view.panel;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import maze.model.question.Question;
import maze.model.question.QuestionDataSource;
import maze.model.question.sqlite.QuestionFactory;
import maze.view.Panel;
import maze.view.PanelType;
import maze.view.ViewUtils;
import utils.ResultProvider;
import utils.ResultReceiver;

import static maze.model.question.sqlite.BooleanQuestion.*;
import static maze.model.question.sqlite.MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE;
import static maze.model.question.sqlite.ShortResponseQuestion.TYPE_SHORT_RESPONSE;

public class QuestionEditorPanel extends Panel implements ResultProvider, ActionListener {
	private QuestionDataSource dataSource;
    private ResultReceiver resultReceiver;
    private Question question;
    private String questionType = TYPE_MULTIPLE_CHOICE;
    private GridBagConstraints gc;

    private JLabel labelQuestionType = new JLabel("Question Type");
    private JLabel labelQuestion = new JLabel("Question");
    private JLabel labelItem = new JLabel("Associated Item");
    private JLabel labelAnswers = new JLabel("Answers");

    private JComboBox<ComboItem> comboBoxQuestionType = new JComboBox<>();
    private JTextPane textFieldQuestion = new JTextPane();
    private JTextPane textFieldItem = new JTextPane();
    private JTable tableAnswers = new JTable(new DefaultTableModel(new Object[]{"Answer", "Correct"}, 0));
    private JScrollPane scrollAnswers = new JScrollPane(tableAnswers);

    private JButton buttonAdd = new JButton("Add");
    private JButton buttonEdit = new JButton("Edit");
    private JButton buttonDelete = new JButton("Delete");
    private JButton buttonSetCorrect = new JButton("Set Correct");
    private JButton buttonSave = new JButton("Save");
    private JButton buttonCancel = new JButton("Cancel");
    
    private class ComboItem {
    	private String value;
    	private String toString;
    	
    	private ComboItem(String value, String toString) {
    		this.value = value;
    		this.toString = toString;
    	}
    	
    	public String getValue() {
    		return value;
    	}
    	
    	public String toString() {
    		return toString;
    	}
    }

    public QuestionEditorPanel(QuestionDataSource dataSource) {
        super(PanelType.QUESTION_EDITOR);

        this.dataSource = dataSource;
        setBackground(ViewUtils.backgroundColor);

        setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.NORTH;
        gc.insets = new Insets(10, 20, 10, 20);

        insertAllComponents();
        initializeAllComponents();
    }

    private void insertAllComponents() {
        ViewUtils.insertComponent(this, gc, labelQuestion, 			0,  0, 1, 1, 600, 30);
        ViewUtils.insertComponent(this, gc, textFieldQuestion, 		0,  1, 1, 4, 600, 200);
        ViewUtils.insertComponent(this, gc, new JLabel(""), 		0,  5, 1, 1, 600, 40);
        ViewUtils.insertComponent(this, gc, labelAnswers, 			0,  6, 1, 1, 600, 30);
        ViewUtils.insertComponent(this, gc, scrollAnswers, 			0,  7, 1, 3, 600, 200);
        ViewUtils.insertComponent(this, gc, new JLabel(""), 		0, 10, 1, 1, 600, 40);
        ViewUtils.insertComponent(this, gc, buttonSave, 			0, 11, 1, 1, 600, 75);

        ViewUtils.insertComponent(this, gc, labelQuestionType, 		1,  0, 2, 1, 400, 30);
        ViewUtils.insertComponent(this, gc, comboBoxQuestionType, 	1,  1, 2, 1, 400, 50);
        ViewUtils.insertComponent(this, gc, new JLabel(""), 		1,  2, 2, 1, 400, 10);
        ViewUtils.insertComponent(this, gc, labelItem, 				1,  3, 2, 1, 400, 30);
        ViewUtils.insertComponent(this, gc, textFieldItem, 			1,  4, 2, 1, 400, 50);
        ViewUtils.insertComponent(this, gc, buttonAdd, 				1,  7, 1, 1, 180, 75);
        ViewUtils.insertComponent(this, gc, buttonDelete, 			1,  8, 1, 1, 180, 75);
        ViewUtils.insertComponent(this, gc, buttonCancel, 			1, 11, 2, 1, 400, 75);

        ViewUtils.insertComponent(this, gc, buttonEdit, 			2,  7, 1, 1, 180, 75);
        ViewUtils.insertComponent(this, gc, buttonSetCorrect, 		2,  8, 1, 1, 180, 75);
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
            if (component instanceof JLabel) {
            	JLabel label = (JLabel) component;
            	label.setForeground(ViewUtils.blueColor);
            }
        }

        ViewUtils.componentColorBorder(textFieldQuestion, ViewUtils.blueColor);
        ViewUtils.componentColorBorder(comboBoxQuestionType, ViewUtils.blueColor);
        ViewUtils.componentColorBorder(textFieldItem, ViewUtils.blueColor);
        ViewUtils.componentColorBorder(scrollAnswers, ViewUtils.blueColor);

        textFieldQuestion.setForeground(ViewUtils.blackColor);
        textFieldItem.setForeground(ViewUtils.blackColor);
        comboBoxQuestionType.setForeground(ViewUtils.blackColor);

        ViewUtils.componentSetFont(tableAnswers, 24);
        ViewUtils.componentSetFont(tableAnswers.getTableHeader(), 24);

        tableAnswers.setRowHeight(36);
        tableAnswers.setDefaultEditor(Object.class, null);
        tableAnswers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(500);
        tableAnswers.getColumnModel().getColumn(1).setPreferredWidth(100);

        comboBoxQuestionType.addActionListener(e -> {
            int selected = comboBoxQuestionType.getSelectedIndex();
            if (selected >= 0) {
                if (!questionType.equals(comboBoxQuestionType.getItemAt(selected).getValue())) {
                    questionType = comboBoxQuestionType.getItemAt(selected).getValue();
                    resetInputValidation();
                    questionTypeChanged();
                }
            }
        });

        comboBoxQuestionType.addItem(new ComboItem(TYPE_MULTIPLE_CHOICE, "Multiple Choice"));
        comboBoxQuestionType.addItem(new ComboItem(TYPE_BOOLEAN, "True False"));
        comboBoxQuestionType.addItem(new ComboItem(TYPE_SHORT_RESPONSE, "Short Answer"));
    }

    @Override
    public void getResult(ResultReceiver resultReceiver, Object object) {
        this.resultReceiver = resultReceiver;
        if(object instanceof Question) {
            this.question = (Question)object;
        } else {
        	this.question = null;
        }
        reloadQuestion();
    }

    private void reloadQuestion() {
        if (question == null) {
            textFieldQuestion.setText("");
            textFieldItem.setText("");
            comboBoxQuestionType.setSelectedIndex(0);
            comboBoxQuestionType.setEnabled(true);
            questionType = TYPE_MULTIPLE_CHOICE;
        } else {
            textFieldQuestion.setText(question.getPrompt());
            textFieldItem.setText(question.constructKeyItem().getName());
            for (int i = 0; i < comboBoxQuestionType.getItemCount(); i++) {
                if (comboBoxQuestionType.getItemAt(i).getValue().equals(question.getQuestionType())) {
                    comboBoxQuestionType.setSelectedIndex(i);
                }
            }
            comboBoxQuestionType.setEnabled(false);
            questionType = question.getQuestionType();
        }

        questionTypeChanged();

        if (question != null) {
            DefaultTableModel model = (DefaultTableModel) tableAnswers.getModel();
            for (String answer : question.getPossibleAnswers()) {
                answerAdd(model, answer);
                if (question.isCorrect(answer)) {
                    answerSetCorrect(model, tableAnswers.getRowCount() - 1);
                }
            }
        }

        resetInputValidation();
        revalidate();
    }

    private void questionTypeChanged() {
        tableAnswers.removeAll();

        switch (questionType) {
            case TYPE_MULTIPLE_CHOICE: {
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
                buttonSetCorrect.setEnabled(true);
                break;
            }
            case TYPE_BOOLEAN: {
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
                buttonSetCorrect.setEnabled(true);
                break;
            }
            case TYPE_SHORT_RESPONSE: {
                DefaultTableModel model = new DefaultTableModel(new Object[]{"Answer"}, 0);
                tableAnswers.setModel(model);
                tableAnswers.getColumnModel().getColumn(0).setPreferredWidth(600);
                buttonAdd.setEnabled(true);
                buttonEdit.setEnabled(true);
                buttonDelete.setEnabled(true);
                buttonSetCorrect.setEnabled(false);
                break;
            }
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
            if (questionType.equals(TYPE_MULTIPLE_CHOICE)) {
                model.addRow(new Object[]{answer, ""});
            } else if (questionType.equals(TYPE_SHORT_RESPONSE)) {
                model.addRow(new Object[]{answer});
            }
        }
        if (model.getRowCount() == 4 || (model.getRowCount() == 1 && questionType.equals(TYPE_BOOLEAN))) {
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

    private void answerSetCorrect(DefaultTableModel model, int index) {
        if (questionType != TYPE_SHORT_RESPONSE && index >= 0) {
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
        colorValid(labelItem, true);
        colorValid(scrollAnswers, true);
        colorValid(labelAnswers, true);
        labelAnswers.setText("Answers");
        labelItem.setText("Associated Item");

        revalidate();
    }

    private boolean inputValidation() {
        boolean valid = true;

        if (textFieldQuestion.getText().trim().isEmpty()) {
            colorValid(textFieldQuestion, false);
            colorValid(labelQuestion, false);
            valid = false;
        }

        if (comboBoxQuestionType.getSelectedIndex() == -1) {
            colorValid(comboBoxQuestionType, false);
            colorValid(labelQuestionType, false);
            valid = false;
        }
        
        if (textFieldItem.getText().trim().isEmpty()) {
        	labelItem.setText("Associated Item (Required)");
        	colorValid(labelItem, false);
        	valid = false;
        }
        
        if (dataSource.exists(textFieldItem.getText().trim())) {
        	if (question == null || !question.constructKeyItem().getName().trim().equals(textFieldItem.getText().trim())) {
        		labelItem.setText("Associated Item (Already Exists)");
        		colorValid(labelItem, false);
        		valid = false;
        	}
        }       

        switch (questionType) {
            case TYPE_MULTIPLE_CHOICE:
                if (tableAnswers.getRowCount() < 3) {
                    colorValid(scrollAnswers, false);
                    colorValid(labelAnswers, false);
                    labelAnswers.setText("Answers (3 - 4 Required)");
                    valid = false;
                } else if (getCorrectAnswer() == null) {
                    colorValid(scrollAnswers, false);
                    colorValid(labelAnswers, false);
                    labelAnswers.setText("Answers (Correct Not Set)");
                    valid = false;
                }
                break;
            case TYPE_BOOLEAN:
                String answer = getCorrectAnswer();
                if (answer == null || !(TEXT_TRUE.toLowerCase().equals(answer.toLowerCase()) || TEXT_FALSE.toLowerCase().equals(answer.toLowerCase()))) {
                    colorValid(scrollAnswers, false);
                    colorValid(labelAnswers, false);
                    labelAnswers.setText("Answers (Correct Not Set)");
                    valid = false;
                }
                break;
            case TYPE_SHORT_RESPONSE:
                if (tableAnswers.getRowCount() == 0) {
                    colorValid(scrollAnswers, false);
                    colorValid(labelAnswers, false);
                    labelAnswers.setText("Answers (One Answer Minimum)");
                    valid = false;
                }
                break;
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

            int nextId = dataSource.getNextQuestionID();
            QuestionFactory factory = new QuestionFactory();

            Question q;
            if (question != null) {
                q = factory.createQuestion(
                        question.getID(),
                        textFieldQuestion.getText(),
                        getCorrectAnswer(),
                        answers.toArray(new String[0]),
                        textFieldItem.getText().trim(),
                        question.getQuestionType());
            } else {
                q = factory.createQuestion(
                        nextId,
                        textFieldQuestion.getText(),
                        getCorrectAnswer(),
                        answers.toArray(new String[0]),
                        textFieldItem.getText().trim(),
                        questionType);
            }

            resultReceiver.processResult(q);
        }

        revalidate();
    }

    private String getCorrectAnswer(){
        String correct = null;
        if(!questionType.equals(TYPE_SHORT_RESPONSE)){
            for(int i = 0; i < tableAnswers.getRowCount(); i++){
                if(tableAnswers.getValueAt(i,1).equals("Yes")){
                    correct = tableAnswers.getValueAt(i,0).toString();
                }
            }
        }
        return correct;
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
}





























