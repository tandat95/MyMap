package com.vbd.mapexam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tandat on 11/13/2017.
 */

public class SearchResultObj {


        @SerializedName("building")
        @Expose
        private Object building;
        @SerializedName("categoryCode")
        @Expose
        private Integer categoryCode;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("fax")
        @Expose
        private Object fax;
        @SerializedName("floor")
        @Expose
        private Object floor;
        @SerializedName("groupCode")
        @Expose
        private Integer groupCode;
        @SerializedName("latitude")
        @Expose
        private Double latitude;
        @SerializedName("longitude")
        @Expose
        private Double longitude;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("province")
        @Expose
        private String province;
        @SerializedName("room")
        @Expose
        private Object room;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("vietbandoId")
        @Expose
        private String vietbandoId;
        @SerializedName("ward")
        @Expose
        private String ward;

        public Object getBuilding() {
            return building;
        }

        public void setBuilding(Object building) {
            this.building = building;
        }

        public Integer getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(Integer categoryCode) {
            this.categoryCode = categoryCode;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public Object getFax() {
            return fax;
        }

        public void setFax(Object fax) {
            this.fax = fax;
        }

        public Object getFloor() {
            return floor;
        }

        public void setFloor(Object floor) {
            this.floor = floor;
        }

        public Integer getGroupCode() {
            return groupCode;
        }

        public void setGroupCode(Integer groupCode) {
            this.groupCode = groupCode;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public Object getRoom() {
            return room;
        }

        public void setRoom(Object room) {
            this.room = room;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getVietbandoId() {
            return vietbandoId;
        }

        public void setVietbandoId(String vietbandoId) {
            this.vietbandoId = vietbandoId;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

}
