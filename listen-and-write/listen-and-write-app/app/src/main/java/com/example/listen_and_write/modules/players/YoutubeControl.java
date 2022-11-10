package com.example.listen_and_write.modules.players;

import android.util.Log;

import com.example.listen_and_write.framework.BasePlayer;
import com.example.listen_and_write.models.Subtitle;
import com.example.listen_and_write.service.SubtitleMapping;
import com.example.srtparser.SubtitleController;
import com.example.srtparser.TranscriptObject;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;

import retrofit2.Response;

public class YoutubeControl extends BasePlayer {
    protected String videoId;
    public YouTubePlayer getYouTubePlayer() {
        return youTubePlayer;
    }

    public void setYouTubePlayer(YouTubePlayer youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
        tracker = new YouTubePlayerTracker();
        youTubePlayer.addListener(tracker);

    }

    protected YouTubePlayer youTubePlayer;
    protected YouTubePlayerTracker tracker;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public void setVolume(int t) {
        youTubePlayer.setVolume(t);
    }
    @Override
    public void init() {
        youTubePlayer.loadVideo(videoId, 0);
        youTubePlayer.pause();
        SubtitleController subtitleController = new SubtitleController(new SubtitleController.SubtitleListener() {
            @Override
            public void onSuccess(Response<TranscriptObject> response) {
                subtitle = SubtitleMapping.transcriptObjectToSubtitle(response.body());
                shouldCheckReady();
            }

            @Override
            public void onFailure(Error error) {

            }
        });
        subtitleController.run(getVideoId(), "en");
    }
    @Override
    public Boolean checkReady() {
        return subtitle != null;
    }

    @Override
    public void seek(Float time) {
        youTubePlayer.seekTo(time);

    }

    @Override
    public void play() {
        youTubePlayer.play();
    }

    @Override
    public void pause() {
        youTubePlayer.pause();
    }

    public void preInit() {
        super.preInit();

    }
    @Override
    public Long getCurrentTime() {
        return new Float(tracker.getCurrentSecond() * 1000).longValue();
    }
    @Override
    public boolean isPlaying() {
        return tracker.getState() == PlayerConstants.PlayerState.PLAYING;
    }
}
