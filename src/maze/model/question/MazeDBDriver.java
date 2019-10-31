package maze.model.question;

import java.util.Iterator;

public class MazeDBDriver {

	public static void driver(String[] args) throws Exception {
		
		IMazeDB db = MazeDBFactory.createMazeDb(MazeDBType.TEXT, "QuestionDb.txt");
		
		System.out.println("IMazeDB of type: " + db.getDbType());
		
        Iterator<Question> iterator = db.iterator(); 
        
        System.out.println("List elements : "); 
  
        while (iterator.hasNext()) 
            System.out.println(iterator.next() + " "); 
	}

}
