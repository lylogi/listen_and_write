package com.example.listen_and_write.models;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "root")
public class Data {
    @ElementList(name = "videoList")
    private  List<VideoMeta> videoMetas;

    public List<VideoMeta> getVideoMetas() {
        return videoMetas;
    }

    public void setVideoMetas(List<VideoMeta> videoMetas) {
        this.videoMetas = videoMetas;
    }
}
