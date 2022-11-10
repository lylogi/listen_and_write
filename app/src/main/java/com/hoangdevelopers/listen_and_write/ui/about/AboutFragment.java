package com.hoangdevelopers.listen_and_write.ui.about;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hoangdevelopers.listen_and_write.R;
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        setHasOptionsMenu(true);
        aboutViewModel =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");
        View root = new AboutPage(getContext())
                .isRTL(false)
                .setImage(R.mipmap.ic_launcher_foreground)
                .setDescription("Listen and Write for kids and adult who wants to improve the English listening knowledge. It helps to improve your English vocabulary.")
                .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("hoangdevelopers@gmail.com")
                .addWebsite("http://hoangdevelopers.github.io")
                .addFacebook("hoang.nguyenduc25")
                .addTwitter("hoangnd_58")
                .addYoutube("UCZZXv8EtQAoWD9bR3DGpNKw")
//                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("hoangdevelopers")
                .addInstagram("hoang.nguyenduc25")
                .create();

        return root;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.common_menu, menu);
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
