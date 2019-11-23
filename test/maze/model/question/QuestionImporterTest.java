package maze.model.question;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maze.model.question.QuestionImporter;

class QuestionImporterTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	void detectNewLineError(String question) throws Exception {
		Exception expectedEx = null;
		
		try {
			QuestionImporter.parseString(question);
		} catch(Exception ex) {
			expectedEx = ex;
		}
		
		if(expectedEx == null) {
			throw new Exception("Parser did not detect error");
		}
	}

	@Test
	void ImporterTest() throws Exception {
		
		
		String TF_NoNewLine = "[TF,1,0]\n" +
				"The 'D' in D-Day stands for'Dooms-day'" + // malformed - no new line
				"false\n" +
				
				"[TF,1,0]\n" +
				"Meteorology the study of weather?\n" +
				"true\n";
		
		detectNewLineError(TF_NoNewLine);
		
		String twoQuestionFirstMalformed =
				"[MULT,3,2]\n" +
				"Where were cats once the most honored?\n" +
				"Greece\n" +
				"China" + // no new line
				"Egypt" + // no new line
				
				"[MULT,3,0]\n" +
				"What gift does Lady Galadriel give to Gimli before the fellowship leaves Lothlorien?\"\n" +
				"Three stands of her hair\n" +
				"Elvin rope\n" +
				"An ancient axe known as Dramborleg\n";
		
		detectNewLineError(twoQuestionFirstMalformed);

	}
	
	@Test
	void testDefaultQuestion() throws Exception {
		QuestionImporter.getDefaultQuestions();
	}

}
