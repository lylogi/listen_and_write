package com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text_1_0.timed_text;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "body")
public class TimedText10 {
    @ElementList(name = "div")
    private List<TextObject10> texts;

    public List<TextObject10> getTexts() {
        return texts;
    }

    public void setTexts(List<TextObject10> texts) {
        this.texts = texts;
    }
}
