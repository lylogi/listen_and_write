package com.hoangdevelopers.listen_and_write.modules.subtitle;

import com.hoangdevelopers.listen_and_write.models.Subtitle;

public abstract class BaseSubtitleController {
    public ResultListener getResultListener() {
        return resultListener;
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public interface ResultListener {
        void onSuccess(Subtitle response);

        void onFailure(Error error);
    }

    private ResultListener resultListener;

    public abstract void run(String dataId);
}
