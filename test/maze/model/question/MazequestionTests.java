package maze.model.question;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maze.model.question.Question;
import maze.model.question.QuestionImporter;

class MazequestionTests {
	
	QuestionImporter db = null;
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
		 db = QuestionImporter.getDefaultQuestions(); //DatabaseManager.createMazeDb(MazeDBType.TEXT, "QuestionDb.txt");

	      for (Question q: db.getQuestions()) 
	      {
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

	@Test
	void ParserTest() {
		//System.out.println("IMazeDB of type: " + db.getDbType());
		
        
        System.out.println("List elements : "); 
  
        for (Question q: db.getQuestions()) {
            System.out.println(q + " ");
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
			assert(userResponse);
			
			//Inversion
			assert(!next.isCorrect(!userResponse + ""));
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
			assert(userResponse);
			
			//Wrong answer
			assert(!next.isCorrect(userResponse + "haha"));

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
			assert(userResponse);
			
			//Wrong answer
			assert(!next.isCorrect(userResponse + "haha"));

		}
			
	}

}
