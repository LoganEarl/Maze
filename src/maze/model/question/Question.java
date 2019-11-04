package maze.model.question;

import maze.model.Item;

public interface Question {
    boolean isCorrect(String answer);
    boolean isCorrect(Item keyItem);
    QuestionInfo getInfo();
    Item constructKeyItem();
    int getQuestionID();

    interface QuestionInfo {
        //Or something to the effect of this.
        String getPromptText();
        String getQuestionType();
        String getCorrectAnswer();
    }

    Item SKELETON_KEY = () -> "Skeleton Key";

    Item STUBBED_ITEM = () -> "StubbedItem";
    Question STUBBED_QUESTION = new Question(){
        public static final String TYPE = "STUBBED";

        @Override
        public boolean isCorrect(String answer) {
            return answer.toLowerCase().trim().contains("friend");
        }

        @Override
        public boolean isCorrect(Item keyItem) {
            return keyItem == STUBBED_ITEM || keyItem == SKELETON_KEY;
        }

        @Override
        public int getQuestionID() {
            return 0;
        }

        @Override
        public QuestionInfo getInfo() {
            return new QuestionInfo(){
                @Override
                public String getCorrectAnswer() {
                    return "friend";
                }

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
        public Item constructKeyItem() {
            return STUBBED_ITEM;
        }
    };
}
