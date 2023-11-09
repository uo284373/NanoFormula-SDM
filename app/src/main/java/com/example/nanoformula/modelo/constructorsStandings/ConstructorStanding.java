
package com.example.nanoformula.modelo.constructorsStandings;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    protected ConstructorStanding(Parcel in) {
        position = in.readString();
        positionText = in.readString();
        points = in.readString();
        wins = in.readString();
        constructor = in.readParcelable(Constructor.class.getClassLoader());
        round = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(position);
        dest.writeString(positionText);
        dest.writeString(points);
        dest.writeString(wins);
        dest.writeParcelable(constructor, flags);
        dest.writeString(round);
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
}
