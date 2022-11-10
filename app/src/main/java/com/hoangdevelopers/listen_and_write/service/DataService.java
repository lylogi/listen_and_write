package com.hoangdevelopers.listen_and_write.service;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.models.ChannelMeta;
import com.hoangdevelopers.listen_and_write.models.Data;
import com.hoangdevelopers.listen_and_write.models.VideoData;
import com.hoangdevelopers.listen_and_write.modules.data.DataController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

public class DataService {
    public interface OnDataChange {
        void run(Data data);
    }

    public interface OnVideoDataChange {
        void run(List<VideoData> data);
    }

    private Map<String, OnDataChange> onDataChanges = new HashMap<>();
    private Map<String, OnVideoDataChange> onVideoDataChanges = new HashMap<>();

    public OnVideoDataChange addVideoDataListener(String key, OnVideoDataChange listener) {
        onVideoDataChanges.put(key, listener);
        if (data != null) {
            listener.run(videoDatas);
        }
        return listener;
    }

    public OnDataChange addDataChangeListener(String key, OnDataChange listener) {
        onDataChanges.put(key, listener);
        if (data != null) {
            listener.run(data);
        }
        return listener;
    }

    public Data data;
    public List<VideoData> videoDatas = new ArrayList<>();
    private static DataService instance;

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public DataService() {
        init();
    }

    private void init() {
        loadData();
    }

    private void loadData() {
        new Thread(() -> {
            DataController dataController = new DataController(new DataController.DataListener() {
                @Override
                public void onSuccess(Response<Data> response) {
                    dataChange(response.body());
                }

                @Override
                public void onFailure(Error error) {

                }
            });
            dataController.run("e83945d72cd8a330df78e3c612fcd3b4");
        }).start();
    }

    private void dataChange(Data _data) {
        data = _data;
        //
        Map<String, ChannelMeta> channelMetaMap = Stream.of(data.getChannels()).collect(Collectors.toMap(ChannelMeta::getKey, x -> x));
        videoDatas = Stream.of(data.getVideoMetas())
                .map(videoMeta -> VideoDataMapping.videoMetaToVideoData(
                        videoMeta,
                        channelMetaMap.get(videoMeta.getChanelId()))
                )
                .collect(Collectors.toList());
        for (OnDataChange onDataChange : onDataChanges.values()) {
            onDataChange.run(data);
        }
        for (OnVideoDataChange onVideoMetasChange : onVideoDataChanges.values()) {
            onVideoMetasChange.run(videoDatas);
        }
    }
}
