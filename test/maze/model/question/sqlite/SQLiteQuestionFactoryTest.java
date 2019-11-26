package maze.model.question.sqlite;

import maze.model.question.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DatabaseManager;
import utils.FileUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.Set;

class SQLiteQuestionFactoryTest {
    private SQLiteQuestionFactory factory;

    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DatabaseManager.DATA_DIRECTORY + FILE_NAME;

    @BeforeEach
    void setUp() {
        factory = new SQLiteQuestionFactory();
        FileUtils.copyFile(new File(DatabaseManager.DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
    }

    @AfterEach
    void tearDown() {
        FileUtils.deleteFile(new File(DATABASE_FILE));
    }

    @Test
    void testParseQuestion() {
        Set<Integer> allIDs = new LinkedHashSet<>();
        Connection c = DatabaseManager.getDatabaseConnection(FILE_NAME);
        PreparedStatement getSQL;
        if (c != null) {
            try {
                getSQL = c.prepareStatement(SQLiteQuestionDataSource.GeneralPurposeSql.GET_ALL_QUESTION_IDS);
                ResultSet questionEntries = getSQL.executeQuery();
                while (questionEntries.next()) {
                    allIDs.add(questionEntries.getInt(1));
                }
                getSQL.close();

                for (Integer i : allIDs) {
                    getSQL = c.prepareStatement(SQLiteQuestionDataSource.GeneralPurposeSql.GET_QUESTION);
                    getSQL.setInt(1, i);
                    questionEntries = getSQL.executeQuery();

                    Question testParsed = factory.parseQuestion(questionEntries);
                    String expectedQuestionType = questionEntries.getString(QuestionTable.COLUMN_TYPE);

                    assert(testParsed.getQuestionType().equals(expectedQuestionType));
                    assert !ShortResponseQuestion.TYPE_SHORT_RESPONSE.equals(expectedQuestionType) || testParsed instanceof ShortResponseQuestion;
                    assert !MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE.equals(expectedQuestionType) || testParsed instanceof MultipleChoiceQuestion;
                    assert !BooleanQuestion.TYPE_BOOLEAN.equals(expectedQuestionType) || testParsed instanceof BooleanQuestion;

                    getSQL.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DatabaseManager.closeDatabaseConnection(FILE_NAME);
    }
}