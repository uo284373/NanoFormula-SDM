package com.example.nanoformula.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Piloto implements Parcelable {

    private int position;
    private String name;
    private String team;
    private int points;
    private int wins;

    public Piloto(int position, String name, String team, int points, int wins) {
        this.position = position;
        this.name = name;
        this.team = team;
        this.points = points;
        this.wins = wins;
    }

    protected Piloto(Parcel in) {
        position = in.readInt();
        name = in.readString();
        team = in.readString();
        points = in.readInt();
        wins = in.readInt();
    }

    public static final Creator<Piloto> CREATOR = new Creator<Piloto>() {
        @Override
        public Piloto createFromParcel(Parcel in) {
            return new Piloto(in);
        }

        @Override
        public Piloto[] newArray(int size) {
            return new Piloto[size];
        }
    };

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(position);
        parcel.writeString(name);
        parcel.writeString(team);
        parcel.writeInt(points);
        parcel.writeInt(wins);
    }
}
