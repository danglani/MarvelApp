package com.example.marvelapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.marvelapp.R;
import com.example.marvelapp.model.CharacterModel;
import com.example.marvelapp.ui.CharacterListAdapter;
import com.example.marvelapp.ui.FavouriteClickListener;
import com.example.marvelapp.ui.ItemClickListener;
import com.example.marvelapp.utils.ScrollPaginationListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CharacterListFragment extends Fragment implements ItemClickListener, FavouriteClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private HomePagePresenter presenter;
    @BindView(R.id.progressbar) ProgressBar progressBar;
    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.container) ConstraintLayout constraintLayout;
    private List<CharacterModel> characterModelList = new ArrayList<>();
    private boolean stopLoadingMore;

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

        presenter = new HomePagePresenter(this);

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

    public boolean isStillLoading(){
        return progressBar.getVisibility() == View.VISIBLE;
    }

    void showCharacters(List<CharacterModel> results, int total) {
        characterModelList.addAll(results);
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        if(characterModelList.size() == total) {
            stopLoadingMore = true;
        }
    }


    void showError(String error) {
        Snackbar.make(constraintLayout, error, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onItemClickListener(CharacterModel item, View ivImage) {
        DetailFragment detailFragment = DetailFragment.newInstance(item, ViewCompat.getTransitionName(ivImage));
        detailFragment.setSharedElementEnterTransition(new AutoTransition());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addSharedElement(ivImage, ViewCompat.getTransitionName(ivImage));
        transaction.add(R.id.fl_main_content, detailFragment)
                .addToBackStack(TAG);
        transaction.commit();
    }


    @Override
    public void OnFavouriteClick(CharacterModel item) {

    }
}
