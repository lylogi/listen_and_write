package com.example.listen_and_write.framework;

import com.example.listen_and_write.models.Sentence;
import com.example.listen_and_write.models.Subtitle;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BasePlayer {
    public abstract  void init();
    public void preInit() {
    }
    private Lesson lesson;
    protected boolean ready;
    protected Subtitle subtitle;
    protected OnPlayerReady onPlayerReady;
    protected int sentenceIndex = 0;
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }
    public void setOnPlayerReady(OnPlayerReady _onPlayerReady) {

        onPlayerReady = _onPlayerReady;
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

    public boolean checkHaveSentence() {
        return subtitle.getSentences().get(sentenceIndex) != null;
    }
    public Sentence getCurrentSentence() {
        return subtitle.getSentences().get(sentenceIndex);
    }
    public abstract void seek(Float time);
    public abstract void play();
    public abstract void pause();
    public abstract void setVolume(int t);
    public void nextSentence() {
        sentenceIndex++;
    }
    public abstract Long getCurrentTime();
    public abstract boolean isPlaying();
}
