package com.balvinder.shopLocator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NearByShopsResponse {
    @SerializedName("results")
    @Expose
    private List<Shop> results = new ArrayList<Shop>();

    @SerializedName("status")
    @Expose
    private String status;

    public List<Shop> getResults() {
        return results;
    }

    public void setResults(List<Shop> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
