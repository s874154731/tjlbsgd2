package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import java.util.List;

/**
 * 店铺信息
 */
public class ShopInfo {

    private static final int Type_OK = 0;//成功
    private static final int Type_Installation_Apply = 1;//申请安装
    private static final int Type_Installation_ING = 2;//安装中
    private static final int Type_Apply_Maintenance = 3;//申请维修
    private static final int Type_Maintenance_ING = 4;//维修中
    private static final int Type_Installation_Warring = 5;//安装异常
    private static final int Type_Maintenance_Warring = 6;//维修异常

    private static final String Type_OK_TIP = "成功";//申请安装
    private static final String Type_Installation_Apply_TIP = "申请安装";//申请安装
    private static final String Type_Installation_ing_TIP = "安装中";//安装中
    private static final String Type_Apply_Maintenance_TIP = "申请维修";//申请维修
    private static final String Type_Maintenance_ING_TIP = "维修中";//维修中
    private static final String Type_Installation_Warring_TIP = "安装异常";//安装异常
    private static final String Type_Maintenance_Warring_TIP = "维修异常";//维修异常

    private static final String Type_Add_Equ_TIP = "添加设备";//对应安装
    private static final String Type_Work_Record_TIP = "维修记录";//对应申请维修

    private String user_id;//用户id？？？
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

    private int status;//0成功，1申请安装，2安装异常，3申请维修，4维修

    private List<EquipmentInfo> devicelist;

    public String getUser_id() {
        return TextUtils.isEmpty(user_id) ? "" : user_id;
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
        return TextUtils.isEmpty(shop_id) ? "" : shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<EquipmentInfo> getDevicelist() {
        return devicelist;
    }

    public void setDevicelist(List<EquipmentInfo> devicelist) {
        this.devicelist = devicelist;
    }

    public String getStatusTip() {
        switch (status) {
            case Type_Installation_Apply:
                return Type_Installation_Apply_TIP;
            case Type_Installation_ING:
                return Type_Installation_ing_TIP;
            case Type_Apply_Maintenance:
                return Type_Apply_Maintenance_TIP;
            case Type_Maintenance_ING:
                return Type_Maintenance_ING_TIP;
            case Type_Installation_Warring:
                return Type_Installation_Warring_TIP;
            case Type_Maintenance_Warring:
                return Type_Maintenance_Warring_TIP;
            case Type_OK:
            default:
                return Type_OK_TIP;
        }
    }

    public String getBtnShowTip() {
        switch (status) {
            case Type_Maintenance_ING:
                return Type_Work_Record_TIP;
            case Type_Installation_Apply:
            default:
                return Type_Add_Equ_TIP;
        }
    }

    /**
     * 添加设备状态
     * @return 状态
     */
    public boolean isAddDeviceStatus(){
        return status == Type_Installation_ING;
    }

    /**
     * 工作记录状态
     * @return 状态
     */
    public boolean isAddRecordStatus(){
        return status == Type_Maintenance_ING;
    }
}
