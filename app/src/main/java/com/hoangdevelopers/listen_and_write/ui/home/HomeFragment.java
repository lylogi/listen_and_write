package com.hoangdevelopers.listen_and_write.ui.home;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.hoangdevelopers.listen_and_write.R;
import com.hoangdevelopers.listen_and_write.adapter.VideosAdapter;
import com.hoangdevelopers.listen_and_write.models.VideoData;
import com.hoangdevelopers.listen_and_write.service.DataService;
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;

import java.util.List;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DataService.OnVideoDataChange onVideoDataChange;

    DataService dataService = DataService.getInstance();
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        setHasOptionsMenu(true);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        ///////////////////
        RecyclerView videoListView = root.findViewById(R.id.rv_video_list);

        Context context = container.getContext();
        onVideoDataChange = dataService.addVideoDataListener("HomeFragment", new DataService.OnVideoDataChange() {
            @Override
            public void run(List<VideoData> videoDatas) {
                videoDatas = Stream.of(videoDatas).sorted((v1, v2) -> v2.getId().compareTo(v1.getId())).collect(Collectors.toList());
                VideosAdapter adapter = new VideosAdapter(videoDatas);
                // Attach the adapter to the recyclerview to populate items
                videoListView.setAdapter(adapter);
                // Set layout manager to position the items
                videoListView.setLayoutManager(new LinearLayoutManager(context));

            }
        });
        return root;
    }

    @Override
    public void onDestroy() {
        onVideoDataChange = null;
        super.onDestroy();
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
