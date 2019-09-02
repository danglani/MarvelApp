package com.example.marvelapp.model.database;

import android.content.Context;

import com.example.marvelapp.model.CharacterModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CharacterModel.class}, version = 1)
abstract class CharacterRoomDatabase extends RoomDatabase {
    abstract CharacterDao characterDao();

    private static volatile CharacterRoomDatabase INSTANCE;

    static CharacterRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CharacterRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CharacterRoomDatabase.class, "character_database").build();
                }
            }
        }
        return INSTANCE;
    }


}
