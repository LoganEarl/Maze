package maze.model.question.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;

class SQLiteQuestionFactory {
    SQLiteQuestion parseQuestion(ResultSet databaseResult){
        try {
            String questionType = databaseResult.getString(QuestionTable.COLUMN_TYPE);
            if(ShortResponseQuestion.TYPE_SHORT_RESPONSE.equals(questionType))
                return new ShortResponseQuestion(databaseResult);
            if(MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE.equals(questionType))
                return new MultipleChoiceQuestion(databaseResult);
            if(BooleanQuestion.TYPE_BOOLEAN.equals(questionType))
                return new BooleanQuestion(databaseResult);
        }catch(SQLException ignored){
        }
        return null;
    }
}
