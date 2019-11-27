package maze.model.question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// Textdatabase
public class QuestionImporter {

	public List<Question> questions = new ArrayList<Question>();

	public QuestionImporter(String FileName) throws Exception {

		Scanner scanner = new Scanner(new File(FileName));

		parse(scanner);
	}

	private QuestionImporter() {
	}
	
	void checkIllegalTokens(String line) throws Exception {
		String [] tokens = {
			"[MULT,",
			"[SH,",
			"[TF,"
		};
		
		for(String t: tokens) {
			int idx = line.indexOf(t);
			
			if(idx != -1 && idx > 0) {
				throw new Exception("Illegal line: " + t);
			}
		}
	}

	public void parse(Scanner scanner) throws Exception {

		List<String> lines = new ArrayList<String>();

		int questionId = 1;
		int answerId = 1;

		while (scanner.hasNext()) {

			lines.add(scanner.nextLine());
		}

		scanner.close();

		///////////////////////////////////////////////////////////////////////
		boolean isNextLineQuestion = false;
		int readAnswerCount = -1; // -1 means we are not reading
		QuestionHeader header = null;
		MazeQuestion question = null;
		
		int lineIdx = -1;

		for (String line : lines) {
			
			lineIdx++; // indicator so we can look at previous lines.

			if (line.startsWith("#")) {
				continue;
			}
			
			checkIllegalTokens(line);

			if (isNextLineQuestion) {
				// question.question = line;
				question.setQuestion(line);
				isNextLineQuestion = false;
				continue;
			} else if (readAnswerCount > 0) {
				
				if (line.startsWith("[")) {
					throw new Exception("Unexpected question start: " + line + ". Prev: " + lines.get(lineIdx - 1));
				}
				
				MazeAnswer answer = new MazeAnswer(answerId++, line, false);
				
				if(answer.getAnswer().length() < 1) {
					throw new Exception("Short answer '" + answer.getAnswer() + "' for Q:" + question.getQuestion());
				}

				// Current size of the answer list is an index for the next quest. So it is OK
				// to compare the correct index to it.
				if (header.correctAnswerIndex == question.getAnswers().size())
					answer.setCorrect(true);

				question.getAnswers().add(answer);
				readAnswerCount--;

				if (readAnswerCount == 0) {
					question.setId(questionId++);
					questions.add(question);
					question = null;
					header = null;
					isNextLineQuestion = false;
					readAnswerCount = -1;
				} else
					continue;
			} else { // reset and start looking for []
				question = null;
				header = null;
				isNextLineQuestion = false;
				readAnswerCount = -1;
			}

			if (line.startsWith("[")) {
				
				if( question != null ) {
					throw new Exception("Unexpected question start: " + line + ". Prev: " + lines.get(lineIdx - 1));
				}

				header = QuestionHeader.parse(line);

				if (header != null) {
					question = new MazeQuestion();
					question.setType(header.type);

					if (header.keywords.size() > 0)
						question.getKeywords().addAll(header.keywords);

					isNextLineQuestion = true;
					readAnswerCount = header.answersCount;
					// System.out.println(header);
				}
			}
		}

	}

	int index = -1;

	public Question getNextQuestion(QuestionType questionType) {

		index = (index + 1) % questions.size();
		return questions.get(index);
	}

	public List<Question> getQuestions() {
		// TODO Auto-generated method stub
		return questions;
	}

	public static QuestionImporter parseString(String questions) throws Exception {
		QuestionImporter db = new QuestionImporter();

		Scanner scanner = new Scanner(questions);

		db.parse(scanner);

		return db;
	}

	public static QuestionImporter getDefaultQuestions() throws Exception {
		return parseString(DefaultQuestions.QUESTIONS);
	}

}

class QuestionHeader {

	public QuestionType type;
	public int answersCount; // a,b,c, - thats 3!
	public int correctAnswerIndex; // answer is b so 1
	public ArrayList<String> keywords = new ArrayList<String>(); // keyword that identifies item

	public String toString() {

		return this.type + ", " + this.answersCount + ", " + this.correctAnswerIndex + ", " + this.keywords.size();
	}

	public static QuestionHeader parse(String line) {

		QuestionHeader header = new QuestionHeader();

		line = line.replace("[", "").replace("]", "");

		String[] parts = line.split(",");

		if (parts.length > 2) {

			switch (parts[0]) {
			case "TF":
				header.type = maze.model.question.QuestionType.TRUE_FALSE;
				break;
			case "SH":
				header.type = maze.model.question.QuestionType.SHORT;
				break;
			case "MULT":
				header.type = maze.model.question.QuestionType.MULTIPLE;
				break;
			default:
				return null;
			}

			// Parse number of questions
			try {
				header.answersCount = Integer.parseInt(parts[1]);

			} catch (Exception e) {
				return null; // invalid record
			}

			// Parse correct index for answer
			try {
				header.correctAnswerIndex = Integer.parseInt(parts[2]);

			} catch (Exception e) {
				return null; // invalid record
			}

			// if we are here we need to check for keywords
			if (parts.length > 3) {

				try {
					int count = Integer.parseInt(parts[3]);

					for (int i = 0; i < count; i++) {

						int ind = 3 + i;

						if (ind < parts.length) {

							header.keywords.add(parts[ind]);
						}
					}
				} catch (Exception e) {
				}

			}

			return header;

		}

		return null;
	}

}


