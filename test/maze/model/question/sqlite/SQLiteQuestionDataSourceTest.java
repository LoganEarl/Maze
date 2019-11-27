package maze.model.question.sqlite;

import maze.model.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.File;
import static utils.FileUtils.DATA_DIRECTORY;

class SQLiteQuestionDataSourceTest {
    private static final String FILE_NAME = "testQuestions.db";
    private static final String DATABASE_FILE = DATA_DIRECTORY + FILE_NAME;

    private SQLiteQuestionDataSource dataSource;

    @BeforeEach
    void setUp() {
        FileUtils.copyFile(new File(DATA_DIRECTORY, "questions.db"), new File(DATABASE_FILE));
        dataSource = new SQLiteQuestionDataSource(FILE_NAME);
    }

    @Test
    void getAllQuestions() {
        assert (dataSource.getAllQuestions().size() > 0);
        boolean foundMultipleChoice = false;
        boolean foundBoolean = false;
        boolean foundShortResponse = false;
        for (Question q : dataSource.getAllQuestions()) {
            if (q.getQuestionType().equals(BooleanQuestion.TYPE_BOOLEAN))
                foundBoolean = true;
            if (q.getQuestionType().equals(ShortResponseQuestion.TYPE_SHORT_RESPONSE))
                foundShortResponse = true;
            if (q.getQuestionType().equals(MultipleChoiceQuestion.TYPE_MULTIPLE_CHOICE))
                foundMultipleChoice = true;
        }
        assert (foundBoolean);
        assert (foundMultipleChoice);
        assert (foundShortResponse);
    }
}