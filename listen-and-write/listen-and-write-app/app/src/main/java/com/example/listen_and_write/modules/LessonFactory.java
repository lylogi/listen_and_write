package com.example.listen_and_write.modules;

import com.example.listen_and_write.framework.Lesson;
import com.example.listen_and_write.framework.TimelineControl;
import com.example.listen_and_write.modules.players.YoutubeControl;
import com.example.listen_and_write.modules.scripts.BlankModeScript;

public class LessonFactory {
    public final static Lesson createYouTubeFullModeLesson() {
        return new Lesson.Builder()
                .setPlayer(new YoutubeControl())
                .setScript(new BlankModeScript())
                .setTimelineControl((new TimelineControl()))
                .create();
    }
}
