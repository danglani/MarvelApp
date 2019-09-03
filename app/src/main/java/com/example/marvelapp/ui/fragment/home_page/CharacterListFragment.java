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
import com.example.marvelapp.viewmodel.CharacterViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListFragment extends Fragment implements HomePageContract.View, ItemClickListener, FavouriteClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = CharacterListFragment.class.getSimpleName();
    private int mColumnCount = 2;
    private HomePagePresenter presenter;
    private boolean stopLoadingMore;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.container) ConstraintLayout constraintLayout;
    private CharacterDBRepository characterDBRepository;
    private CharacterListAdapter adapter;
    private int totalCharacters = 0;


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
        CharacterViewModel viewModel = ViewModelProviders.of(this).get(CharacterViewModel.class);
        observeViewModel(viewModel);
    }


    private void observeViewModel(CharacterViewModel viewModel) {
        viewModel.getAllCharacters().observe(this, characterModels -> {
            adapter.setCharacters(characterModels);
//                STOP CALL API WHEN ALL ELEMENTS ARE SAVED
            if(characterModels.size() == totalCharacters){
                stopLoadingMore = true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_list, container, false);
        ButterKnife.bind(this, view);
        presenter.downloadCharacters();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        adapter = new CharacterListAdapter(this);
        recyclerView.setAdapter(adapter);
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

    private boolean isStillLoading(){
        return progressBar.getVisibility() == View.VISIBLE;
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


    @Override
    public void setTotalCharacters(int total) {
        totalCharacters = total;
    }


    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError(String error) {
        Snackbar.make(getView().findViewById(R.id.container), error, Snackbar.LENGTH_LONG).show();
    }
}
