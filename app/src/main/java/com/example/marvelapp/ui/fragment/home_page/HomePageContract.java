package com.example.marvelapp.ui.fragment.home_page;

public class HomePageContract {


    public interface View {
        void hideProgressBar();
        void showProgressBar();
        void showError(String error);
        void setTotalCharacters(int totalCharacters);
    }

    public interface Presenter{
        void downloadCharacters();


    }
}
