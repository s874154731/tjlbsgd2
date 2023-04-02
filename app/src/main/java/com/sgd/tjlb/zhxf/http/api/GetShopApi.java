package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 添加店铺
 */
public final class GetShopApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_shop";
    }

    private String user_id;

    public GetShopApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public final static class Bean {

    }
}