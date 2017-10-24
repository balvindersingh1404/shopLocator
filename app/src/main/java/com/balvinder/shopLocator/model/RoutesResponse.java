package com.balvinder.shopLocator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoutesResponse {
    @SerializedName("routes")
    @Expose
    public List<Route> routes = new ArrayList<Route>();
    @SerializedName("status")
    @Expose
    public String status;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
