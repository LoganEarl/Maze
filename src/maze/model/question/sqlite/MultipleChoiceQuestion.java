package maze.model.question.sqlite;

import maze.model.Item;
import maze.model.question.DoorKey;
import utils.DatabaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static maze.model.question.sqlite.SQLiteQuestionDataSource.GeneralPurposeSql.*;

public class MultipleChoiceQuestion implements SQLiteQuestion {
    public static final String TYPE_MULTIPLE_CHOICE = "typeMultipleChoice";

    private int id;
    private String correctAnswer;
    private String[] possibleAnswers;
    private String prompt;
    private String keyItemName;

    MultipleChoiceQuestion(int id, String correctAnswer, String[] possibleAnswers, String prompt, String keyItemName) {
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
            throw new IllegalArgumentException("Answer cannot be null");
        return correctAnswer.toLowerCase().trim().equals(answer.toLowerCase().trim());
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
        boolean update = DatabaseManager.executeStatement(REPLACE_QUESTION, databaseName, id, prompt, TYPE_MULTIPLE_CHOICE, keyItemName) > 0;
        DatabaseManager.executeStatement(DELETE_ANSWERS, databaseName, id);
        for(String answer: possibleAnswers)
            if(update) {
                int correctCode = correctAnswer.equals(answer)? 1:0;
                update = DatabaseManager.executeStatement(REPLACE_ANSWER, databaseName, id, answer, correctCode) > 0;
            }
        return update;
    }

    @Override
    public boolean existsInDatabase(String databaseName) {
        if(databaseName == null || databaseName.isEmpty())
            throw new IllegalArgumentException("database name should not be empty");
        return SQLiteQuestion.existsInDatabase(id,databaseName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MultipleChoiceQuestion that = (MultipleChoiceQuestion) o;
        return id == that.id &&
                correctAnswer.equals(that.correctAnswer) &&
                Arrays.equals(possibleAnswers, that.possibleAnswers) &&
                prompt.equals(that.prompt) &&
                keyItemName.equals(that.keyItemName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, correctAnswer, prompt, keyItemName);
        result = 31 * result + Arrays.hashCode(possibleAnswers);
        return result;
    }
}
