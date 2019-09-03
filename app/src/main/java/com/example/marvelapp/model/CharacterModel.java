package com.example.marvelapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "character_table")
public class CharacterModel implements Parcelable {


    public CharacterModel() {
    }

    public CharacterModel(long id, String name, String description, CharacterThumbnailModel thumbnail, boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.filename = thumbnail.getFileName();
        this.isFavourite = isFavourite;
    }


    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("thumbnail")
    @Ignore
    private CharacterThumbnailModel thumbnail;

    @ColumnInfo(name = "filename")
    private String filename;

    private boolean isFavourite;


    private CharacterModel(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        isFavourite = in.readByte() != 0;
        filename = in.readString();
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


    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setThumbnail(CharacterThumbnailModel thumbnail) {
        this.thumbnail = thumbnail;
    }


    public boolean isFavourite() {
        return isFavourite;
    }


    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }


    public String getFilename() {
        return filename;
    }


    public void setFilename(String filename) {
        this.filename = filename;
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
        dest.writeString(filename);
    }


    @Override
    public String toString() {
        return "CharacterModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail=" + thumbnail +
                ", filename='" + filename + '\'' +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
