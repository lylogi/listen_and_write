package com.hoangdevelopers.listen_and_write.framework;

import com.hoangdevelopers.listen_and_write.models.Paragraph;
import com.hoangdevelopers.listen_and_write.models.Subtitle;

public abstract class BasePlayer {
    public abstract void init();

    public void preInit() {
    }

    private Lesson lesson;
    private String subType;
    protected boolean ready;
    protected Subtitle subtitle;
    protected OnPlayerReady onPlayerReady;
    protected int paragraphIndex = 0;

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setOnPlayerReady(OnPlayerReady _onPlayerReady) {

        onPlayerReady = _onPlayerReady;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public interface OnPlayerReady {
        void run();
    }

    public abstract Boolean checkReady();

    protected void shouldCheckReady() {
        if (!ready && checkReady()) {
            ready = true;
            onPlayerReady.run();
        }
    }

    public boolean checkHaveParagraph() {
        return subtitle.getParagraphs().size() > paragraphIndex && subtitle.getParagraphs().get(paragraphIndex) != null;
    }

    public Paragraph getCurrentParagraph() {
        return subtitle.getParagraphs().get(paragraphIndex);
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public abstract void seek(Float time);

    public abstract void play();

    public abstract void pause();

    public abstract void setVolume(int t);

    public void nextParagraph() {
        paragraphIndex++;
    }

    public abstract Long getCurrentTime();

    public abstract boolean isPlaying();

    public void reset() {
        paragraphIndex = 0;
    }
}
