package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 查询设备
 */
public final class QueryEquipmentApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_device";
    }


    private String device_id; //设备ID

    public QueryEquipmentApi setDeviceID(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public final static class Bean {

    }
}