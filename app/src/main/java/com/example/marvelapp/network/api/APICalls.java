package com.example.marvelapp.network.api;



import com.example.marvelapp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APICalls {

    @GET("v1/public/characters")
    Call<ResponseModel> getCharacters(@Query("ts") long ts, @Query("apikey") String apiKey, @Query("hash") String md5Hash, @Query("limit") int limit, @Query("offset") long offset);

}
