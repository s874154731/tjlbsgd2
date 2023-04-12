package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   : 根据id获取记录
 */
public final class FindConstructionRecordByIDApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_device_work";
    }

    private String device_work_id;//     id


    public FindConstructionRecordByIDApi setDeviceID(String id) {
        this.device_work_id = id;
        return this;
    }

    public final static class Bean {

    }
}