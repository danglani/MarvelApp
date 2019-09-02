package com.example.marvelapp.model.database;

import android.app.Application;
import android.os.AsyncTask;

import com.example.marvelapp.model.CharacterModel;

import java.util.List;

public class CharacterDBRepository {

    private final List<CharacterModel> allCharacters;
    private CharacterDao characterDao;
    private List<CharacterModel> favouriteCharacters;


    public CharacterDBRepository(Application application) {
        CharacterRoomDatabase db = CharacterRoomDatabase.getDatabase(application);
        characterDao = db.characterDao();
        favouriteCharacters = characterDao.getAllFavouriteCharacters();
        allCharacters = characterDao.getAllCharacters();
    }

    public List<CharacterModel> getAllFavouriteCharacters(){
    return favouriteCharacters;
    }

    public List<CharacterModel> getAllCharacters(){
        return allCharacters;
    }

    public void insertAll(List<CharacterModel> models) {
        new insertAllAsyncTask(characterDao).execute(models);
    }

    public void updateCharacter(CharacterModel model){
        new insertAsyncTask(characterDao).execute(model);
    }

    private static class insertAllAsyncTask extends AsyncTask<List<CharacterModel>, Void, Void> {

        private CharacterDao mAsyncTaskDao;

        insertAllAsyncTask(CharacterDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<CharacterModel>... params) {
            mAsyncTaskDao.insertAllCharacters(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<CharacterModel, Void, Void> {

        private CharacterDao mAsyncTaskDao;

        insertAsyncTask(CharacterDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CharacterModel... params) {
            mAsyncTaskDao.updateCharacter(params[0]);
            return null;
        }
    }
}
