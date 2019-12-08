package maze.model.question.sqlite;

import maze.model.question.Question;
import maze.model.question.QuestionDataSource;
import utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SQLiteQuestionDataSource implements QuestionDataSource {
    private String databaseFileName;
    private SQLiteQuestionFactory factory;

    public SQLiteQuestionDataSource(String databaseFileName) {
        this.databaseFileName = databaseFileName;
        factory = new SQLiteQuestionFactory();

        List<DatabaseManager.DatabaseTable> tables = new ArrayList<>();
        tables.add(new QuestionTable());
        tables.add(new AnswerTable());

        DatabaseManager.createDirectories();
        DatabaseManager.createDatabase(databaseFileName);
        DatabaseManager.createTables(databaseFileName, tables);
    }

    @Override
    public Set<Question> getAllQuestions() {
        Set<Integer> allIDs = new LinkedHashSet<>();
        Connection c = DatabaseManager.getDatabaseConnection(databaseFileName);
        Set<Question> questions = new LinkedHashSet<>();

        PreparedStatement getSQL;
        if (c != null) {
            try {
                getSQL = c.prepareStatement(GeneralPurposeSql.GET_ALL_QUESTION_IDS);
                ResultSet questionEntries = getSQL.executeQuery();
                while (questionEntries.next()) {
                    allIDs.add(questionEntries.getInt(1));
                }
                getSQL.close();

                for (Integer i : allIDs) {
                    getSQL = c.prepareStatement(GeneralPurposeSql.GET_QUESTION);
                    getSQL.setInt(1, i);
                    questionEntries = getSQL.executeQuery();
                    questions.add(factory.parseQuestion(questionEntries));
                    getSQL.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DatabaseManager.closeDatabaseConnection(databaseFileName);
        return questions;
    }

    @Override
    public Question getQuestionWithID(int questionID) {
        Connection c = DatabaseManager.getDatabaseConnection(databaseFileName);
        PreparedStatement getSQL;
        Question toReturn = null;
        if (c != null) {
            try {
                getSQL = c.prepareStatement(GeneralPurposeSql.GET_QUESTION);
                getSQL.setInt(1, questionID);
                ResultSet questionEntries = getSQL.executeQuery();
                toReturn = factory.parseQuestion(questionEntries);
                getSQL.close();
            } catch (Exception e) {
                e.printStackTrace();
                assert false;
            }
        }
        return toReturn;
    }

    @Override
    public void update(Question q) {
        if (!(q instanceof SQLiteQuestion))
            throw new IllegalArgumentException("Use of unsupported type of question");
        ((SQLiteQuestion) q).updateInDatabase(databaseFileName);
    }

    @Override
    public void delete(Question q) {
        if (!(q instanceof SQLiteQuestion))
            throw new IllegalArgumentException("Use of unsupported type of question");
        ((SQLiteQuestion) q).removeFromDatabase(databaseFileName);
    }

    @Override
    public boolean exists(Question q) {
        if (!(q instanceof SQLiteQuestion))
            throw new IllegalArgumentException("Use of unsupported type of question");
        return ((SQLiteQuestion) q).existsInDatabase(databaseFileName);
    }

    @Override
    public boolean exists(String itemName) {
        Connection c = DatabaseManager.getDatabaseConnection(databaseFileName);
        PreparedStatement getSQL;
        boolean toReturn = false;
        if (c != null) {
            try {
                getSQL = c.prepareStatement(GeneralPurposeSql.GET_ITEM);
                getSQL.setString(1, itemName);
                ResultSet questionEntries = getSQL.executeQuery();
                toReturn = questionEntries.next();
                getSQL.close();
            } catch (Exception e) {
                e.printStackTrace();
                toReturn = false;
            }
        }
        return toReturn;
    }

    @Override
    public int getNextQuestionID() {
        return SQLiteQuestion.getNextQuestionID(databaseFileName);
    }

    @Override
    public java.util.Iterator<Question> iterator() {
        return new Iterator(getAllQuestions());
    }

    static final class GeneralPurposeSql {
        static final String DELETE_QUESTION = String.format(Locale.US,
                "DELETE FROM %s WHERE %s=?",
                QuestionTable.TABLE_NAME, QuestionTable.COLUMN_ID);
        static final String DELETE_ANSWERS = String.format(Locale.US,
                "DELETE FROM %s WHERE %s=?",
                AnswerTable.TABLE_NAME, AnswerTable.COLUMN_QUESTION_ID);
        static final String GET_ALL_QUESTION_IDS = String.format(Locale.US,
                "SELECT %s FROM %s",
                QuestionTable.COLUMN_ID, QuestionTable.TABLE_NAME);
        static final String GET_QUESTION = String.format(Locale.US,
                "SELECT * FROM %s LEFT JOIN %s ON %s.%s=%s.%s WHERE %s.%s=?",
                QuestionTable.TABLE_NAME, AnswerTable.TABLE_NAME, QuestionTable.TABLE_NAME, QuestionTable.COLUMN_ID,
                AnswerTable.TABLE_NAME, AnswerTable.COLUMN_QUESTION_ID, QuestionTable.TABLE_NAME, QuestionTable.COLUMN_ID);
        static final String REPLACE_QUESTION = String.format(Locale.US,
                "REPLACE INTO %s(%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                QuestionTable.TABLE_NAME, QuestionTable.COLUMN_ID, QuestionTable.COLUMN_PROMPT,
                QuestionTable.COLUMN_TYPE, QuestionTable.COLUMN_ITEM_NAME);
        static final String REPLACE_ANSWER = String.format(Locale.US,
                "REPLACE INTO %s(%s, %s, %s) VALUES (?, ?, ?)",
                AnswerTable.TABLE_NAME, AnswerTable.COLUMN_QUESTION_ID, AnswerTable.COLUMN_ANSWER, AnswerTable.COLUMN_IS_CORRECT);
        static final String GET_MAX_QUESTION_ID = String.format(Locale.US,
                "SELECT MAX(%s) as %s FROM %s",
                QuestionTable.COLUMN_ID, QuestionTable.COLUMN_ID, QuestionTable.TABLE_NAME);
        static final String GET_ITEM = String.format(Locale.US,
                "SELECT * FROM %s WHERE %s=?",
                QuestionTable.TABLE_NAME, QuestionTable.COLUMN_ITEM_NAME);
    }
}
