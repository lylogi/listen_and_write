package com.example.listen_and_write.models;


public class Sentence {
    private Float start;
    private Float duration;
    private String content;

    public Sentence(Float _start, Float _duration, String _content) {
        start = _start;
        duration = _duration;
        content = _content.replaceAll("&quot;", "").replaceAll("[^a-zA-Z0-9\\s\\']", "").replaceAll("[\\t\\n\\r]+"," ");;

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
}
