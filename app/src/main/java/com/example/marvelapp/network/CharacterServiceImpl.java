package com.example.marvelapp.network;


import com.example.marvelapp.model.ResponseModel;
import com.example.marvelapp.network.api.APICallback;
import com.example.marvelapp.network.api.ApiClient;
import com.example.marvelapp.utils.Constants;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CharacterServiceImpl implements CharacterService {

    @Override
    public void getCharacters(long offset, @NonNull long ts, @NonNull String publicApiKey, @NonNull String md5Hash, APICallback<ResponseModel> callback) {
        Call<ResponseModel> charactersCall = ApiClient.getApiClient().getCharacters(ts, Constants.PUBLIC_API_KEY, md5Hash, Constants.RESULTS_LIMIT, offset);
        charactersCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        ResponseModel characterList = response.body();
                        callback.onSuccess(characterList);
                    }
                } else {
                    callback.onFailure(Constants.GENERIC_API_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callback.onFailure(Constants.GENERIC_API_ERROR);
            }
        });
    }
}
