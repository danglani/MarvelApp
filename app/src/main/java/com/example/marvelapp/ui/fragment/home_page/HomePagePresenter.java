package com.example.marvelapp.ui.fragment.home_page;


import com.example.marvelapp.model.ResponseModel;
import com.example.marvelapp.network.CharacterRepository;
import com.example.marvelapp.network.api.APICallback;
import com.example.marvelapp.utils.Constants;
import com.example.marvelapp.utils.Injection;
import com.example.marvelapp.utils.EncryptPreferencesMgmt;

public class HomePagePresenter implements HomePageContract.Presenter {

    private CharacterRepository characterRepository;
    private CharacterListFragment view;

    public HomePagePresenter(CharacterListFragment view){
        this.view = view;
        this.characterRepository = Injection.provideCharacterRepository();
    }

    @Override
    public void downloadCharacters() {
        view.showProgressBar();
        long ts = EncryptPreferencesMgmt.getInstance().getTimeStamp();
        long offset = ts * Constants.RESULTS_LIMIT;
        String md5Hash = EncryptPreferencesMgmt.getInstance().getMd5Hash(Constants.PUBLIC_API_KEY, Constants.PRIVATE_KEY);
        characterRepository.getCharacterList(offset, ts, Constants.PUBLIC_API_KEY, md5Hash, new APICallback<ResponseModel>() {
            @Override
            public void onSuccess(ResponseModel object) {
                view.hideProgressBar();
                view.showCharacters(object.getData().getResults(), object.getData().getTotal());
                long timestamp = ts;
                timestamp += 1;
                EncryptPreferencesMgmt.getInstance().setTimeStamp(timestamp);
            }


            @Override
            public void onFailure(String error) {
                view.hideProgressBar();
                view.showError(error);
            }
        });

    }

}