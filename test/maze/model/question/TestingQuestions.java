package maze.model.question;

public class TestingQuestions {
    public static final Question[] questions = {
            new StubbedQuestion(0, "q0 prompt. a=answer0", "answer0",
                    new String[]{"answer0", "answer1", "answer2"}, "key0"),
            new StubbedQuestion(1, "q1 prompt. a=answer1", "answer1",
                    new String[]{"answer1", "answer1", "answer2"}, "key1"),
            new StubbedQuestion(2, "q2 prompt. a=answer2", "answer2",
                    new String[]{"answer2", "answer1", "answer2"}, "key2"),
            new StubbedQuestion(0, "q3 prompt. a=answer3", "answer3",
                    new String[]{"answer3", "answer4", "answer5"}, "key0"),
            new StubbedQuestion(1, "q4 prompt. a=answer4", "answer4",
                    new String[]{"answer3", "answer4", "answer5"}, "key1"),
            new StubbedQuestion(2, "q5 prompt. a=answer5", "answer5",
                    new String[]{"answer3", "answer4", "answer5"}, "key2"),
            new StubbedQuestion(0, "q6 prompt. a=answer6", "answer6",
                    new String[]{"answer6", "answer7", "answer8"}, "key0"),
            new StubbedQuestion(1, "q7 prompt. a=answer7", "answer7",
                    new String[]{"answer6", "answer7", "answer8"}, "key1"),
            new StubbedQuestion(2, "q8 prompt. a=answer8", "answer8",
                    new String[]{"answer6", "answer7", "answer8"}, "key2"),
            new StubbedQuestion(0, "q9 prompt. a=answer9", "answer9",
                    new String[]{"answer9", "answer10", "answer11"}, "key0"),
            new StubbedQuestion(1, "q10 prompt. a=answer10", "answer10",
                    new String[]{"answer9", "answer10", "answer11"}, "key1"),
            new StubbedQuestion(2, "q11 prompt. a=answer11", "answer11",
                    new String[]{"answer9", "answer10", "answer11"}, "key2"),
    };
}
