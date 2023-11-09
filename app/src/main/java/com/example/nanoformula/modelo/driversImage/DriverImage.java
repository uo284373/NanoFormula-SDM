
package com.example.nanoformula.modelo.driversImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverImage {

    @SerializedName("batchcomplete")
    @Expose
    private Boolean batchcomplete;
    @SerializedName("query")
    @Expose
    private Query query;

    public Boolean getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(Boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

}
