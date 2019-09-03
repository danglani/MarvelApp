package com.example.marvelapp.ui.fragment.details;

import android.content.Context;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvelapp.R;
import com.example.marvelapp.model.CharacterModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {
    private static final String TRANSITION_NAME = "TRANSITION_NAME";
    private static final String CHARACTER = "CHARACTER";

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivImage)
    ImageView ivImage;


    public DetailFragment() {
    }


    public static DetailFragment newInstance(CharacterModel characterItem, String transitionName) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHARACTER, characterItem);
        args.putString(TRANSITION_NAME, transitionName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getArguments() != null) {
            CharacterModel character = getArguments().getParcelable(CHARACTER);
            String transitionName = getArguments().getString(TRANSITION_NAME);


            if (character != null) {
                tvName.setText(character.getName());
                tvDescription.setText(character.getDescription());

                if (character.isFavourite()) {
                    tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_favourite_gold, null), null);
                }

                ivImage.setTransitionName(transitionName);

                Picasso.get()
                        .load(character.getFilename())
                        .placeholder(R.drawable.image_not_available)
                        .into(ivImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                startPostponedEnterTransition();
                            }


                            @Override
                            public void onError(Exception e) {
                                startPostponedEnterTransition();
                            }

                        });
            }
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
