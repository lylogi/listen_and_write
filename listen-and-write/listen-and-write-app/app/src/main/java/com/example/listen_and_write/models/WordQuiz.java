package com.example.listen_and_write.models;

public class WordQuiz {
    private String word;
    private boolean isBlank;
    private boolean done;
    public WordQuiz(String _word, boolean _isBlank) {
        word = _word.trim();
        isBlank = _isBlank;
        done = !isBlank;
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
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
