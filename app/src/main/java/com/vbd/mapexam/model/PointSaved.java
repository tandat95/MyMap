package com.vbd.mapexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tandat on 11/17/2017.
 */

public class PointSaved {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("inform")
    @Expose
    private String inform;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("extraElements")
    @Expose
    private Object extraElements;
    @SerializedName("namePoint")
    @Expose
    private String namePoint;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lg")
    @Expose
    private Double lg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getInform() {
        return inform;
    }

    public void setInform(String inform) {
        this.inform = inform;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getExtraElements() {
        return extraElements;
    }

    public void setExtraElements(Object extraElements) {
        this.extraElements = extraElements;
    }

    public String getNamePoint() {
        return namePoint;
    }

    public void setNamePoint(String namePoint) {
        this.namePoint = namePoint;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLg() {
        return lg;
    }

    public void setLg(Double lg) {
        this.lg = lg;
    }

}