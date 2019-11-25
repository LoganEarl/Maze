package maze.model.question.sqlite;

import maze.model.Item;
import maze.model.question.DoorKey;
import utils.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static maze.model.question.sqlite.SQLiteQuestionDataSource.GeneralPurposeSql.*;
import static maze.model.question.sqlite.SQLiteQuestionDataSource.GeneralPurposeSql.REPLACE_ANSWER;

public class MultipleChoiceQuestion implements SQLiteQuestion {
    public static final String TYPE_MULTIPLE_CHOICE = "typeMultipleChoice";

    private int id;
    private String correctAnswer;
    private String[] possibleAnswers;
    private String prompt;
    private String keyItemName;

    public MultipleChoiceQuestion(int id, String correctAnswer, String[] possibleAnswers, String prompt, String keyItemName) {
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
        this.prompt = prompt;
        this.keyItemName = keyItemName;
    }

    MultipleChoiceQuestion(ResultSet questionSet) throws SQLException {
        this.id = questionSet.getInt(QuestionTable.COLUMN_ID);
        this.prompt = questionSet.getString(QuestionTable.COLUMN_PROMPT);
        this.keyItemName = questionSet.getString(QuestionTable.COLUMN_ITEM_NAME);
        List<String> answers = new ArrayList<>();
        while(questionSet.next() && questionSet.getInt(QuestionTable.COLUMN_ID) == id){
            String answer = questionSet.getString(AnswerTable.COLUMN_ANSWER);
            if(questionSet.getInt(AnswerTable.COLUMN_IS_CORRECT) == 1)
                correctAnswer = answer;
            answers.add(answer);
        }
        possibleAnswers = answers.toArray(new String[0]);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public String getQuestionType() {
        return TYPE_MULTIPLE_CHOICE;
    }

    @Override
    public Item constructKeyItem() {
        return new DoorKey(keyItemName);
    }

    @Override
    public boolean isCorrect(String answer) {
        if(answer == null)
            return false;
        return correctAnswer.toLowerCase().trim().equals(answer.toLowerCase().trim());
    }

    @Override
    public boolean isCorrect(Item key) {
        return key.getName().equals(keyItemName);
    }

    @Override
    public boolean removeFromDatabase(String databaseName) {
        boolean deletion =  DatabaseManager.executeStatement(DELETE_ANSWERS, databaseName, id) > 0;
        if(deletion)
            deletion = DatabaseManager.executeStatement(DELETE_QUESTION, databaseName, id) > 0;
        return deletion;
    }

    @Override
    public boolean updateInDatabase(String databaseName) {
        boolean update = DatabaseManager.executeStatement(REPLACE_QUESTION, databaseName, id, prompt, TYPE_MULTIPLE_CHOICE, keyItemName) > 0;
        for(String answer: possibleAnswers)
            if(update) {
                int correctCode = correctAnswer.equals(answer)? 1:0;
                update = DatabaseManager.executeStatement(REPLACE_ANSWER, databaseName, id, answer, correctCode) > 0;
            }
        return update;
    }

    @Override
    public boolean existsInDatabase(String databaseName) {
        return SQLiteQuestion.existsInDatabase(id,databaseName);
    }
}
