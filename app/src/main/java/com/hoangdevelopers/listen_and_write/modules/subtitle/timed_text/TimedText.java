package com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "transcript")
public class TimedText {
    @ElementList(inline = true)
    private List<TextObject> texts;

    public List<TextObject> getTexts() {
        return texts;
    }

    public void setTexts(List<TextObject> texts) {
        this.texts = texts;
    }
}
