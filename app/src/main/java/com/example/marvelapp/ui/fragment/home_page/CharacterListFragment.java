package com.example.marvelapp.ui.fragment.home_page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.marvelapp.R;
import com.example.marvelapp.model.CharacterModel;
import com.example.marvelapp.model.database.CharacterDBRepository;
import com.example.marvelapp.ui.fragment.details.DetailFragment;
import com.example.marvelapp.ui.fragment.home_page.adapter.CharacterListAdapter;
import com.example.marvelapp.ui.fragment.listener.FavouriteClickListener;
import com.example.marvelapp.ui.fragment.listener.ItemClickListener;
import com.example.marvelapp.utils.ScrollPaginationListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListFragment extends Fragment implements ItemClickListener, FavouriteClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = CharacterListFragment.class.getSimpleName();
    private int mColumnCount = 2;
    private List<CharacterModel> characterModelList = new ArrayList<>();
    private HomePagePresenter presenter;
    private boolean stopLoadingMore;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.container) ConstraintLayout constraintLayout;
    private CharacterDBRepository characterDBRepository;


    public CharacterListFragment() {
    }

    public static CharacterListFragment newInstance(int columnCount) {
        CharacterListFragment fragment = new CharacterListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        if(getActivity() != null) {
            characterDBRepository = new CharacterDBRepository(getActivity().getApplication());
        }
        presenter = new HomePagePresenter(this, characterDBRepository);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_list, container, false);
        ButterKnife.bind(this, view);
        presenter.downloadCharacters();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        recyclerView.setAdapter(new CharacterListAdapter(this, characterModelList));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new ScrollPaginationListener((LinearLayoutManager)recyclerView.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                if (!stopLoadingMore) {
                    presenter.downloadCharacters();
                }
            }

            @Override
            public boolean isLoading() {
                return isStillLoading();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }


    void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private boolean isStillLoading(){
        return progressBar.getVisibility() == View.VISIBLE;
    }

    void showCharacters(List<CharacterModel> results, int total) {
        characterModelList.addAll(results);
        if(characterModelList.size() == total) {
            stopLoadingMore = true;
        }
        if(characterModelList.size() == 0){
            characterModelList.addAll(presenter.getAllCharacters());
            recyclerView.getAdapter().notifyDataSetChanged();
        }else {
            presenter.syncronizeFavourites(characterModelList);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }


    void showError(String error) {
        Snackbar.make(constraintLayout, error, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onItemClickListener(CharacterModel item, View ivImage) {
        if (getFragmentManager() !=null) {
            Fragment animalDetailFragment = DetailFragment.newInstance(item, ViewCompat.getTransitionName(ivImage));
            getFragmentManager()
                    .beginTransaction()
                    .addSharedElement(ivImage,  ViewCompat.getTransitionName(ivImage))
                    .addToBackStack(TAG)
                    .add(R.id.fl_main_content, animalDetailFragment)
                    .commit();
        }
    }


    @Override
    public void OnFavouriteClick(CharacterModel item) {
        presenter.setFavourite(item);
    }
}
