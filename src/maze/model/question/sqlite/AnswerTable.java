package maze.model.question.sqlite;

import utils.DatabaseManager;

import java.util.*;

public class AnswerTable implements DatabaseManager.DatabaseTable {
    static final String TABLE_NAME = "answer";

    static final String COLUMN_QUESTION_ID = "questionID";
    static final String COLUMN_ANSWER = "answerText";
    static final String COLUMN_IS_CORRECT = "isCorrect";

    private final Map<String, String> TABLE_DEFINITION = new LinkedHashMap<>();
    private final Set<String> CONSTRAINTS = new HashSet<>();

    AnswerTable() {
        TABLE_DEFINITION.put(COLUMN_QUESTION_ID, "INT");
        TABLE_DEFINITION.put(COLUMN_ANSWER, "TEXT");
        TABLE_DEFINITION.put(COLUMN_IS_CORRECT, "INT");

        CONSTRAINTS.add(String.format(Locale.US,"PRIMARY KEY (%s, %s)", COLUMN_QUESTION_ID, COLUMN_ANSWER));
        CONSTRAINTS.add(String.format(Locale.US, "FOREIGN KEY (%s) REFERENCES %s(%s)",
                COLUMN_QUESTION_ID, QuestionTable.TABLE_NAME, QuestionTable.COLUMN_ID));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, String> getColumnDefinitions() {
        return TABLE_DEFINITION;
    }

    @Override
    public Set<String> getConstraints() {
        return CONSTRAINTS;
    }
}
