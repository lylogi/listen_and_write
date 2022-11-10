package com.hoangdevelopers.listen_and_write.framework;

import android.util.Log;

import com.hoangdevelopers.listen_and_write.models.Quiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.halfbit.tinymachine.StateHandler;
import de.halfbit.tinymachine.TinyMachine;

public class TimelineControl {
    public OnReachTarget getOnReachTarget() {
        return onReachTarget;
    }

    public void setOnReachTarget(OnReachTarget onReachTarget) {
        this.onReachTarget = onReachTarget;
    }

    public TimelineConfig getConfig() {
        return config;
    }

    public void setConfig(TimelineConfig config) {
        this.config = config;
    }

    public TimelineConfig getConfigTemp() {
        return configTemp;
    }

    public void setConfigTemp(TimelineConfig configTemp) {
        this.configTemp = configTemp;
    }

    public interface OnReachTarget {
        void run(String id);
    }

    private OnReachTarget onReachTarget;
    private BasePlayer player;

    public TimelineControl() {
        handler = new TimelineHandler(this);
        tinyMachine = new TinyMachine(handler, TimelineHandler.PENDING);
    }

    protected Lesson lesson;

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void preInit() {
        tinyMachine.transitionTo(TimelineHandler.PRE_PENDING);
    }

    public void init() {
    }

    public void run() {

    }

    public BasePlayer getPlayer() {
        return player;
    }

    public void setPlayer(BasePlayer player) {
        this.player = player;
    }

    public void requestPlay(Float _startTime, Float _duration, Quiz _quiz) {
        setConfigTemp(new TimelineConfig(_startTime, _duration, _quiz));
    }

    protected TimelineHandler handler;
    protected TinyMachine tinyMachine;
    private TimelineConfig config;
    private TimelineConfig configTemp;

    public static class TimelineConfig {
        float startTime;
        float duration;
        Quiz quiz;

        public TimelineConfig(float _startTime, float _duration, Quiz _quiz) {
            startTime = _startTime;
            duration = _duration;
            quiz = _quiz;
        }

        public float getEndTime() {
            return startTime + duration;
        }
    }

    public final static class TimelineHandler {
        private static final List<String> states = new ArrayList<String>(Arrays.asList("PRE_PENDING", "PENDING", "SETUP", "SEEK_TO", "PRE_WAIT_TO_SEEK", "WAIT_TO_SEEK", "PLAY", "PRE_CHECK_REACH_ENDTIME", "CHECK_REACH_ENDTIME", "REACH_ENDTIME", "STOP"));
        private static final int PRE_PENDING = 0;
        private static final int PENDING = 1;
        private static final int SETUP = 2;
        private static final int SEEK_TO = 3;
        private static final int PRE_WAIT_TO_SEEK = 4;
        private static final int WAIT_TO_SEEK = 5;
        private static final int PLAY = 6;
        private static final int PRE_CHECK_REACH_ENDTIME = 7;
        private static final int CHECK_REACH_ENDTIME = 8;
        private static final int REACH_ENDTIME = 9;
        private static final int STOP = 10;
        protected TimelineControl timelineControl;
        private boolean adjustStop = false;

        public TimelineHandler(TimelineControl _timelineControl) {
            timelineControl = _timelineControl;
        }

        @StateHandler(state = StateHandler.STATE_ANY, type = StateHandler.Type.OnEntry)
        public void onEntryState(TinyMachine tm) {
            // This method is called when machine enters any new state
            Log.d("TimelineHandler", " - Event = " + states.get(tm.getCurrentState()));
        }

        @StateHandler(state = PRE_PENDING, type = StateHandler.Type.OnEntry)
        public void onPrePending(TinyMachine tm) {
            transitionTo(PENDING);
        }

