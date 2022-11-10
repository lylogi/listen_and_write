package com.hoangdevelopers.listen_and_write.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "channelMeta")
public class ChannelMeta {
    @Element()
    private String key;
    @Element()
    private String name;
    @Element()
    private String url;
    @Element()
    private String avatar;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
