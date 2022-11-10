package com.hoangdevelopers.listen_and_write.service;

import com.hoangdevelopers.listen_and_write.models.ChannelMeta;
import com.hoangdevelopers.listen_and_write.models.VideoData;
import com.hoangdevelopers.listen_and_write.models.VideoMeta;

public class VideoDataMapping {
    public final static VideoData videoMetaToVideoData(VideoMeta videoMeta, ChannelMeta channelMeta) {
        VideoData videoData = new VideoData();
        videoData.setChannelMeta(channelMeta);
        videoData.setChanelId(videoMeta.getChanelId());
        videoData.setDesc(videoMeta.getDesc());
        videoData.setDuration(videoMeta.getDuration());
        videoData.setId(videoMeta.getId());
        videoData.setLevel(videoMeta.getLevel());
        videoData.setSubType(videoMeta.getSubType());
        videoData.setSubUrl(videoMeta.getSubUrl());
        videoData.setTags(videoMeta.getTags());
        videoData.setThumbnail(videoMeta.getThumbnail());
        videoData.setTitle(videoMeta.getTitle());
        videoData.setVideoUrl(videoMeta.getVideoUrl());

        return videoData;
    }
}
