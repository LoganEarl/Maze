package maze.model.question;

import java.util.Collection;
import java.util.Set;

public interface QuestionDataSource extends Iterable<Question>{
    Set<Question> getAllQuestions();

    class Iterator implements java.util.Iterator<Question> {
        private Question[] questions;
        int index = 0;

        public Iterator(Collection<Question> questions) {
            this.questions = questions.toArray(new Question[0]);
        }

        @Override
        public boolean hasNext() {
            return index < questions.length;
        }

        @Override
        public Question next() {
            return questions[index++];
        }
    }
}
