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

    interface QuestionInfo {
        //Or something to the effect of this.
        String getPromptText();
        String getQuestionType();
    }










    Question stubbedQuestion = new Question(){
        public static final String TYPE = "STUBBED";
        
        public final QuestionType QTYPE = QuestionType.MULTIPLE;

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
	TRUE_FALSE,
	MULTIPLE,
	SHORT
}