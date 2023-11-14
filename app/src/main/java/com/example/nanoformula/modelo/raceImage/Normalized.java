
package com.example.nanoformula.modelo.raceImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Normalized {

    @SerializedName("fromencoded")
    @Expose
    private Boolean fromencoded;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;

    public Boolean getFromencoded() {
        return fromencoded;
    }

    public void setFromencoded(Boolean fromencoded) {
        this.fromencoded = fromencoded;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
