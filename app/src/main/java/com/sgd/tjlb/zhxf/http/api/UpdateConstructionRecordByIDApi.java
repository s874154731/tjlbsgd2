package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   : 修改维修记录
 */
public final class UpdateConstructionRecordByIDApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_device_work";
    }

    private String worker_id;//用户ID        用户状态获取
    private String device_work_id;//工作记录ID
    private String status_img;// 图片
    private String status_info;//  描述
    private String status;//  0成功，2安装异常，4维修异常
    private String device_id;// 设备id

    public UpdateConstructionRecordByIDApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.worker_id = userInfo.getUserID();
    }

    public UpdateConstructionRecordByIDApi setDeviceWorkID(String id) {
        this.device_work_id = id;
        return this;
    }

    public UpdateConstructionRecordByIDApi setStatusImg(String status_img) {
        this.status_img = status_img;
        return this;
    }

    public UpdateConstructionRecordByIDApi setStatusInfo(String status_info) {
        this.status_info = status_info;
        return this;
    }

    public UpdateConstructionRecordByIDApi setStatus(int status) {
        this.status = String.valueOf(status);
        return this;
    }

    public UpdateConstructionRecordByIDApi setDeviceId(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public final static class Bean {

    }
}