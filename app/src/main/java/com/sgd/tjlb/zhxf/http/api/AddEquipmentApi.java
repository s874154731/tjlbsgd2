package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 添加设备
 */
public final class AddEquipmentApi implements IRequestApi {

    @Override
    public String getApi() {
        return "add_device";
    }

    private String worker_id;        //用户ID        用户状态获取
    private String user_id;          //客户端用户ID
    private String device_sn;       //设备SN码  输入或者扫描---安装的弹窗显示

    public AddEquipmentApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.worker_id = userInfo.getUserID();
    }

    public AddEquipmentApi setShopid(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public AddEquipmentApi setDeviceSn(String device_sn) {
        this.device_sn = device_sn;
        return this;
    }

    public final static class Bean {

    }
}