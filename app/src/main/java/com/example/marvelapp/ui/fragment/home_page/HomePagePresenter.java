package com.example.marvelapp.ui.fragment.home_page;


import com.example.marvelapp.model.CharacterModel;
import com.example.marvelapp.model.ResponseModel;
import com.example.marvelapp.model.database.CharacterDBRepository;
import com.example.marvelapp.network.CharacterRepository;
import com.example.marvelapp.network.api.APICallback;
import com.example.marvelapp.utils.Constants;
import com.example.marvelapp.utils.EncryptPreferencesMgmt;
import com.example.marvelapp.utils.Injection;

import java.util.List;

public class HomePagePresenter implements HomePageContract.Presenter {

    private final CharacterDBRepository characterDBRepository;
    private CharacterRepository characterRepository;
    private HomePageContract.View view;

    HomePagePresenter(CharacterListFragment view, CharacterDBRepository characterDBRepository){
        this.view = view;
        this.characterRepository = Injection.provideCharacterRepository();
        this.characterDBRepository = characterDBRepository;
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
                view.setTotalCharacters(object.getData().getTotal());
                long timestamp = ts;
                timestamp += 1;
//                SAVE AND ENCRYPT TIMESTAMP INTO PREFERENCES
                EncryptPreferencesMgmt.getInstance().setTimeStamp(timestamp);
                List<CharacterModel> list = object.getData().getResults();
                setThumbnails(list);
                characterDBRepository.insertAll(list);
            }

            @Override
            public void onFailure(String error) {
                view.hideProgressBar();
                view.showError(error);
            }
        });

    }

    private void setThumbnails(List<CharacterModel> list) {
        for(int i = 0; i < list.size(); i++){
            list.get(i).setFilename(list.get(i).getThumbnail().getFileName());
        }
    }


    void setFavourite(CharacterModel item) {
        characterDBRepository.updateCharacter(item);
    }
}