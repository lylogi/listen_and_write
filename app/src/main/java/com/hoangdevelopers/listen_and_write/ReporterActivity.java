package com.hoangdevelopers.listen_and_write;

import android.os.Bundle;

import com.heinrichreimersoftware.androidissuereporter.IssueReporterActivity;
import com.heinrichreimersoftware.androidissuereporter.model.github.ExtraInfo;
import com.heinrichreimersoftware.androidissuereporter.model.github.GithubTarget;

public class ReporterActivity extends IssueReporterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMinimumDescriptionLength(10);
        setGuestToken("1fb0e86b25ee7ab1ef74a427bbe9d8a1aac2c3cc");
    }

    @Override
    public GithubTarget getTarget() {
        return new GithubTarget("hoangdevelopers", "listen-and-write-reporter");
    }

    @Override
    public void onSaveExtraInfo(ExtraInfo extraInfo) {
        extraInfo.put("Test 1", "Example string");
        extraInfo.put("Test 2", true);
    }
}
