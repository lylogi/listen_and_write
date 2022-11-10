package com.hoangdevelopers.listen_and_write.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "root")
public class Data {
    @ElementList(name = "videoList")
    private List<VideoMeta> videoMetas;

    @ElementList(name = "channelList")
    private List<ChannelMeta> channels;

    public List<VideoMeta> getVideoMetas() {
        return videoMetas;
    }

    public void setVideoMetas(List<VideoMeta> videoMetas) {
        this.videoMetas = videoMetas;
    }

    public List<ChannelMeta> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelMeta> channels) {
        this.channels = channels;
    }
}
