package maze.model.question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TextDatabase implements MazeDatabase {

	public List<Question> questions = new ArrayList<Question>();

	public TextDatabase(String FileName) throws FileNotFoundException {

		parse(FileName);
	}

	public void parse(String FileName) throws FileNotFoundException {

		List<String> lines = new ArrayList<String>();
		
		int questionId = 1;
		int answerId = 1;

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
				MazeAnswer answer = new MazeAnswer(answerId++, line, false);
				
				// Current size of the answer list is an index for the next quest. So it is OK
				// to compare the correct index to it.
				if(header.correctAnswerIndex == question.answers.size())
					answer.correct = true;
					
				question.answers.add(new MazeAnswer(answerId++, line, false));
				readAnswerCount--;	
				
				if(readAnswerCount == 0)
				{
					question.id = questionId++;
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
					
					if(header.keywords.size() > 0)
						question.keywords.addAll(header.keywords);
					
					isNextLineQuestion = true;
					readAnswerCount = header.answersCount;					
					 //System.out.println(header);
				}
			}
		}

	}
	
	int index = -1;
	
	public Question getNextQuestion(QuestionType questionType) {
		
		index = (index + 1) % questions.size();
		return questions.get(index);
	}

	@Override
	public List<Question> readAllRecords() {
		// TODO Auto-generated method stub
		return questions;
	}

	@Override
	public MazeQuestion add(MazeQuestion q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MazeQuestion remove(int questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(MazeQuestion q) {
		// TODO Auto-generated method stub
		return false;
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
