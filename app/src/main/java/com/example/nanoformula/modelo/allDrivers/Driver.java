
package com.example.nanoformula.modelo.allDrivers;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Driver implements Parcelable {

    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("givenName")
    @Expose
    private String givenName;
    @SerializedName("familyName")
    @Expose
    private String familyName;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("nationality")
    @Expose
    private String nationality;

    private String urlImage;

    private int victorias;

    private int podios;
    private double puntos;

    private int titulos;
    private int poles;
    private int temporadas;
    private int vueltasRapidas;
    private ArrayList<String> escuderias;


    protected Driver(Parcel in) {
        driverId = in.readString();
        url = in.readString();
        givenName = in.readString();
        familyName = in.readString();
        dateOfBirth = in.readString();
        nationality = in.readString();
        urlImage = in.readString();
        victorias = in.readInt();
        podios = in.readInt();
        puntos = in.readDouble();
        titulos = in.readInt();
        poles = in.readInt();
        temporadas = in.readInt();
        vueltasRapidas = in.readInt();
        escuderias = in.createStringArrayList();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

    public int getPoles() {
        return poles;
    }

    public void setPoles(int poles) {
        this.poles = poles;
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public int getVueltasRapidas() {
        return vueltasRapidas;
    }

    public void setVueltasRapidas(int vueltasRapidas) {
        this.vueltasRapidas = vueltasRapidas;
    }

    public ArrayList<String> getEscuderias() {
        if(escuderias == null){
            escuderias = new ArrayList<>();
        }
        return escuderias;
    }

    public void setEscuderias(ArrayList<String> escuderias) {
        this.escuderias = escuderias;
    }

    public int getPodios() {
        return podios;
    }

    public void setPodios(int podios) {
        this.podios = podios;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }



    @Override
    public String toString(){
        return getGivenName() + " " + getFamilyName();
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

        parcel.writeString(driverId);
        parcel.writeString(url);
        parcel.writeString(givenName);
        parcel.writeString(familyName);
        parcel.writeString(dateOfBirth);
        parcel.writeString(nationality);
        parcel.writeString(urlImage);
        parcel.writeInt(victorias);
        parcel.writeInt(podios);
        parcel.writeDouble(puntos);
        parcel.writeInt(titulos);
        parcel.writeInt(poles);
        parcel.writeInt(temporadas);
        parcel.writeInt(vueltasRapidas);
        parcel.writeStringList(escuderias);
    }

    public int getTitulos() {
        return titulos;
    }

    public void setTitulos(int titulos) {
        this.titulos = titulos;
    }

    public void addEscuderia(String escuderia){
        escuderias.add(escuderia);
    }

}
