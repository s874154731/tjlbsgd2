package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("user_id")
    private String userID;
    @SerializedName("user_name")
    private String userName;
    private int city_id;
    private int area_id = -1;

    private String tel;//手机号
    private String nickname;//昵称
    private String avatar;//头像
    private int sex;//性别

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTel() {
        return TextUtils.isEmpty(tel) ? "未设置手机号" : tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserName() {
        return TextUtils.isEmpty(userName) ? "佚名" : userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
