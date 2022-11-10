package com.hoangdevelopers.listen_and_write.models;

import java.util.ArrayList;
import java.util.List;

public class Subtitle {
    private String lang;
    private List<Sentence> sentences = new ArrayList<Sentence>();
    private List<Paragraph> paragraphs = new ArrayList<>();

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
