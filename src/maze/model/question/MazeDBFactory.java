package maze.model.question;

import java.io.File;
import java.util.List;

public class DatabaseManager {
	
    /**
     * This is the declaration for the /data directory used to store db files in
     */
    public static final String DATA_DIRECTORY = System.getProperty("user.dir").replace("\\", "/") + "/data/";
    
    public static final String DEFAULT_DB_FILE = "mazedb.sqlite3";
    
    static void createDirectories(String appDir) {
        File f = new File(DATA_DIRECTORY + appDir + "/");
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.mkdirs();
        }
    }
    
    static String createDbFileName(String appDir)
    {
		createDirectories(appDir);
		
		return DATA_DIRECTORY + appDir + "/" + DEFAULT_DB_FILE;
    }
    
	public static MazeDatabase openDatabase(String appDir)  {
		
		// Makes sure that the DATA_DIRECTORY and all sub directories exist
		String fName = createDbFileName(appDir);		
		
		return new SqLiteDatabase(fName);
	}	
	
	/*
	public void closeDatabase(MazeDatabase)
	{
		
	}
	*/
}

 
interface MazeDatabase {
	
	Question getNextQuestion(QuestionType questionType);
	
	List<Question> readAllRecords();
	
	MazeQuestion add(MazeQuestion q);	
	
	MazeQuestion remove(int questionId);
	
	boolean update(MazeQuestion q);
	
}

/*
interface IQuestion {
	
	QuestionType getType();
	String getQuestion();
	List<String> getAnswers();
	int getCorrectAnswerIndex();
	List<String> getKeyWords();
}
*/
