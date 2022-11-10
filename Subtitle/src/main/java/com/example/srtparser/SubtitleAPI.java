package com.example.srtparser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SubtitleAPI {
    @GET
    Call<TranscriptObject> getTimedText(@Url String url);
}
