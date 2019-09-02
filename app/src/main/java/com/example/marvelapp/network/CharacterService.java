package com.example.marvelapp.network;

import com.example.marvelapp.model.ResponseModel;
import com.example.marvelapp.network.api.APICallback;


import androidx.annotation.NonNull;

public interface CharacterService {
    void getCharacters(long offset, @NonNull long ts, @NonNull String publicApiKey, String md5Hash, APICallback<ResponseModel> callback);

}
