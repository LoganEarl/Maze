package maze.model.question;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;

public class DatabaseManager extends SqLiteDatabase {
	
    /**
     * This is the declaration for the /data directory used to store db files in
     */
    private static final String DATA_DIRECTORY = System.getProperty("user.dir").replace("\\", "/") + "/data/";
    
    private static final String DEFAULT_DB_FILE = "mazedb.sqlite3";
    
    private DatabaseManager(String fullFileName)
    {
    	super(fullFileName);
    }
    
    static void createDirectories(String appDir) {
        File f = new File(DATA_DIRECTORY + appDir + "/");
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.mkdirs();
        }
    }
    
    static String createDbFileName(String appDir) {
		createDirectories(appDir);
		
		return DATA_DIRECTORY + appDir + "/" + DEFAULT_DB_FILE;
    }
    
	private static MazeDatabase openDatabase(String appDir)  {
		
		// Makes sure that the DATA_DIRECTORY and all sub directories exist
		String fname = createDbFileName(appDir);		
		
		return new SqLiteDatabase(fname);			
	}
	
	private static void  deleteIfExists(String appDir){
		String fname = createDbFileName(appDir);		

        File f = new File(fname);
        if (f.exists()) {
        	f.delete();
        }
        
	}
	
	public static MazeDatabase createDatabaseWithDefaultQuestions(String appDir) {
		String fname = createDbFileName(appDir);		

		deleteIfExists(appDir);
        
        MazeDatabase db = new SqLiteDatabase(fname);
        
        ImportDefault(db);
        
        return db;
        
	}
	
	public static void importDatabase(String fname, String appDir, boolean deleteDatabaseFirst) throws FileNotFoundException {
		QuestionImporter importer = new QuestionImporter(fname);
		
		if(deleteDatabaseFirst)
		{
			deleteIfExists(appDir);
		}
		
		MazeDatabase db = openDatabase(appDir);
		
		for(Question q: importer.getQuestions())
		{
			db.insert(q);
		}
	}

	static void ImportDefault(MazeDatabase db) {
		QuestionImporter defaults = QuestionImporter.getDefaultQuestions();
		
		for(Question q: defaults.getQuestions()) {	
			/*
			if(db.insert(q))
				System.out.println("Inserted: " + q);
			else
				System.out.println("Existed: " + q);
			*/
			db.insert(q);
		}
	}
}
