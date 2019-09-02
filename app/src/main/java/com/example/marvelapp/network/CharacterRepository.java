package com.example.marvelapp.network;

import com.example.marvelapp.model.ResponseModel;
import com.example.marvelapp.network.api.APICallback;

import java.util.List;

import androidx.annotation.NonNull;

public class CharacterRepository {
    private static CharacterRepository instance;
    private final CharacterService service;

    private CharacterRepository(@NonNull CharacterService service) {
        this.service = service;
    }

    public synchronized static CharacterRepository getInstance(@NonNull CharacterService service) {
        if (instance == null) {
            instance = new CharacterRepository(service);
        }
        return instance;
    }


    public void getCharacterList(long offset, long ts, String publicApiKey, String md5Hash, APICallback<ResponseModel> responseModelAPICallback) {
        service.getCharacters(offset, ts, publicApiKey, md5Hash, new APICallback<ResponseModel>() {
            @Override
            public void onSuccess(ResponseModel response) {
                responseModelAPICallback.onSuccess(response);
            }


            @Override
            public void onFailure(String error) {
                responseModelAPICallback.onFailure(error);
            }
        });
    }
}
