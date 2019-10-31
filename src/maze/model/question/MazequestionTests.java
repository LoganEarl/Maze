package maze.model.question;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MazequestionTests {
	
	IMazeDB db = null;
	ArrayList<Question> tfQuestions = new ArrayList<Question>();
	ArrayList<Question> multQuestions = new ArrayList<Question>();
	ArrayList<Question> shQuestions = new ArrayList<Question>();
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		 db = MazeDBFactory.createMazeDb(MazeDBType.TEXT, "QuestionDb.txt");

	     Iterator<Question> iterator = db.iterator(); 

	      while (iterator.hasNext()) 
	      {
	    	 Question q = iterator.next();
	    	  switch(q.getType())
	    	  {
	    	  	case MULTIPLE:
	    	  		multQuestions.add(q);
	    	  		break;	    	  	
	    	  	case TRUE_FALSE:
	    	  		tfQuestions.add(q);
		    	  	break;
	    	  	case SHORT:
		    	  	shQuestions.add(q);
		    	  	break;	    	  	   		  	    	
	    	  }
	    	  
	      }
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	// @Test
	void ParserTest() {
		System.out.println("IMazeDB of type: " + db.getDbType());
		
        Iterator<Question> iterator = db.iterator(); 
        
        System.out.println("List elements : "); 
  
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
	}
	
	@Test
	void UserInteractionHappyPath() {
		
		System.out.printf("Testing T/F questions (%d)", tfQuestions.size());
		
		for(Question next: tfQuestions) {
			
			// Next question - simulate user input
			
			System.out.println(next);
			
			// Simulate answer
			String answer = next.getCorrectAnswer();
			
			System.out.printf("Answer: %s\n", answer);
			
			boolean userResponse = next.isCorrect(answer);
			// Check Answer
			Assert.assertEquals(true, userResponse);
			
			//Inversion
			Assert.assertEquals(false, next.isCorrect(!userResponse + ""));
		}
		
		
		System.out.printf("Testing MULT questions (%d)", multQuestions.size());
		
		for(Question next: multQuestions) {
			
			// Next question - simulate user input
			
			System.out.println(next);
			
			// Simulate answer
			String answer = next.getCorrectAnswer();
			
			System.out.printf("Answer: %s\n", answer);
			
			boolean userResponse = next.isCorrect(answer);
			// Check Answer
			Assert.assertEquals(true, userResponse);
			
			//Wrong answer
			Assert.assertEquals(false, next.isCorrect(userResponse + "haha"));

		}
		
		
		System.out.printf("Testing SHORT questions (%d)", shQuestions.size());
		
		for(Question next: shQuestions) {
			
			// Next question - simulate user input
			
			System.out.println(next);
			
			// Simulate answer
			String answer = next.getCorrectAnswer();
			
			System.out.printf("Answer: %s\n", answer);
			
			boolean userResponse = next.isCorrect(answer);
			// Check Answer
			Assert.assertEquals(true, userResponse);
			
			//Wrong answer
			Assert.assertEquals(false, next.isCorrect(userResponse + "haha"));

		}
			
	}

}
