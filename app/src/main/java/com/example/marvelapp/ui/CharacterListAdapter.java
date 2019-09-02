package com.example.marvelapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marvelapp.R;
import com.example.marvelapp.model.CharacterModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private List<CharacterModel> mValues;
    private final ItemClickListener itemClickListener;
    private final FavouriteClickListener favouriteClickListener;


    public CharacterListAdapter(ItemClickListener listener, List<CharacterModel> characterModelList) {
        mValues = characterModelList;
        itemClickListener = listener;
        favouriteClickListener = (FavouriteClickListener) listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getName());
        if(holder.mItem.isFavourite()) {
            holder.fabFavourite.setImageResource(R.drawable.ic_favourite_gold);
        }else {
            holder.fabFavourite.setImageResource(R.drawable.ic_favourite_grey);
        }
        Picasso.get()
                .load(mValues.get(position).getThumbnail().getFileName())
                .into(holder.ivImage);

        ViewCompat.setTransitionName(holder.ivImage, String.valueOf(position));
//        ViewCompat.setTransitionName(holder.ivImage, "transition");

        holder.ivImage.setOnClickListener(v -> {
            if (null != itemClickListener) {
                itemClickListener.onItemClickListener(holder.mItem, holder.ivImage);
            }
        });

        holder.fabFavourite.setOnClickListener(v -> {
            if (null != favouriteClickListener) {
                holder.mItem.setFavourite(!holder.mItem.isFavourite());
                favouriteClickListener.OnFavouriteClick(holder.mItem);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView name;
        final ImageView ivImage;
        final FloatingActionButton fabFavourite;
        CharacterModel mItem;


        ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.tvTitle);
            ivImage = view.findViewById(R.id.ivImage);
            fabFavourite = view.findViewById(R.id.fab_favourite);

        }

    }
}
