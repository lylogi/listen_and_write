package com.example.srtparser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;

public class SubtitleController implements Callback<TranscriptObject> {
    public interface SubtitleListener {
        void onSuccess(Response<TranscriptObject> response);
        void onFailure(Error error);
    }
    SubtitleListener subtitleListener;
    public SubtitleController(final SubtitleListener listener) {
        subtitleListener = listener;
    }
    public void run(String videoId, String lang) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.youtube.com").addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        SubtitleAPI subtitleAPI = retrofit.create(SubtitleAPI.class);

        Call<TranscriptObject> call = subtitleAPI.getTimedText(videoId, lang);
        call.enqueue(this);
    }

    public void onResponse(Call<TranscriptObject> call, Response<TranscriptObject> response) {

        if (response.isSuccessful()) {

//            TranscriptObject feed = response.body();
//            for (TextObject textObject: feed.getTexts()) {
//                System.out.println("Channel textObject: " + textObject.getContent());
//                System.out.println("----------------------------------------------\n");
//            }
        subtitleListener.onSuccess(response);

        } else {
            System.out.println(response.errorBody());
            subtitleListener.onFailure(new Error("Error"));
        }
    }

    public void onFailure(Call<TranscriptObject> call, Throwable t) {

        t.printStackTrace();
        subtitleListener.onFailure(new Error("Error"));
    }

}
