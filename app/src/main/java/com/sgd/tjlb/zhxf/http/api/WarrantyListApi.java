package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   : 获取报修单list
 */
public final class WarrantyListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_shop_list";
    }

    private int area_id;            //区域ID        用户状态获取
    private int page;           //页码
    private int type;// 1申请安装 3申请维修

    public WarrantyListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.area_id = userInfo.getArea_id();
    }

    public WarrantyListApi setPage(int page) {
        this.page = page;
        return this;
    }

    public WarrantyListApi setType(int type) {
        this.type = type;
        return this;
    }

    public final static class Bean {

    }
}