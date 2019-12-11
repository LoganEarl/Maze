package maze.model.question;

import maze.model.Item;

public class StubbedQuestion implements Question {
    private int id;
    private String prompt;
    private String correctAnswer;
    private String[] possibleAnswers;
    private String keyItemName;

    public StubbedQuestion(int id, String prompt, String correctAnswer, String[] possibleAnswers, String keyItemName) {
        this.id = id;
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = possibleAnswers;
        this.keyItemName = keyItemName;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    @Override
    public String getQuestionType() {
        return null;
    }

    @Override
    public Item constructKeyItem() {
        return new StubbedDoorItem(keyItemName);
    }

    @Override
    public boolean isCorrect(String answer) {
        return answer.equals(correctAnswer);
    }

    @Override
    public boolean isCorrect(Item key) {
        return key.getName().equals(keyItemName);
    }
}
