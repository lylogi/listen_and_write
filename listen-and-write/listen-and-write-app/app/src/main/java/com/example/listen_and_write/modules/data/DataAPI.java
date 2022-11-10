package com.example.listen_and_write.modules.data;

import com.example.listen_and_write.models.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataAPI {
    @GET("hoangdevelopers/{id}/raw/")
    Call<Data> getData(@Path("id") String id);
}
