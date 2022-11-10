package com.hoangdevelopers.listen_and_write.ui.FilterVideoOptionFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.R;
import com.hoangdevelopers.listen_and_write.models.ChannelMeta;
import com.hoangdevelopers.listen_and_write.models.Data;
import com.hoangdevelopers.listen_and_write.models.VideoData;
import com.hoangdevelopers.listen_and_write.service.DataService;
import com.hoangdevelopers.listen_and_write.service.FilterService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterVideoOptionFragment extends BottomSheetDialogFragment {
    DataService dataService = DataService.getInstance();
    FilterService filterService = FilterService.getInstance();
    Set<Integer> levelCheckeds = new HashSet<>();
    Set<String> channelCheckeds = new HashSet<>();

    public FilterVideoOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_video_option, container, false);
        ViewGroup levelContainer = view.findViewById(R.id.fragment_filter_video_level_container);
        ViewGroup channelContainer = view.findViewById(R.id.fragment_filter_video_channel_container);
        levelCheckeds = filterService.getLevelFilters();
        channelCheckeds = filterService.getChannelFilters();
        dataService.addVideoDataListener("FilterVideoOptionFragment", new DataService.OnVideoDataChange() {
            @Override
            public void run(List<VideoData> videoDatas) {
                levelContainer.removeAllViews();
                Set<Integer> levels = Stream.of(videoDatas).map(VideoData::getLevel).collect(Collectors.toSet());
                for (Integer level : levels) {
                    ViewGroup levelView = (ViewGroup) inflater.inflate(R.layout.chip_item, container, false);
                    Chip chipview = (Chip) levelView.getChildAt(0);
                    chipview.setText(level.toString());
                    chipview.setChecked(Stream.of(levelCheckeds).filter(x -> x.equals(level)).findFirst().isPresent());
                    chipview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                levelCheckeds.add(level);
                            } else {
                                levelCheckeds.remove(level);
                            }
                            filterService.setLevelFilters(levelCheckeds);
                        }
                    });
                    levelContainer.addView(levelView);
                }
            }
        });
        dataService.addDataChangeListener("FilterVideoOptionFragment", new DataService.OnDataChange() {
            @Override
            public void run(Data data) {
                for (ChannelMeta channelMeta: data.getChannels()) {
                    ViewGroup levelView = (ViewGroup) inflater.inflate(R.layout.chip_item, container, false);
                    Chip chipview = (Chip) levelView.getChildAt(0);
                    chipview.setText(channelMeta.getName());
                    chipview.setChecked(Stream.of(channelCheckeds).filter(x -> x.equals(channelMeta.getName())).findFirst().isPresent());
                    chipview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                channelCheckeds.add(channelMeta.getName());
                            } else {
                                channelCheckeds.remove(channelMeta.getName());
                            }
                            filterService.setChannelFilters(channelCheckeds);
                        }
                    });
                    channelContainer.addView(levelView);
                }
            }
        });
        return view;
    }
}
