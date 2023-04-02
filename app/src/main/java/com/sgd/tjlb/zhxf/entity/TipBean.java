package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

public class TipBean {

    private String news_info;

    public String getNews_info() {
        return TextUtils.isEmpty(news_info) ? "" : news_info;
    }

    public void setNews_info(String news_info) {
        this.news_info = news_info;
    }
}
