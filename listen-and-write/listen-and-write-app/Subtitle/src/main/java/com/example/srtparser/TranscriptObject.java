package com.example.srtparser;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "transcript")
public class TranscriptObject {
    @ElementList(inline = true)
    private  List<TextObject> texts;

    public List<TextObject> getTexts() {
        return texts;
    }

    public void setTexts(List<TextObject> texts) {
        this.texts = texts;
    }
}
