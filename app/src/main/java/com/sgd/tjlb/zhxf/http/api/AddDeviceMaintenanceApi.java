package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 添加设备维修
 */
public final class AddDeviceMaintenanceApi implements IRequestApi {

    @Override
    public String getApi() {
        return "add_device_work";
    }

    private String device_id;//设备ID
    private String status_img;//图片
    private String status_info;//申请内容

    public AddDeviceMaintenanceApi setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public AddDeviceMaintenanceApi setStatus_img(String status_img) {
        this.status_img = status_img;
        return this;
    }

    public AddDeviceMaintenanceApi setStatus_info(String status_info) {
        this.status_info = status_info;
        return this;
    }

    public final static class Bean {

    }
}