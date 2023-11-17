
package com.example.nanoformula.modelo.driversStandings;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Constructor implements Parcelable {

    @SerializedName("constructorId")
    @Expose
    private String constructorId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nationality")
    @Expose
    private String nationality;

    protected Constructor(Parcel in) {
        constructorId = in.readString();
        url = in.readString();
        name = in.readString();
        nationality = in.readString();
    }

    public static final Creator<Constructor> CREATOR = new Creator<Constructor>() {
        @Override
        public Constructor createFromParcel(Parcel in) {
            return new Constructor(in);
        }

        @Override
        public Constructor[] newArray(int size) {
            return new Constructor[size];
        }
    };

    public String getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(constructorId);
        parcel.writeString(url);
        parcel.writeString(name);
        parcel.writeString(nationality);
    }
}
