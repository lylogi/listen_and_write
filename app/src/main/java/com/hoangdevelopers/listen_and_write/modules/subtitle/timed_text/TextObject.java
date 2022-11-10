package com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "text")
public class TextObject {
    @Attribute(name = "start", required = true)
    private Float start;
    @Attribute(name = "dur", required = true)
    private Float duration;
    @Text(required = true)
    private String content;
    @Attribute(name = "ignoreQuest", required = false)
    private boolean ignoreQuest = false;

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
}
