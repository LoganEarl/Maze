package maze.db;

import java.util.List;

public class MazeDBFactory {
	
	public static IMazeDB createMazeDb(MazeDBType DbType, String FileName) throws Exception {
		
		if(DbType == MazeDBType.TEXT)
			return new TextDatabase(FileName);
		
		throw new Exception("DB w/ sqLite not supported yet");
		//return ;
	}
	
}

interface IMazeDB extends Iterable<IQuestion> {
	MazeDBType getDbType();
	
	IQuestion getNextQuestion(QuestionType questionType);
}

enum QuestionType {
	TRUE_FALSE,
	MULTIPLE,
	SHORT
}

enum MazeDBType {
	TEXT,
	SQLITE
}

interface IQuestion {
	
	QuestionType getType();
	String getQuestion();
	List<String> getAnswers();
	int getCorrectAnswerIndex();
	List<String> getKeyWords();
}