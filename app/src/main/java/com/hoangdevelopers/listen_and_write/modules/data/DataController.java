package com.hoangdevelopers.listen_and_write.modules.data;

import com.hoangdevelopers.listen_and_write.models.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;

public class DataController implements Callback<Data> {
    public interface DataListener {
        void onSuccess(Response<Data> response);

        void onFailure(Error error);
    }

    DataListener dataListener;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://gist.githubusercontent.com").addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    public DataController(final DataListener listener) {
        dataListener = listener;
    }

    public void run(String dataId) {


        DataAPI dataAPI = retrofit.create(DataAPI.class);

        Call<Data> call = dataAPI.getData(dataId);
        call.enqueue(this);
    }

    public void onResponse(Call<Data> call, Response<Data> response) {

        if (response.isSuccessful()) {

            dataListener.onSuccess(response);

        } else {
            System.out.println(response.errorBody());
            dataListener.onFailure(new Error("Error"));
        }
    }

    public void onFailure(Call<Data> call, Throwable t) {

        t.printStackTrace();
        dataListener.onFailure(new Error("Error"));
    }

}
