package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 添加设备维修记录
 */
public final class AddDeviceMaintenanceRecordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "add_device_work";
    }

    private String worker_id;//用户名
    private String device_id;//设备ID
    private String status_img;//图片
    private String status_info;//申请内容
    private String status;//0成功，2安装异常，4维修异常

    public AddDeviceMaintenanceRecordApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.worker_id = userInfo.getUserID();
    }

    public AddDeviceMaintenanceRecordApi setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public AddDeviceMaintenanceRecordApi setStatus_img(String status_img) {
        this.status_img = status_img;
        return this;
    }

    public AddDeviceMaintenanceRecordApi setStatus_info(String status_info) {
        this.status_info = status_info;
        return this;
    }

    public AddDeviceMaintenanceRecordApi setStatus(int status) {
        this.status = String.valueOf(status);
        return this;
    }

    public final static class Bean {

    }
}