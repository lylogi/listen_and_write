package com.hoangdevelopers.listen_and_write.models;

import java.util.UUID;

public class WordQuiz {
    private String id = UUID.randomUUID().toString();
    private String word;
    private String wordNormalize;
    private boolean isBlank;
    private boolean done;
    private boolean isIgnoreQuest;

    public WordQuiz(String _word, boolean _isBlank, boolean _isIgnoreQuest) {
        word = _word.trim();
        wordNormalize = word.replaceAll("[^A-Za-z0-9\\']+", "").toLowerCase();
        isBlank = _isBlank;
        done = !isBlank;
        isIgnoreQuest = _isIgnoreQuest;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isBlank() {
        return isBlank;
    }

    public WordQuiz setBlank(boolean blank) {
        isBlank = blank;
        done = !blank;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isIgnoreQuest() {
        return isIgnoreQuest;
    }

    public void setIgnoreQuest(boolean ignoreQuest) {
        isIgnoreQuest = ignoreQuest;
    }

    public String getWordNormalize() {
        return wordNormalize;
    }

    public void setWordNormalize(String wordNormalize) {
        this.wordNormalize = wordNormalize;
    }

    public String getId() {
        return id;
    }
}
