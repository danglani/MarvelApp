package com.example.marvelapp.ui.fragment.home_page.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvelapp.R;
import com.example.marvelapp.model.CharacterModel;
import com.example.marvelapp.ui.fragment.listener.FavouriteClickListener;
import com.example.marvelapp.ui.fragment.listener.ItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private List<CharacterModel> mValues;
    private final ItemClickListener itemClickListener;
    private final FavouriteClickListener favouriteClickListener;


    public CharacterListAdapter(ItemClickListener listener) {
        itemClickListener = listener;
        favouriteClickListener = (FavouriteClickListener) listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public void setCharacters(List<CharacterModel> list){
        mValues = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView name;
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.fab_favourite)
        FloatingActionButton fabFavourite;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(CharacterModel model, int position) {
            name.setText(model.getName());
            try {
                Picasso.get()
                        .load(model.getFilename())
                        .placeholder(R.drawable.image_not_available)
                        .into(ivImage);
            }catch (Exception e){
             e.printStackTrace();
            }

//            SET DYNAMIC TRANSITION NAME TO SHOW IMAGE ANIMATION BETWEEN MASTER AND DETAIL FRAGMENT
            ViewCompat.setTransitionName(ivImage, String.valueOf(position));

            fabFavourite.setImageResource(model.isFavourite() ? R.drawable.ic_favourite_gold : R.drawable.ic_favourite_grey);

            ivImage.setOnClickListener(v -> itemClickListener.onItemClickListener(model, ivImage));

            fabFavourite.setOnClickListener(v -> {
                model.setFavourite(!model.isFavourite());
                favouriteClickListener.OnFavouriteClick(model);
                notifyItemChanged(position);
            });
        }

    }
}
