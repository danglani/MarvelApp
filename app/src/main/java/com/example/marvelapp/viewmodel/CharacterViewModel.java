package com.example.marvelapp.viewmodel;

import android.app.Application;

import com.example.marvelapp.model.CharacterModel;
import com.example.marvelapp.model.database.CharacterDBRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class CharacterViewModel extends AndroidViewModel {

    private CharacterDBRepository characterDBRepository;
    private LiveData<List<CharacterModel>> characterList;


    public CharacterViewModel(@NonNull Application application) {
        super(application);
        characterDBRepository = new CharacterDBRepository(application);
        characterList = characterDBRepository.getAllCharacters();
    }

    public LiveData<List<CharacterModel>> getAllCharacters(){
        return characterList;
    }

    public void updateCharacter(CharacterModel model){
        characterDBRepository.updateCharacter(model);
    }

    public void insertAllCharacters(List<CharacterModel> list){
        characterDBRepository.insertAll(list);
    }

}
