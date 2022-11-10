package com.hoangdevelopers.listen_and_write.modules.players;

import com.hoangdevelopers.listen_and_write.framework.BasePlayer;
import com.hoangdevelopers.listen_and_write.models.Subtitle;
import com.hoangdevelopers.listen_and_write.modules.subtitle.BaseSubtitleController;
import com.hoangdevelopers.listen_and_write.modules.subtitle.SubtitleParseFactory;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;

public class YoutubeControl extends BasePlayer {
    protected String videoId;
    private String subUrl;
    private boolean videoReady;

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
        new Thread(() -> {
            youTubePlayer.loadVideo(videoId, 0);
            youTubePlayer.pause();
            videoReady = true;
            shouldCheckReady();
        }).start();
        BaseSubtitleController subtitleController = SubtitleParseFactory.get(getSubType());
        subtitleController.setResultListener(new BaseSubtitleController.ResultListener() {
            @Override
            public void onSuccess(Subtitle response) {
                subtitle = response;
                shouldCheckReady();
            }

            @Override
            public void onFailure(Error error) {

            }
        });
        subtitleController.run(getSubUrl());
    }

    @Override
    public Boolean checkReady() {
        return videoReady && subtitle != null;
    }

    @Override
    public void seek(Float time) {
        if(youTubePlayer == null) {
            return;
        }
        youTubePlayer.seekTo(time);

    }

    @Override
    public void play() {
        if(youTubePlayer == null) {
            return;
        }
        youTubePlayer.play();
    }

    @Override
    public void pause() {
        if(youTubePlayer == null) {
            return;
        }
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

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }
}
