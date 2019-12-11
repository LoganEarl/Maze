package maze.model.question.sqlite;

import utils.DatabaseManager;

import java.util.*;

public class QuestionTable implements DatabaseManager.DatabaseTable {
    static final String TABLE_NAME = "question";

    static final String COLUMN_ID = "id";
    static final String COLUMN_PROMPT = "prompt";
    static final String COLUMN_TYPE = "type";
    static final String COLUMN_ITEM_NAME = "itemName";

    private final Map<String, String> TABLE_DEFINITION = new LinkedHashMap<>();
    private final Set<String> CONSTRAINTS = new HashSet<>();

    QuestionTable() {
        TABLE_DEFINITION.put(COLUMN_ID, "INT");
        TABLE_DEFINITION.put(COLUMN_PROMPT, "TEXT");
        TABLE_DEFINITION.put(COLUMN_TYPE, "TEXT");
        TABLE_DEFINITION.put(COLUMN_ITEM_NAME, "TEXT");

        CONSTRAINTS.add(String.format(Locale.US,"PRIMARY KEY (%s)", COLUMN_ID));
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
