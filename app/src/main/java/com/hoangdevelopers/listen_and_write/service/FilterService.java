package com.hoangdevelopers.listen_and_write.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FilterService {
    public interface OnLevelFilterChange {
        void run(Set<Integer> data);
    }

    public interface OnChannelFilterChange {
        void run(Set<String> data);
    }

    private Map<String, OnLevelFilterChange> onLevelFilterChanges = new HashMap<>();
    private Map<String, OnChannelFilterChange> onChannelFilterChanges = new HashMap<>();

    public OnChannelFilterChange addChannelFilterListener(String key, OnChannelFilterChange listener) {
        onChannelFilterChanges.put(key, listener);
        if (channelFilters != null) {
            listener.run(channelFilters);
        }
        return listener;
    }

    public OnLevelFilterChange addLevelFilterListener(String key, OnLevelFilterChange listener) {
        onLevelFilterChanges.put(key, listener);
        if (levelFilters != null) {
            listener.run(levelFilters);
        }
        return listener;
    }

    public void setLevelFilters(Set<Integer> levelFilters) {
        this.levelFilters = levelFilters;
        for(OnLevelFilterChange onLevelFilterChange: onLevelFilterChanges.values()) {
            onLevelFilterChange.run(levelFilters);
        }
    }

    public void setChannelFilters(Set<String> channelFilters) {
        this.channelFilters = channelFilters;
        for(OnChannelFilterChange onChannelFilterChange: onChannelFilterChanges.values()) {
            onChannelFilterChange.run(channelFilters);
        }
    }

    public Set<Integer> getLevelFilters() {
        return levelFilters;
    }

    public Set<String> getChannelFilters() {
        return channelFilters;
    }

    private Set<Integer> levelFilters = new HashSet<>();
    private Set<String> channelFilters = new HashSet<>();
    private static FilterService instance;

    public static FilterService getInstance() {
        if (instance == null) {
            instance = new FilterService();
        }
        return instance;
    }

}
