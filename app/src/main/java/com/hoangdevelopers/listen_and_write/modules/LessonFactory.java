package com.hoangdevelopers.listen_and_write.modules;

import com.hoangdevelopers.listen_and_write.framework.Lesson;
import com.hoangdevelopers.listen_and_write.framework.TimelineControl;
import com.hoangdevelopers.listen_and_write.modules.players.YoutubeControl;
import com.hoangdevelopers.listen_and_write.modules.scripts.BlankModeScript;

public class LessonFactory {
    public final static Lesson createYouTubeFullModeLesson() {
        return new Lesson.Builder()
                .setPlayer(new YoutubeControl())
                .setScript(new BlankModeScript())
                .setTimelineControl((new TimelineControl()))
                .create();
    }
}
