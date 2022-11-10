package com.hoangdevelopers.listen_and_write.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "videoMeta")
public class VideoMeta {
    @Element()
    private Float id;
    @Element()
    private String chanelId;
    @Element()
    private int level;
    @Element()
    private String subUrl;
    @Element()
    private String subType;
    @Element()
    private String tags;
    @Element()
    private String videoUrl;
    @Element()
    private String thumbnail;
    @Element()
    private String title;
    @Element()
    private String duration;
    @Element()
    private String desc;

    public Float getId() {
        return id;
    }

    public void setId(Float id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getChanelId() {
        return chanelId;
    }

    public void setChanelId(String chanelId) {
        this.chanelId = chanelId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
