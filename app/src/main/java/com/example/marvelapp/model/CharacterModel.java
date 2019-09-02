package com.example.marvelapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CharacterModel implements Parcelable {

    @SerializedName("id")
     private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("thumbnail")
    private CharacterThumbnailModel thumbnail;

    private boolean isFavourite;


    protected CharacterModel(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        isFavourite = in.readByte() != 0;
    }


    public static final Creator<CharacterModel> CREATOR = new Creator<CharacterModel>() {
        @Override
        public CharacterModel createFromParcel(Parcel in) {
            return new CharacterModel(in);
        }


        @Override
        public CharacterModel[] newArray(int size) {
            return new CharacterModel[size];
        }
    };


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public CharacterThumbnailModel getThumbnail() {
        return thumbnail;
    }


    public boolean isFavourite() {
        return isFavourite;
    }


    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }
}
