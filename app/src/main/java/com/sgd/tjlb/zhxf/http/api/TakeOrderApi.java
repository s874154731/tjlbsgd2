package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 接单
 */
public final class TakeOrderApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_shop";
    }

    private String shop_id;  		//门店ID        2中选择
    private String worker_id;  		//用户ID        用户状态获取

    public TakeOrderApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.worker_id = userInfo.getUserID();
    }

    public TakeOrderApi setShopID(String shop_id) {
        this.shop_id = shop_id;
        return this;
    }

    public final static class Bean {

    }
}