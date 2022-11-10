package com.hoangdevelopers.listen_and_write.models;


public class Sentence {
    private Float start;
    private Float duration;
    private String content;
    private boolean ignoreQuest;

    private int index;

    public Sentence(int _index, Float _start, Float _duration, String _content, boolean _ignoreQuest) {
        start = _start;
        duration = _duration;
        ignoreQuest = _ignoreQuest;
        index = _index;
        content = _content.replaceAll("[\\t\\n\\r]+", " ").trim();

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

    public boolean isIgnoreQuest() {
        return ignoreQuest;
    }

    public void setIgnoreQuest(boolean ignoreQuest) {
        this.ignoreQuest = ignoreQuest;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
