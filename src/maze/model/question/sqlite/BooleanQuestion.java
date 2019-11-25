package maze.model.question.sqlite;

import maze.model.Item;
import maze.model.question.DoorKey;
import utils.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;

import static maze.model.question.sqlite.SQLiteQuestionDataSource.GeneralPurposeSql.*;

public class BooleanQuestion implements SQLiteQuestion {
    public static final String TYPE_BOOLEAN = "typeBoolean";
    public static final String TEXT_TRUE = "true";
    public static final String TEXT_FALSE = "false";
    private int id;
    private boolean correctAnswer;
    private String prompt;
    private String keyItemName;

    public BooleanQuestion(int id, boolean correctAnswer, String prompt, String keyItemName) {
        this.id = id;
        this.correctAnswer = correctAnswer;
        this.prompt = prompt;
        this.keyItemName = keyItemName;
    }

    BooleanQuestion(ResultSet questionSet) throws SQLException {
        this.id = questionSet.getInt(QuestionTable.COLUMN_ID);
        this.prompt = questionSet.getString(QuestionTable.COLUMN_PROMPT);
        this.keyItemName = questionSet.getString(QuestionTable.COLUMN_ITEM_NAME);
        this.correctAnswer = TEXT_TRUE.equals(questionSet.getString(AnswerTable.COLUMN_ANSWER));
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
        return correctAnswer? TEXT_TRUE: TEXT_FALSE;
    }

    @Override
    public String getQuestionType() {
        return TYPE_BOOLEAN;
    }

    @Override
    public String[] getPossibleAnswers() {
        return new String[]{TEXT_TRUE, TEXT_FALSE};
    }

    @Override
    public Item constructKeyItem() {
        return new DoorKey(keyItemName);
    }

    @Override
    public boolean isCorrect(String answer) {
        if(answer == null)
            return false;
        return getCorrectAnswer().toLowerCase().equals(answer.toLowerCase().trim());
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
        boolean update = DatabaseManager.executeStatement(REPLACE_QUESTION, databaseName, id, prompt, TYPE_BOOLEAN, keyItemName) > 0;
        if(update)
            update = DatabaseManager.executeStatement(REPLACE_ANSWER, databaseName, id, getCorrectAnswer(), 1) > 0;
        return update;
    }

    @Override
    public boolean existsInDatabase(String databaseName) {
        return SQLiteQuestion.existsInDatabase(id,databaseName);
    }
}
