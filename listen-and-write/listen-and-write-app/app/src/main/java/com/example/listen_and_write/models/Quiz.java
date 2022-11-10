package com.example.listen_and_write.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
public class Quiz {
    private Float duration;
    private Float start;
    private String content;
    private Long quizLevel;
    private List<WordQuiz> wordQuizs;

    public Quiz(Float _start, Float _duration, String _content, Long _quizLevel) {
        start = _start;
        duration = _duration;
        content = _content;
        quizLevel = _quizLevel;
        wordQuizs = Stream.of(content.split(" ")).filter(text -> text != null && !text.equals("")).map(text -> new WordQuiz(text, false)).collect(Collectors.toList());
        List<WordQuiz> pick = new ArrayList<>();
        Random RANDOM = new Random();
        while (pick.size() < quizLevel && pick.size() < wordQuizs.size()) {
            List<WordQuiz> wordLeft = Stream.of(wordQuizs).filter(w -> !pick.contains(w)).collect(Collectors.toList());
            pick.add(wordLeft.get(RANDOM.nextInt(wordLeft.size() -1)));
        }
        Stream.of(pick).map(p -> p.setBlank(true)).collect(Collectors.toList());
    }

    public Float getStart() {
        return start;
    }

    public void setStart(Float start) {
        this.start = start;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<WordQuiz> getWordQuizs() {
        return wordQuizs;
    }

    public void setWordQuizs(List<WordQuiz> wordQuizs) {
        this.wordQuizs = wordQuizs;
    }

    public Long getQuizLevel() {
        return quizLevel;
    }

    public void setQuizLevel(Long quizLevel) {
        this.quizLevel = quizLevel;
    }
}