        @StateHandler(state = PENDING, type = StateHandler.Type.OnEntry)
        public void onPending(TinyMachine tm) {
            new Thread(() -> {
                try {
                    Thread.sleep(Long.valueOf((long) (1 * 1000)));
                    if (timelineControl.getConfigTemp() != null && timelineControl.getConfigTemp().getEndTime() > 0) {
                        timelineControl.lesson.getPlayer().pause();
                        transitionTo(SETUP);
                    } else {
                        transitionTo(PRE_PENDING);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }).start();
        }

        @StateHandler(state = SETUP, type = StateHandler.Type.OnEntry)
        public void onSetUp(TinyMachine tm) {
            timelineControl.setConfig(new TimelineControl.TimelineConfig(
                    timelineControl.getConfigTemp().startTime,
                    timelineControl.getConfigTemp().duration,
                    timelineControl.getConfigTemp().quiz
            ));
            timelineControl.lesson.getPlayer().pause();
            transitionTo(SEEK_TO);
        }

        @StateHandler(state = SEEK_TO, type = StateHandler.Type.OnEntry)
        public void onSeekTo(TinyMachine tm) {
            timelineControl.lesson.getPlayer().seek(timelineControl.config.startTime);
            transitionTo(PRE_WAIT_TO_SEEK);
        }

        @StateHandler(state = PRE_WAIT_TO_SEEK, type = StateHandler.Type.OnEntry)
        public void onPreWaitToSeek(TinyMachine tm) {
            transitionTo(WAIT_TO_SEEK);
        }

        @StateHandler(state = WAIT_TO_SEEK, type = StateHandler.Type.OnEntry)
        public void onWaitToSeek(TinyMachine tm) {
            new Thread(() -> {
                try {
                    timelineControl.lesson.getPlayer().setVolume(0);
                    timelineControl.lesson.getPlayer().play();
                    Thread.sleep(Long.valueOf((long) (0.5 * 1000)));
                    timelineControl.lesson.getPlayer().pause();
                    if (timelineControl.config.startTime * 1000 <= timelineControl.lesson.getPlayer().getCurrentTime()) {
                        timelineControl.lesson.getPlayer().setVolume(100);
                        timelineControl.lesson.getPlayer().seek(timelineControl.config.startTime);
                        transitionTo(PLAY);
                    } else {
                        transitionTo(PRE_WAIT_TO_SEEK);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }).start();
        }

        @StateHandler(state = PLAY, type = StateHandler.Type.OnEntry)
        public void onPlay(TinyMachine tm) {
            timelineControl.lesson.getPlayer().play();
            transitionTo(CHECK_REACH_ENDTIME);
        }

        @StateHandler(state = PRE_CHECK_REACH_ENDTIME, type = StateHandler.Type.OnEntry)
        public void onPreCheckReachEndtime(TinyMachine tm) {
            transitionTo(CHECK_REACH_ENDTIME);
        }

        @StateHandler(state = CHECK_REACH_ENDTIME, type = StateHandler.Type.OnEntry)
        public void onCheckReachEndtime(TinyMachine tm) {
            new Thread(() -> {
                try {
                    Thread.sleep(Long.valueOf((long) (0.01 * 1000)));
                    if (timelineControl.config.getEndTime() * 1000 <= timelineControl.lesson.getPlayer().getCurrentTime()) {
                        transitionTo(REACH_ENDTIME);
                    } else {
                        transitionTo(PRE_CHECK_REACH_ENDTIME);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }).start();
        }

        @StateHandler(state = REACH_ENDTIME, type = StateHandler.Type.OnEntry)
        public void onReachEndtime(TinyMachine tm) {
            timelineControl.lesson.getPlayer().pause();
            timelineControl.onReachTarget.run(timelineControl.config.quiz.getId());
            transitionTo(PRE_PENDING);
        }

        void transitionTo(int state) {
            if (isAdjustStop()) {
                timelineControl.tinyMachine.transitionTo(STOP);
            } else {
                timelineControl.tinyMachine.transitionTo(state);
            }
        }

        public boolean isAdjustStop() {
            return adjustStop;
        }

        public void setAdjustStop(boolean _adjustStop) {
            this.adjustStop = _adjustStop;
        }
    }

    public void stop() {
        handler.setAdjustStop(true);
        tinyMachine.transitionTo(TimelineHandler.STOP);
    }
}
