package maze.model.question;

import maze.model.Item;

public interface Question {
    boolean isCorrect(String answer);
    boolean isCorrect(Item keyItem);
    QuestionInfo getInfo();
    String getCorrectAnswer();
    Item constructKeyItem();
    
    
    //sveta's
    QuestionType getType();
    int getId();

    interface QuestionInfo {
        //Or something to the effect of this.
        String getPromptText();
        String getQuestionType();
    }

    

    Question stubbedQuestion = new Question(){
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
            return false;
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
    };
}

enum QuestionType {
	MULTIPLE(0),
	TRUE_FALSE(1),
	SHORT(2);
	
	private final int value;
    private QuestionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
