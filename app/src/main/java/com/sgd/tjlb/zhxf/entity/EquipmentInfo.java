package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * 设备信息
 */
public class EquipmentInfo {

    @SerializedName("device_id")
    private String id;//设备ID
    private String device_sn;//设备Sn
    private int on_line;//在线状态 0离线，1在线
    private int status;//0安装成功，1申请，2预约，3安装，4安装异常，5维修，6维修异常
    private int alarm_count;//新报警数量

    private String name;
    private String imgUrl;
    private boolean isOnline;
    private boolean isWaring;

    public String getId() {
        return TextUtils.isEmpty(id) ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isOnline() {
        return on_line == 1;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getDevice_sn() {
        return TextUtils.isEmpty(device_sn) ? "设备SN" : device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public int getOn_line() {
        return on_line;
    }

    public void setOn_line(int on_line) {
        this.on_line = on_line;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAlarm_count() {
//        return alarm_count > 99 ? 99 : alarm_count;
        return Math.min(alarm_count, 99);
    }

    public void setAlarm_count(int alarm_count) {
        this.alarm_count = alarm_count;
    }

    public boolean isWaring() {
        return isWaring;
    }

    public void setWaring(boolean waring) {
        isWaring = waring;
    }
}
