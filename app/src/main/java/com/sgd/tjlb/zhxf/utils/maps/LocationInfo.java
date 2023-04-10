package com.sgd.tjlb.zhxf.utils.maps;

/**
 * @ProjectName: tjlbsgd2
 * @Package: com.sgd.tjlb.zhxf.utils.maps
 * @ClassName: LocationInfo
 * @Description: 经纬度等信息封装
 * @CreateDate: 2023/4/10/010 11:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/4/10/010 11:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LocationInfo {
    private String key;
    private String  name;
    private double  latitude;
    private double longitude;
    private float rotation;
    private String time;
    private String oil;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public LocationInfo(double latitude, double longitude, String time, String oil) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.oil = oil;
    }

    public LocationInfo(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "LocationInfo{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
