package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 修改设备
 */
public final class UpdateEquipmentApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_device";
    }

    private String device_id;//设备ID
    private String device_sn;//设备SN码  输入或者扫描---安装的弹窗显示

    public UpdateEquipmentApi setDeviceID(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public UpdateEquipmentApi setDeviceSn(String device_sn) {
        this.device_sn = device_sn;
        return this;
    }

    public final static class Bean {

    }
}