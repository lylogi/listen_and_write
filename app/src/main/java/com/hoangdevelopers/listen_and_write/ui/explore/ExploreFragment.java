package com.hoangdevelopers.listen_and_write.ui.explore;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.R;
import com.hoangdevelopers.listen_and_write.adapter.VideosAdapter;
import com.hoangdevelopers.listen_and_write.models.VideoData;
import com.hoangdevelopers.listen_and_write.service.DataService;
import com.hoangdevelopers.listen_and_write.service.FilterService;
import com.hoangdevelopers.listen_and_write.ui.FilterVideoOptionFragment.FilterVideoOptionFragment;
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExploreFragment extends Fragment {
    Set<Integer> levelFilters = new HashSet<>();
    Set<String> channelFilters = new HashSet<>();
    List<VideoData> videoDataTemp = new ArrayList<>();
    List<VideoData> videoDatas;
    DataService dataService = DataService.getInstance();
    FilterService filterService = FilterService.getInstance();
    Context context;
    @BindView(R.id.rv_video_list)
    RecyclerView videoListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, root);
        listenFilter();
        listenVideoData();
        ///////////////////
        return root;
    }

    private void listenVideoData() {
        dataService.addVideoDataListener("ExploreFragment", new DataService.OnVideoDataChange() {
            @Override
            public void run(List<VideoData> data) {
                videoDataTemp = data;
                onChange();
            }
        });
    }

    private void listenFilter() {
        filterService.addLevelFilterListener("ExploreFragment", new FilterService.OnLevelFilterChange() {
            @Override
            public void run(Set<Integer> _levelFilters) {
                levelFilters = _levelFilters;
                onChange();
            }
        });

        filterService.addChannelFilterListener("ExploreFragment", new FilterService.OnChannelFilterChange() {
            @Override
            public void run(Set<String> _channelFilters) {
                channelFilters = _channelFilters;
                onChange();
            }
        });
    }
    private void onChange() {
        videoDatas = Stream.of(videoDataTemp).filter(x -> {
            boolean isLevelFilterIncludeLevel = Stream.of(levelFilters).filter(l -> l.equals(x.getLevel())).findFirst().isPresent();
            boolean isChannelFilterIncludeChannel = Stream.of(channelFilters).filter(l -> l.equals(x.getChannelMeta().getName())).findFirst().isPresent();
            return (levelFilters.size() > 0 && isLevelFilterIncludeLevel || levelFilters.size() == 0) && (channelFilters.size() > 0 && isChannelFilterIncludeChannel || channelFilters.size() == 0);
        }).collect(Collectors.toList());

        VideosAdapter adapter = new VideosAdapter(videoDatas);
        // Attach the adapter to the recyclerview to populate items
        videoListView.swapAdapter(adapter, true);
        // Set layout manager to position the items
        videoListView.setLayoutManager(new LinearLayoutManager(context));
    }

    @OnClick(R.id.test)
    public void showBottomSheetDialogFragment(View view) {
        FilterVideoOptionFragment bottomSheetFragment = new FilterVideoOptionFragment();
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setMaxWidth(Integer.MAX_VALUE);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_feedback) {
            IssueReporterLauncher.forTarget("hoangdevelopers", "listen-and-write-reporter")
                    .guestToken("1fb0e86b25ee7ab1ef74a427bbe9d8a1aac2c3cc")
                    .homeAsUpEnabled(true)
                    .theme(R.style.Theme_App_Light)
                    .launch(context);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
