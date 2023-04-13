package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   : 我的接单list
 */
public final class MyOrderListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "my_shop_list";
    }

    private String worker_id;//     用户状态获取
    private int page;           //页码

    public MyOrderListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.worker_id = userInfo.getUserID();
    }

    public MyOrderListApi setPage(int page) {
        this.page = page;
        return this;
    }

    public final static class Bean {

    }
}