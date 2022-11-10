package com.hoangdevelopers.listen_and_write.models;

public class VideoData extends VideoMeta {
    private ChannelMeta channelMeta;

    public ChannelMeta getChannelMeta() {
        return channelMeta;
    }

    public void setChannelMeta(ChannelMeta channelMeta) {
        this.channelMeta = channelMeta;
    }
}
