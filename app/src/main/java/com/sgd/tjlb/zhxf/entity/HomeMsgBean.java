package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

public class HomeMsgBean {

    private String msg_info;//消息内容
    private String create_time;//时间

    public String getMsg_info() {
        return TextUtils.isEmpty(msg_info) ? "未知内容" : msg_info;
    }

    public void setMsg_info(String msg_info) {
        this.msg_info = msg_info;
    }

    public String getCreate_time() {
        return TextUtils.isEmpty(create_time) ? "1900-01-01 00:00:00" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
