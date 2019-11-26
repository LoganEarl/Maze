package maze.model.question;

import java.util.ArrayList;
import java.util.List;

import maze.model.Item;

public interface Question {
	boolean isCorrect(String answer);

	boolean isCorrect(Item keyItem);

	QuestionInfo getInfo();

	String getCorrectAnswer();

	Item constructKeyItem();

	// -- sveta's particulars --
	void setId(int id);

	int getId();

	String getQuestion();

	List<Answer> getAnswers();

	QuestionType getType();
	
	void setName(String name);
	
	String getName();
	
	interface QuestionInfo {
		// Or something to the effect of this.
		String getPromptText();

		String getQuestionType();
	}


    Item STUBBED_ITEM = () -> "STUBBED ITEM";

    Item SKELETON_KEY = () -> "SKELETON KEY";

    Question STUBBED_QUESTION = new Question(){
        public static final String TYPE = "STUBBED";

        public final QuestionType QTYPE = QuestionType.MULTIPLE;

        public int getId() {
        	return 1;
        }
        @Override
        public boolean isCorrect(String answer) {
            return answer.toLowerCase().trim().equals("friend");
        }

        @Override
        public boolean isCorrect(Item keyItem) {
            return keyItem == STUBBED_ITEM || keyItem == SKELETON_KEY;
        }

        @Override
        public QuestionInfo getInfo() {
            return new QuestionInfo(){
                @Override
                public String getPromptText() {
                    return "Say friend and enter";
                }

                @Override
                public String getQuestionType() {
                    return TYPE;
                }
            };
        }

        @Override
        public String getCorrectAnswer() {
            return "friend";
        }

        @Override
        public Item constructKeyItem() {
            return null;
        }

		@Override
		public QuestionType getType() {

			return QTYPE;
		}
		@Override
		public String getQuestion() {
			return "How are you?";
		}
		@Override
		public List<Answer> getAnswers() {
			return new ArrayList<Answer>();
		}

		@Override
		public void setId(int id) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void setName(String name) {
			
		}
		
		@Override
		public String getName() {
			
			return "";
		}
    };
}

interface Answer {
	// gets record id of this answer
	int getId();

	// sets record id of this answer
	void setId(int id);

	// gets answer of this answer
	String getAnswer();

	// sets answer of this answer
	void setAnswer(String answer);

	// gets flag indicating if this answer is correct
	boolean getCorrect();

	// sets flag indicating if this record is correct
	void setCorrect(boolean correct);
}
