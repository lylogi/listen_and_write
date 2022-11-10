package com.hoangdevelopers.listen_and_write.models;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    private List<Sentence> sentences = new ArrayList<>();
    private Float start = 0f;
    private Float duration = 0f;
    private int index;

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
        onSentenceAdded();
    }

    private void onSentenceAdded() {
        start = sentences.get(0).getStart();
        duration = sentences.get(sentences.size() - 1).getStart() + sentences.get(sentences.size() - 1).getDuration() - start;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
