package com.example.listen_and_write.service;

import com.example.listen_and_write.models.Quiz;
import com.example.listen_and_write.models.Sentence;

public class QuizMapping {
    public final static Quiz SentenceToQuiz(Sentence sentence, Long quizLevel) {
        return new Quiz(sentence.getStart(), sentence.getDuration(), sentence.getContent(), quizLevel);
    }
}
