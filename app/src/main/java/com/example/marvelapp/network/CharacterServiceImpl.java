package com.example.marvelapp.network;


import com.example.marvelapp.model.ResponseModel;
import com.example.marvelapp.network.api.APICallback;
import com.example.marvelapp.network.api.ApiClient;
import com.example.marvelapp.utils.Constants;

import androidx.annotation.NonNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CharacterServiceImpl implements CharacterService {

    @Override
    public void getCharacters(long offset, @NonNull long ts, @NonNull String publicApiKey, @NonNull String md5Hash, APICallback<ResponseModel> callback) {
        Call<ResponseModel> charactersCall = ApiClient.getApiClient().getCharacters(ts, Constants.PUBLIC_API_KEY, md5Hash, Constants.RESULTS_LIMIT, offset);
        charactersCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callback.onSuccess(response.body());
                    } else {
                        try {
                            if (response.errorBody() != null)
                                callback.onFailure(response.errorBody().string());
                            else
                                callback.onFailure(Constants.GENERIC_API_ERROR);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    callback.onFailure(Constants.GENERIC_API_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }
}
