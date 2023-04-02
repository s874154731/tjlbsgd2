package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 我的设备-警报列表
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EquipmentWaringListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_device_alarm_list";
    }

    private String device_sn;//设备SN码
    private int page;

    public EquipmentWaringListApi() {
    }

    public EquipmentWaringListApi setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
        return this;
    }

    public EquipmentWaringListApi setPage(int page) {
        this.page = page;
        return this;
    }
}