class DefaultQuestions
{
	public static final String QUESTIONS = 
		"# TF\n" +
		"# MULT\n" +
		"# SH\n" +
		"# |------------ REQUIRED ----------|------------- OPITIONAL ------------------|\n" +
		"# [FORMAT,NUM-ANSWERS,CORRECT-INDEX,NUMBER-OF-KEYWORDS,KEYWORD_0,...,KEYWORD_N]\n" +
	
		"[TF,1,0]\n" +
		"The color of sky is blue?\n" +
		"true\n" +		
		
		"[TF,1,0]\n" +
		"The 'D' in D-Day stands for'Dooms-day'\n" +
		"false\n" +
		
		"[TF,1,0]\n" +
		"Meteorology the study of weather?\n" +
		"true\n" +	
		
		"[TF,1,0]\n" +
		"Cats can be allergic to humans?\n" +
		"true\n" +
		
		"[TF,1,0]\n" +
		"In Lord of the Rings, 20 Rings of Power were forged?\n" +
		"true\n" +
	
		"[SH,2,0]\n" +
		"What is the color of the sky?\n" +
		"blue\n" +
		"blueish\n" +
		
		"[SH,1,0]\n" +
		"What is the symbol for potassium?\n" +
		"k\n" +
	
		"[SH,1,0]\n" +
		"Is Java a type of OS?\n" +
		"no\n" +
	
		"[SH,1,0]\n" +
		"Which email service is owned by Microsoft?\n" +
		"hotmail\n" +
	
		"[SH,1,0]\n" +
		"What year was the very first model of the iPhone released?\n" +
		"2007\n" +
	
		"[MULT,3,0]\n" +
		"What was a Puffing Billy?\n" +
		"Steam Train\n" +
		"Steam Engine\n" +
		"Steam Boat\n" +
		
		"[MULT,3,2]\n" +
		"What is the bluebird a symbol of?\n" +
		"Wisdom\n" +
		"Joy\n" +
		"Happiness\n" +		
		
		"[MULT,3,0]\n" +
		"Finish this sentence, \"My name is Inigo Montoya...\"\n" +
		"You killed my father, prepare to die.\n" +
		"Would you like fries with that?\n" +
		"Gimme a hive five!\n" +	
		
		"[MULT,3,2]\n" +
		"Where were cats once the most honored?\n" +
		"Greece\n" +
		"China\n" +
		"Egypt\n" +		
		
		
		"[MULT,3,0]\n" +
		"What gift does Lady Galadriel give to Gimli before the fellowship leaves Lothlorien?\"\n" +
		"Three stands of her hair\n" +
		"Elvin rope\n" +
		"An ancient axe known as Dramborleg\n" +
	
		"[MULT,3,1]\n" +
		"Wesley's eyes are the color of?\n" +
		"The fire of a thousand suns.\n" +
		"High seas after a storm.\n" +
		"Black as the night sky.\n" +
	
		"[MULT,3,1]\n" +
		"Lying \"mostly dead\" on Miracle Max's table, Wesley responds to Max's question \"What's so important?\" with what response?\n" +
		"To blave\n" +
		"True love\n" +
		"As you wish\n" +
					
		"[MULT,3,2]\n" +
		"What is the name of the Six-Fingered Man?\n" +
		"Prince Humperdink\n" +
		"Inigo Montoya\n" +
		"Count Rugen\n" +
		
		"[MULT,3,2]\n" +
		"What does \"R.O.U.S.\" stand for?\n" +
		"Rotten old umbrella stands\n" +
		"Rats of usual shape\n" +
		"Rodents of unusual size\n" +
		
		"[MULT,3,1]\n" +
		"DEF CON usually takes place during..?\n" +
		"last week of June or the first week of July\n" +
		"last week of July or the first week of Augest\n" +
		"mid Augest or the end of Augest\n" +
		
		"[MULT,3,1]\n" +
		"Who is often called the father of the computer?\n" +
		"Marie Curie\n" +
		"Charles Babbage\n" +
		"James Gosling\n" +
		
		"[MULT,3,2]\n" +
		"What is the only edible food that is said to never spoil?\n" +
		"Sugar\n" +
		"Honey\n" +
		"Crackers\n" +
		
		"[MULT,3,0]\n" +
		"Who is the creator of C programming language?\n" +
		"Dennis Ritchie\n" +
		"Charles Babbage\n" +
		"James Gosling\n" +
		
		"[MULT,3,0]\n" +
		"What was Twitter’s original name?\n" +
		"twttr\n" +
		"twit\n" +
		"twtr\n" +
		
		"[MULT,3,0]\n" +
		"What was Twitter’s original name?\n" +
		"twttr\n" +
		"twit\n" +
		"twtr\n" +
		
		"[MULT,3,0]\n" +
		"What year was the ps4 released\n" +
		"2013\n" +
		"2014\n" +
		"2015\n" +
		
		"[MULT,3,1]\n" +
		"Which country once knighted a penguin?\n" +
		"England\n" +
		"Norway\n" +
		"Greenland\n" +
		
		"[MULT,3,0]\n" +
		"What is the name of the poison that Westley uses to match wits with Vincini?\n" +
		"Iokane\n" +
		"Cyanide\n" +
		"Iodine\n" +
		
		"[MULT,3,1]\n" +
		"In Princess Bride, is Westley left handed ?\n" +
		"Yes\n" +
		"No\n" +
		"Neither\n";
}	



