package com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text_1_0.timed_text;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "p")
public class TextObject10 {
    @Attribute(name = "begin", required = true)
    private String begin;
    @Attribute(name = "end", required = true)
    private String end;
    @Text(required = true)
    private String content;
    @Attribute(name = "id")
    private String id;
    @Attribute(name = "ignoreQuest", required = false)
    private boolean ignoreQuest = false;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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
}
