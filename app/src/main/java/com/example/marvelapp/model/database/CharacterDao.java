package com.example.marvelapp.model.database;

import com.example.marvelapp.model.CharacterModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateCharacter(CharacterModel characterModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCharacters(List<CharacterModel> characterModels);

    @Delete
    void deleteCharacter(CharacterModel characterModel);

    @Query("SELECT * from character_table WHERE isFavourite = 1 ORDER BY name ASC")
    List<CharacterModel> getAllFavouriteCharacters();

    @Query("SELECT * from character_table ORDER BY name ASC")
    List<CharacterModel> getAllCharacters();

}


