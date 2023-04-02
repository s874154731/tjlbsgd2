package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 获取标签
 */
public final class FindTipByTypeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_app_news_by_type";
    }

    private int type;

    public FindTipByTypeApi setType(int type) {
        this.type = type;
        return this;
    }
}