package com.sgd.tjlb.zhxf.entity;

import java.io.Serializable;

public class LocationBean implements Serializable {

    private double latitude;// 维度
    private double longitude; // 经度
    private String name;
    private String value;//编码
    private String detailLocal;//纤细地址

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetailLocal() {
        return detailLocal;
    }

    public void setDetailLocal(String detailLocal) {
        this.detailLocal = detailLocal;
    }
}
