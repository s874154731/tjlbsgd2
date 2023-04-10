package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   :  店铺申请单list
 */
public final class ShopApplayWarrantyListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_shop_list";
    }

    private int area_id;            //区域ID        用户状态获取
    private int page;           //页码

    public ShopApplayWarrantyListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.area_id = userInfo.getArea_id();
    }

    public ShopApplayWarrantyListApi setPage(int page) {
        this.page = page;
        return this;
    }

    public final static class Bean {

    }
}