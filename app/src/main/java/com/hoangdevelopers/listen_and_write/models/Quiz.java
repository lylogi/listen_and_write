package com.hoangdevelopers.listen_and_write.models;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Quiz {
    private String id = UUID.randomUUID().toString();
    private Float start;
    private Float duration;
    private Long quizLevel;
    private List<WordQuiz> wordQuizs = new ArrayList<>();

    private int index;

    public boolean isIgnoreQuest() {
        return isIgnoreQuest;
    }

    public void setIgnoreQuest(boolean ignoreQuest) {
        isIgnoreQuest = ignoreQuest;
    }

    private boolean isIgnoreQuest;

    public Quiz() {
    }

    public Quiz(Sentence sentence, Long _quizLevel) {
        start = sentence.getStart();
        duration = sentence.getDuration();
        String content = sentence.getContent();
        quizLevel = _quizLevel;
        isIgnoreQuest = sentence.isIgnoreQuest();
        index = sentence.getIndex();
        wordQuizs = Stream.of(content.split(" ")).filter(text -> text != null && !text.equals("")).map(text -> new WordQuiz(text, false, isIgnoreQuest)).collect(Collectors.toList());
        if (!isIgnoreQuest) {
            List<WordQuiz> pick = new ArrayList<>();
            Random RANDOM = new Random();
            while (pick.size() < quizLevel && pick.size() < wordQuizs.size()) {
                List<WordQuiz> wordLeft = Stream.of(wordQuizs).filter(w -> !pick.contains(w)).collect(Collectors.toList());
                if (wordLeft.size() == 1) {
                    pick.add(wordLeft.get(0));
                } else {
                    pick.add(wordLeft.get(RANDOM.nextInt(wordLeft.size() - 1)));
                }
            }
            Stream.of(pick).map(p -> p.setBlank(true)).collect(Collectors.toList());
        }
    }

    public Quiz(Paragraph paragraph, Long _quizLevel) {
        start = paragraph.getStart();
        duration = paragraph.getDuration();
        quizLevel = _quizLevel;
        index = paragraph.getIndex();
        for (Sentence sentence : paragraph.getSentences()) {
            wordQuizs = Stream.concat(
                    Stream.of(wordQuizs),
                    Stream.of(sentence.getContent().split(" "))
                            .filter(text -> text != null && !text.equals(""))
                            .map(text -> new WordQuiz(text, false, sentence.isIgnoreQuest())))
                    .collect(Collectors.toList());
        }
        List<WordQuiz> wordQuizAble = Stream.of(wordQuizs).filter(w -> !w.isIgnoreQuest()).collect(Collectors.toList());
        if (wordQuizAble.size() > 0) {
            List<WordQuiz> pick = new ArrayList<>();
            Random RANDOM = new Random();
            while (pick.size() < quizLevel && pick.size() < wordQuizAble.size()) {
                List<WordQuiz> wordLeft = Stream.of(wordQuizAble).filter(w -> !pick.contains(w)).collect(Collectors.toList());
                if (wordLeft.size() == 1) {
                    pick.add(wordLeft.get(0));
                } else {
                    pick.add(wordLeft.get(RANDOM.nextInt(wordLeft.size() - 1)));
                }
            }
            isIgnoreQuest = pick.size() == 0;
            Stream.of(pick).map(p -> p.setBlank(true)).collect(Collectors.toList());
        }
    }

    public Optional<WordQuiz> getNextWordQuizBlank() {
        return Stream.of(wordQuizs).filter(w -> w.isBlank() && !w.isDone()).findFirst();
    }

    public Quiz clone() {
        Quiz quiz = new Quiz();
        quiz.id = id;
        quiz.index = index;
        quiz.start = start;
        quiz.duration = duration;
        quiz.quizLevel = quizLevel;
        quiz.wordQuizs = wordQuizs;
        quiz.isIgnoreQuest = isIgnoreQuest;
        return quiz;

    }

    public String getId() {
        return id;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
