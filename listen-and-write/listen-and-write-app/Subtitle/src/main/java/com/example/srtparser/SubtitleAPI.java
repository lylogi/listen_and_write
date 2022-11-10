package com.example.srtparser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SubtitleAPI {
    @GET("api/timedtext")
    Call<TranscriptObject> getTimedText(@Query("v") String videoId, @Query("lang") String language);
}
