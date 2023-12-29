
package com.example.nanoformula.modelo.raceImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Query {

    @SerializedName("normalized")
    @Expose
    private List<Normalized> normalized;
    @SerializedName("pages")
    @Expose
    private List<Page> pages;

    public List<Normalized> getNormalized() {
        return normalized;
    }

    public void setNormalized(List<Normalized> normalized) {
        this.normalized = normalized;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

}
