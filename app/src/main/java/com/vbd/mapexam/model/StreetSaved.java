package com.vbd.mapexam.model;

/**
 * Created by tandat on 11/24/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StreetSaved {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("namePath")
    @Expose
    private String namePath;
    @SerializedName("idUser")
    @Expose
    private String idUser;
    @SerializedName("pointPath")
    @Expose

    private List<PointPath> pointPath = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public List<PointPath> getPointPath() {
        return pointPath;
    }

    public void setPointPath(List<PointPath> pointPath) {
        this.pointPath = pointPath;
    }

   public class PointPath {
        @SerializedName("namePoint")
        @Expose
        private String namePoint;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lg")
        @Expose
        private Double lg;

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

}