package maze.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TextDatabase implements IMazeDB {

	public List<IQuestion> questions = new ArrayList<IQuestion>();

	public TextDatabase(String FileName) throws FileNotFoundException {

		parse(FileName);
	}

	public void parse(String FileName) throws FileNotFoundException {

		List<String> lines = new ArrayList<String>();

		Scanner scanner = new Scanner(new File(FileName));

		while (scanner.hasNext()) {

			lines.add(scanner.nextLine());
		}

		scanner.close();
		
		///////////////////////////////////////////////////////////////////////
		boolean isNextLineQuestion = false;
		int readAnswerCount = -1; //-1 means we are not reading
		QuestionHeader header = null;
		MazeQuestion question = null;

		
		for (String line : lines) {

			if (line.startsWith("#")) {
				continue;
			}
			
			if(isNextLineQuestion)
			{
				question.question = line;
				isNextLineQuestion = false;
				continue;
			}
			else if(readAnswerCount > 0)
			{
				question.answers.add(line);
				readAnswerCount--;	
				
				if(readAnswerCount == 0)
				{
					questions.add(question);
					question = null;
					header = null;
					isNextLineQuestion = false;
					readAnswerCount = -1;
				}
				else
					continue;
			}
			else // reset and start looking for []
			{
				question = null;
				header = null;
				isNextLineQuestion = false;
				readAnswerCount = -1;
			}
			

			
			if (line.startsWith("[")) {

				header = QuestionHeader.parse(line);
				
				if(header != null) 
				{
					question = new MazeQuestion();
					question.type = header.type;
					question.answerIndex = header.correctAnswerIndex;
					
					if(header.keywords.size() > 0)
						question.keywords.addAll(header.keywords);
					
					isNextLineQuestion = true;
					readAnswerCount = header.answersCount;					
					 //System.out.println(header);
				}
			}
		}

	}

	public IQuestion getNextQuestion(QuestionType questionType) {

		return null;
	}

	@Override
	public MazeDBType getDbType() {
		return MazeDBType.TEXT;
	}

	@Override
	public Iterator<IQuestion> iterator() {
		
		return new TextDBIterator(this);
	}
	
	
	class TextDBIterator implements Iterator<IQuestion> {
		
		TextDatabase db;
		int currentIndex = -1;
		
		public TextDBIterator(TextDatabase db) {
			this.db = db;
			
		}
		@Override
		public boolean hasNext() {
			int next = currentIndex + 1;
			
			return next < db.questions.size();
		}

		@Override
		public IQuestion next() {
			
			if(hasNext()) {
				return db.questions.get(++currentIndex);
			}
			
			throw new IndexOutOfBoundsException();	
		}
		
		
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
				header.type = QuestionType.TRUE_FALSE;
				break;
			case "SH":
				header.type = QuestionType.SHORT;
				break;
			case "MULT":
				header.type = QuestionType.MULTIPLE;
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