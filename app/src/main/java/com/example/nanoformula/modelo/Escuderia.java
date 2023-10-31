package com.example.nanoformula.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.nanoformula.R;

import java.util.Set;

public class Escuderia implements Parcelable {

    private int position;
    private String name;
    private String nacionalidad;
    private Set<String> pilots;
    private int points;
    private int wins;
    private int titles;
    private int seasons;
    private int foto;

    public Escuderia(int position, String name, String nacionalidad, Set<String> pilots, int points) {
        this.position = position;
        this.name = name;
        this.nacionalidad = nacionalidad;
        this.pilots = pilots;
        this.points = points;
        this.foto = R.drawable.mclaren;
    }

    public Escuderia(int position, String name, String nacionalidad, Set<String> pilots, int points, int wins, int titles, int seasons, int foto) {
        this.position = position;
        this.name = name;
        this.nacionalidad = nacionalidad;
        this.pilots = pilots;
        this.points = points;
        this.wins = wins;
        this.titles = titles;
        this.seasons = seasons;
        this.foto = foto;
    }

    protected Escuderia(Parcel in) {
        position = in.readInt();
        name = in.readString();
        nacionalidad = in.readString();
        points = in.readInt();
        wins = in.readInt();
        titles = in.readInt();
        seasons = in.readInt();
        foto = in.readInt();
    }

    public static final Creator<Escuderia> CREATOR = new Creator<Escuderia>() {
        @Override
        public Escuderia createFromParcel(Parcel in) {
            return new Escuderia(in);
        }

        @Override
        public Escuderia[] newArray(int size) {
            return new Escuderia[size];
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Set<String> getPilots() {
        return pilots;
    }

    public void setPilots(Set<String> pilots) {
        this.pilots = pilots;
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

    public int getTitles() {
        return titles;
    }

    public void setTitles(int titles) {
        this.titles = titles;
    }

    public int getSeasons() {
        return seasons;
    }

    public void setSeasons(int seasons) {
        this.seasons = seasons;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(position);
        parcel.writeString(name);
        parcel.writeString(nacionalidad);
        parcel.writeInt(points);
        parcel.writeInt(wins);
        parcel.writeInt(titles);
        parcel.writeInt(seasons);
        parcel.writeInt(foto);
    }
}
