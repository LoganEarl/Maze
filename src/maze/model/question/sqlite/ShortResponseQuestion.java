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

public class ShortResponseQuestion implements SQLiteQuestion {
    public static final String TYPE_SHORT_RESPONSE = "typeShortResponse";

    private int id;
    private String[] keywords;
    private String prompt;
    private String keyItemName;

    public ShortResponseQuestion(int id, String[] keywords, String prompt, String keyItemName) {
        this.id = id;
        this.keywords = keywords;
        this.prompt = prompt;
        this.keyItemName = keyItemName;
    }

    ShortResponseQuestion(ResultSet questionSet) throws SQLException {
        this.id = questionSet.getInt(QuestionTable.COLUMN_ID);
        this.prompt = questionSet.getString(QuestionTable.COLUMN_PROMPT);
        this.keyItemName = questionSet.getString(QuestionTable.COLUMN_ITEM_NAME);
        List<String> keywords = new ArrayList<>();
        while(questionSet.next() && questionSet.getInt(QuestionTable.COLUMN_ID) == id){
            keywords.add(questionSet.getString(AnswerTable.COLUMN_ANSWER));
        }
        this.keywords = keywords.toArray(new String[0]);
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
        return keywords[0];
    }

    @Override
    public String[] getPossibleAnswers() {
        return new String[0];
    }

    @Override
    public String getQuestionType() {
        return TYPE_SHORT_RESPONSE;
    }

    @Override
    public Item constructKeyItem() {
        return new DoorKey(keyItemName);
    }

    @Override
    public boolean isCorrect(String answer) {
        if(answer == null)
            throw new IllegalArgumentException("Answer cannot be null");
        String[] answerWords = answer.split("\\W+");
        for(String keyword:keywords)
            for(String answerWord: answerWords)
                if(answerWord.toLowerCase().equals(keyword.toLowerCase()))
                    return true;
        return false;
    }

    @Override
    public boolean isCorrect(Item key) {
        if(key == null)
            throw new NullPointerException("Key should not be null");
        return key.getName().equals(keyItemName);
    }

    @Override
    public boolean removeFromDatabase(String databaseName) {
        if(databaseName == null || databaseName.isEmpty())
            throw new IllegalArgumentException("database name should not be empty");
        boolean deletion =  DatabaseManager.executeStatement(DELETE_ANSWERS, databaseName, id) > 0;
        if(deletion)
            deletion = DatabaseManager.executeStatement(DELETE_QUESTION, databaseName, id) > 0;
        return deletion;
    }

    @Override
    public boolean updateInDatabase(String databaseName) {
        if(databaseName == null || databaseName.isEmpty())
            throw new IllegalArgumentException("database name should not be empty");
        boolean update = DatabaseManager.executeStatement(REPLACE_QUESTION, databaseName, id, prompt, TYPE_SHORT_RESPONSE, keyItemName) > 0;
        DatabaseManager.executeStatement(DELETE_ANSWERS, databaseName, id);
        for(String keyword: keywords)
            if(update)
                update = DatabaseManager.executeStatement(REPLACE_ANSWER, databaseName, id, keyword, 1) > 0;
        return update;
    }

    @Override
    public boolean existsInDatabase(String databaseName) {
        if(databaseName == null || databaseName.isEmpty())
            throw new IllegalArgumentException("database name should not be empty");
        return SQLiteQuestion.existsInDatabase(id,databaseName);
    }
}
