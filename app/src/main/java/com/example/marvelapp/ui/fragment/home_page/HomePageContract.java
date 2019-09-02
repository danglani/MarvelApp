package com.example.marvelapp.ui.fragment.home_page;

import com.example.marvelapp.model.CharacterModel;

import java.util.List;

public class HomePageContract {


    public interface View {
        void showCharacters(List<CharacterModel> characterList);
        void hideProgressBar();
        void showProgressBar();
        void showError(String error);
        void decrementPage();
    }

    public interface Presenter{
        void downloadCharacters();


    }
}
