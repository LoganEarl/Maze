package maze.model.question;

import maze.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MazeQuestion implements Question {

    public int id;
    public String question;
    public List<MazeAnswer> answers = new ArrayList<MazeAnswer>();
    public List<String> keywords = new ArrayList<String>(); //list of item names that can answer question for you
    public QuestionType type = maze.model.question.QuestionType.MULTIPLE;

    public int getId() {
        return this.id;
    }

    public QuestionType getType() {
        return this.type;
    }


    public String getQuestion() {
        return this.question;
    }

    public List<MazeAnswer> getAnswers() {

        return this.answers;
    }

    public List<String> getKeywords() {
        return keywords;
    }


    public int getCorrectAnswerIndex() {
        int index = 0;
        for (MazeAnswer a : answers) {
            if (a.correct) {
                return index;
            }

            index++;
        }

        return 0;
    }


    public List<String> getKeyWords() {
        return this.keywords;
    }

    public String toString() {
        return "Q: " + question + "\n  A: " + this.answers.get(getCorrectAnswerIndex()) + "\n";
    }


    @Override
    // 1. for t/f Q's valid user input: t, true, f, false
    //
    public boolean isCorrect(String answer) {
        if (this.type == QuestionType.TRUE_FALSE) {
            //FORMAT: true/false in txt
            String userAnswer = "false";

            switch (answer.toLowerCase()) {
                case "t":
                case "true":
                    userAnswer = "true";
                    break;
                case "f":
                case "false":
                    break;
                default:
                    return false;
            }

            return userAnswer.compareTo(this.answers.get(0).answer) == 0;
        }

        if (this.type == QuestionType.MULTIPLE) {
            return (answer.compareToIgnoreCase(answers.get(getCorrectAnswerIndex()).answer) == 0);
        }

        if (this.type == QuestionType.SHORT) {
            for (MazeAnswer ans : answers) {
                if (answer.compareToIgnoreCase(ans.answer) == 0)
                    return true;
            }
        }

        return false;
    }


    @Override
    public boolean isCorrect(Item keyItem) {

        for (String key : keywords) {
            if (keyItem.getName().compareToIgnoreCase(key) == 0)
                return true;
        }
        return false;
    }

    class MazeQuestionInfo implements QuestionInfo {

        MazeQuestion mq;

        public MazeQuestionInfo(MazeQuestion mq) {

            this.mq = mq;
        }

        public String getPromptText() {

            //return this.mq.getQuestion();
        	/*
        	 * Q: Wesley's eyes are the color of?
				 1. The fire of a thousand suns.
				 2. High seas after a storm.
				 3. Black as the night sky.
        	 */

            StringBuilder str = new StringBuilder();

            str.append("Q: " + mq.question + "\n");

            if (mq.type == QuestionType.MULTIPLE) {

                for (int i = 0; i < mq.answers.size(); i++) {
                    str.append("  " + (i + 1) + ". " + mq.answers.get(i) + "\n");
                }
            } else if (mq.type == QuestionType.TRUE_FALSE) {
                str.append("  1. True\n");
                str.append("  2. False\n");
            }

            return str.toString();

        }

        public String getQuestionType() {

            return this.mq.getType().toString();
        }
    }

    @Override
    public QuestionInfo getInfo() {

        return new MazeQuestionInfo(this);
    }


    @Override
    public String getCorrectAnswer() {
        return this.answers.get(this.getCorrectAnswerIndex()).answer;
    }

    class MazeItem implements Item {

        String name;

        public MazeItem(String name) {

            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    @Override
    public Item constructKeyItem() {

        return new MazeItem(this.keywords.size() > 0 ? this.keywords.get(0) : "gold");
    }
}

class MazeAnswer {
    public int id;
    public String answer;
    public boolean correct;

    public MazeAnswer(int id, String answer, boolean correct) {
        this.id = id;
        this.answer = answer;
        this.correct = correct;
    }

    public MazeAnswer(String answer, boolean correct) {
        this(0, answer, correct);
    }

    public String toString() {
        return answer + "[" + (correct ? "CORRECT" : "WRONG") + "]";
    }
}
