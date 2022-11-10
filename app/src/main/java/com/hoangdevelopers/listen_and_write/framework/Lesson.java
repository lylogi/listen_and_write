package com.hoangdevelopers.listen_and_write.framework;

public class Lesson {
    public final static class Builder {
        private Lesson instance = new Lesson();

        public Lesson create() {
            return instance;
        }

        public Builder setPlayer(BasePlayer player) {
            instance.setPlayer(player);
            return this;
        }

        public Builder setScript(BaseScript baseScript) {
            instance.setScript(baseScript);
            return this;
        }

        public Builder setTimelineControl(TimelineControl timelineControl) {
            instance.setTimelineControl(timelineControl);
            return this;
        }
    }

    public BasePlayer getPlayer() {
        return player;
    }

    public TimelineControl getTimelineControl() {
        return timelineControl;
    }

    public BaseScript getScript() {
        return script;
    }

    protected BasePlayer player;
    protected BaseScript script;
    protected TimelineControl timelineControl;

    protected void setPlayer(BasePlayer _player) {
        if (player != null) {
            throw new RuntimeException("player already assign");
        }
        player = _player;
        player.setLesson(this);
    }

    protected void setScript(BaseScript baseScript) {
        if (script != null) {
            throw new RuntimeException("script already assign");
        }
        script = baseScript;
        script.setLesson(this);

    }

    protected void setTimelineControl(TimelineControl _timelineControl) {
        if (timelineControl != null) {
            throw new RuntimeException("timelineControl already assign");
        }
        timelineControl = _timelineControl;
        timelineControl.setLesson(this);
    }

    public void init() {
        script.preInit();
        player.preInit();
        timelineControl.preInit();
        script.init();
        player.init();
        timelineControl.init();
    }

    public void stop() {
        script.stop();
        player.pause();
        timelineControl.stop();
    }

    public void destroy() {
        stop();
    }
}
