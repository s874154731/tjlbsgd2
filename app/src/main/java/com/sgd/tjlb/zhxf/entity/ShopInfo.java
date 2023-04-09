package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

/**
 * 店铺信息
 */
public class ShopInfo {

    public static final int Page_Type_OK = 0;//申请安装
    public static final int Page_Type_Installation = 1;//申请安装
    public static final int Page_Type_Installation_Warring = 2;//安装异常
    public static final int Page_Type_Apply_Maintenance = 3;//申请维修
    public static final int Page_Type_Maintenance_Warring = 4;//维修异常

    private String user_id;
    private String shop_id;//店铺id
    private String shop_name;//门店名称
    private int shop_type_id;//所属行业    选中
    private int city_id;//市
    private int area_id;//area_id
    private String address;//地址
    private double longitude;//经
    private double latitude;//纬
    private String contact;//紧急联系人
    private String contact_tel;//紧急联系人电话
    private String shop_area;//门店面积
    private String shop_info;//门店详情
    private String sell_no;//销售编码

    private String status;//0成功，1申请安装，2安装异常，3申请维修，4维修

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShop_name() {
        return TextUtils.isEmpty(shop_name) ? "" : shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getShop_type_id() {
        return shop_type_id;
    }

    public void setShop_type_id(int shop_type_id) {
        this.shop_type_id = shop_type_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getContact() {
        return TextUtils.isEmpty(contact) ? "" : contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact_tel() {
        return TextUtils.isEmpty(contact_tel) ? "" : contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public String getShop_area() {
        return TextUtils.isEmpty(shop_area) ? "" : shop_area;
    }

    public void setShop_area(String shop_area) {
        this.shop_area = shop_area;
    }

    public String getShop_info() {
        return TextUtils.isEmpty(shop_info) ? "" : shop_info;
    }

    public void setShop_info(String shop_info) {
        this.shop_info = shop_info;
    }

    public String getSell_no() {
        return TextUtils.isEmpty(sell_no) ? "" : sell_no;
    }

    public void setSell_no(String sell_no) {
        this.sell_no = sell_no;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
