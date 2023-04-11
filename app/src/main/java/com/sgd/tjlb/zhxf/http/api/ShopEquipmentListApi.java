package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   : 门店下的设备
 */
public final class ShopEquipmentListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_device_list";
    }

    private String user_id;//用户id


    public ShopEquipmentListApi setUserID(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public final static class Bean {

    }
}