package com.hoangdevelopers.listen_and_write.service;

import com.hoangdevelopers.listen_and_write.models.Paragraph;
import com.hoangdevelopers.listen_and_write.models.Quiz;
import com.hoangdevelopers.listen_and_write.models.Sentence;

public class QuizMapping {
    public final static Quiz SentenceToQuiz(Sentence sentence, Long quizLevel) {
        return new Quiz(sentence, quizLevel);
    }

    public final static Quiz SentenceToQuiz(Paragraph paragraph, Long quizLevel) {
        return new Quiz(paragraph, quizLevel);
    }
}
