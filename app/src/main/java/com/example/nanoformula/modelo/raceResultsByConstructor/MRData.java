
package com.example.nanoformula.modelo.raceResultsByConstructor;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MRData {

    @SerializedName("xmlns")
    @Expose
    private String xmlns;
    @SerializedName("series")
    @Expose
    private String series;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("limit")
    @Expose
    private String limit;
    @SerializedName("offset")
    @Expose
    private String offset;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("RaceTable")
    @Expose
    private RaceTable raceTable;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public RaceTable getRaceTable() {
        return raceTable;
    }

    public void setRaceTable(RaceTable raceTable) {
        this.raceTable = raceTable;
    }

}
