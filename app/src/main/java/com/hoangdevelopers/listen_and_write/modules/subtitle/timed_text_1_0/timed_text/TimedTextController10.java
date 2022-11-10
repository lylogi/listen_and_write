package com.hoangdevelopers.listen_and_write.modules.subtitle.timed_text_1_0.timed_text;

import com.hoangdevelopers.listen_and_write.modules.subtitle.BaseSubtitleController;
import com.hoangdevelopers.listen_and_write.service.SubtitleMapping;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class TimedTextController10 extends BaseSubtitleController implements Callback<TimedText10> {

    public interface API {
        @GET("hoangdevelopers/{id}/raw/")
        Call<TimedText10> getData(@Path("id") String id);

    }

    Retrofit retrofit = new Retrofit
            .Builder()
            .baseUrl("https://gist.githubusercontent.com")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    @Override
    public void run(String dataId) {
        API api = retrofit.create(API.class);
        Call<TimedText10> call = api.getData(dataId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<TimedText10> call, Response<TimedText10> response) {
        if (response.isSuccessful()) {
            getResultListener().onSuccess(SubtitleMapping.timedText10ToSubtitle(response.body()));
        } else {
            System.out.println(response.errorBody());
            getResultListener().onFailure(new Error("Error"));
        }
    }

    @Override
    public void onFailure(Call<TimedText10> call, Throwable t) {
        t.printStackTrace();
        getResultListener().onFailure(new Error("Error"));
    }

}
