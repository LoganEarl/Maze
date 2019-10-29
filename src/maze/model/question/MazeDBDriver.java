package maze.model.question;

import java.util.Iterator;

public class MazeDBDriver {

	public static void main(String[] args) throws Exception {
		
		IMazeDB db = MazeDBFactory.createMazeDb(MazeDBType.TEXT, "data/QuestionDb.txt");
		
		System.out.println("IMazeDB of type: " + db.getDbType());
		
        Iterator<IQuestion> iterator = db.iterator(); 
        
        System.out.println("List elements : "); 
  
        while (iterator.hasNext()) 
            System.out.println(iterator.next() + " "); 
	}

}
