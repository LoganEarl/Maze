package maze.model.question.sqlite;

import maze.model.question.Question;
import utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface SQLiteQuestion extends Question, DatabaseManager.DatabaseEntry {
    static boolean existsInDatabase(int questionID, String databaseFileName){
        Connection c = DatabaseManager.getDatabaseConnection(databaseFileName);
        PreparedStatement getSQL;
        boolean result = false;
        if(c != null){
            try {
                getSQL = c.prepareStatement(SQLiteQuestionDataSource.GeneralPurposeSql.GET_QUESTION);
                getSQL.setInt(1,questionID);
                ResultSet existsSet = getSQL.executeQuery();
                result = existsSet.next();
                getSQL.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        DatabaseManager.closeDatabaseConnection(databaseFileName);
        return result;
    }

    static int getNextQuestionID(String databaseFileName) {
        Connection c = DatabaseManager.getDatabaseConnection(databaseFileName);
        PreparedStatement getSQL;
        int max = -1;
        if (c != null) {
            try {
                getSQL = c.prepareStatement(SQLiteQuestionDataSource.GeneralPurposeSql.GET_MAX_QUESTION_ID);
                ResultSet maxSet = getSQL.executeQuery();
                if (maxSet.next()) {
                    max = maxSet.getInt(1);
                }
                getSQL.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DatabaseManager.closeDatabaseConnection(databaseFileName);
        return max + 1;
    }
}
