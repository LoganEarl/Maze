package maze.model.question.sqlite;

import maze.model.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.File;

import static maze.model.question.sqlite.BooleanQuestion.TEXT_FALSE;
import static maze.model.question.sqlite.BooleanQuestion.TEXT_TRUE;
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

    @Test
    void getQuestionWithID() {
        for(Question q : dataSource.getAllQuestions())
            assert (dataSource.getQuestionWithID(q.getID()).equals(q));
        assert dataSource.getQuestionWithID(-1) == null;
    }

    @Test
    void update() {
        int modifyId = dataSource.getNextQuestionID();
        assert dataSource.getQuestionWithID(modifyId) == null;
        Question questionEdit = new BooleanQuestion(modifyId,false,"OLD PROMPT", "OLD ITEM");
        dataSource.update(questionEdit);
        questionEdit = dataSource.getQuestionWithID(modifyId);
        assert  questionEdit != null;
        assert dataSource.exists(questionEdit);
        assert questionEdit.getPrompt().equals("OLD PROMPT");
        assert questionEdit.getCorrectAnswer().equals(TEXT_FALSE);
        assert questionEdit.constructKeyItem().getName().equals("OLD ITEM");
        questionEdit = new BooleanQuestion(modifyId,true,"NEW PROMPT", "NEW ITEM");
        dataSource.update(questionEdit);
        questionEdit = dataSource.getQuestionWithID(modifyId);
        assert  questionEdit != null;
        assert dataSource.exists(questionEdit);
        assert questionEdit.getPrompt().equals("NEW PROMPT");
        assert questionEdit.getCorrectAnswer().equals(TEXT_TRUE);
        assert questionEdit.constructKeyItem().getName().equals("NEW ITEM");
    }

    @Test
    void delete() {
        Question toDelete = dataSource.getAllQuestions().iterator().next();
        assert(dataSource.exists(toDelete));
        dataSource.delete(toDelete);
        assert(!dataSource.exists(toDelete));
    }

    @Test
    void exists() {
        Question check = dataSource.getAllQuestions().iterator().next();
        assert(dataSource.exists(check));
        check = new BooleanQuestion(dataSource.getNextQuestionID(),true,"NEW PROMPT", "NEW ITEM");
        assert(!dataSource.exists(check));
    }

    @Test
    void testExists() {
        //whew*
        String itemName = dataSource.getAllQuestions().iterator().next().constructKeyItem().getName();
        assert dataSource.exists(itemName);
        itemName = "THIS IS A DUMMY ITEM NAME THAT SHOULD NOT EXISTaskdfj;alksjf";
        assert !dataSource.exists(itemName);
    }

    @Test
    void getNextQuestionID() {
        int max = -1;
        for(Question q: dataSource.getAllQuestions())
            if(q.getID() > max) max = q.getID();
        max++;
        assert dataSource.getNextQuestionID() == max;
    }
}