
package com.example.nanoformula.modelo.constructorsStandings;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ConstructorStanding implements Parcelable {

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
    @SerializedName("Constructor")
    @Expose
    private Constructor constructor;

    private String round;

    private List<String> driversNames = new ArrayList<>();
    private int drawableDetails;
    private int drawableFragment;

    protected ConstructorStanding(Parcel in) {
        position = in.readString();
        positionText = in.readString();
        points = in.readString();
        wins = in.readString();
        constructor = in.readParcelable(Constructor.class.getClassLoader());
        round = in.readString();
        driversNames = in.createStringArrayList();
        drawableDetails = in.readInt();
        drawableFragment = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(positionText);
        dest.writeString(points);
        dest.writeString(wins);
        dest.writeParcelable(constructor, flags);
        dest.writeString(round);
        dest.writeStringList(driversNames);
        dest.writeInt(drawableDetails);
        dest.writeInt(drawableFragment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConstructorStanding> CREATOR = new Creator<ConstructorStanding>() {
        @Override
        public ConstructorStanding createFromParcel(Parcel in) {
            return new ConstructorStanding(in);
        }

        @Override
        public ConstructorStanding[] newArray(int size) {
            return new ConstructorStanding[size];
        }
    };

    public int getDrawableDetails() {
        return drawableDetails;
    }

    public void setDrawableDetails(int drawableDetails) {
        this.drawableDetails = drawableDetails;
    }

    public int getDrawableFragment() {
        return drawableFragment;
    }

    public void setDrawableFragment(int drawableFragment) {
        this.drawableFragment = drawableFragment;
    }

    public List<String> getDriversNames() {
        return driversNames;
    }

    public void setDriversNames(List<String> driversNames) {
        this.driversNames = driversNames;
    }

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

    public Constructor getConstructor() {
        return constructor;
    }

    public void setConstructor(Constructor constructor) {
        this.constructor = constructor;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public void addDriversName(String driverName) {
        if(driversNames == null)
            driversNames = new ArrayList<>();
        driversNames.add(driverName);
    }


}
