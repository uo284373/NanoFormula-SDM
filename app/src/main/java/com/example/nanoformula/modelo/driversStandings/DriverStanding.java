
package com.example.nanoformula.modelo.driversStandings;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverStanding implements Parcelable {

    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("positionText")
    @Expose
    private String positionText;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("wins")
    @Expose
    private String wins;
    @SerializedName("Driver")
    @Expose
    private Driver driver;
    @SerializedName("Constructors")
    @Expose
    private List<Constructor> constructors;

    protected DriverStanding(Parcel in) {
        position = in.readString();
        positionText = in.readString();
        points = in.readString();
        wins = in.readString();
    }

    public static final Creator<DriverStanding> CREATOR = new Creator<DriverStanding>() {
        @Override
        public DriverStanding createFromParcel(Parcel in) {
            return new DriverStanding(in);
        }

        @Override
        public DriverStanding[] newArray(int size) {
            return new DriverStanding[size];
        }
    };

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionText() {
        return positionText;
    }

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<Constructor> constructors) {
        this.constructors = constructors;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(position);
        parcel.writeString(positionText);
        parcel.writeString(points);
        parcel.writeString(wins);
    }
}
