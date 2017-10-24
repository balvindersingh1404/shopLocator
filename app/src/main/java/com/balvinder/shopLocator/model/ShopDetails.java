package com.balvinder.shopLocator.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShopDetails {
    @SerializedName("formatted_phone_number")
    @Expose
    public String formattedPhoneNumber;

    @SerializedName("geometry")
    @Expose
    public Geometry geometry;

    @SerializedName("icon")
    @Expose
    public String icon;

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("international_phone_number")
    @Expose
    public String internationalPhoneNumber;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("opening_hours")
    @Expose
    public OpeningHours openingHours;

    @SerializedName("place_id")
    @Expose
    public String placeId;

    @SerializedName("rating")
    @Expose
    public Double rating;

    @SerializedName("scope")
    @Expose
    public String scope;

    @SerializedName("types")
    @Expose
    public List<String> types = new ArrayList<String>();

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("vicinity")
    @Expose
    public String vicinity;

    @SerializedName("website")
    @Expose
    public String website;

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

