
package com.example.nanoformula.modelo.raceSchedule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Circuit implements Parcelable {

    @SerializedName("circuitId")
    @Expose
    private String circuitId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("circuitName")
    @Expose
    private String circuitName;
    @SerializedName("Location")
    @Expose
    private Location location;

    protected Circuit(Parcel in) {
        circuitId = in.readString();
        url = in.readString();
        circuitName = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(circuitId);
        dest.writeString(url);
        dest.writeString(circuitName);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Circuit> CREATOR = new Creator<Circuit>() {
        @Override
        public Circuit createFromParcel(Parcel in) {
            return new Circuit(in);
        }

        @Override
        public Circuit[] newArray(int size) {
            return new Circuit[size];
        }
    };

    public String getCircuitId() {
        return circuitId;
    }

    public void setCircuitId(String circuitId) {
        this.circuitId = circuitId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCircuitName() {
        return circuitName;
    }

    public void setCircuitName(String circuitName) {
        this.circuitName = circuitName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
