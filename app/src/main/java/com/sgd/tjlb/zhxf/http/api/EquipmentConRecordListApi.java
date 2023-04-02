package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 我的设备-施工记录
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EquipmentConRecordListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_device_work_list";
    }

    private String device_id;//设备SN码
    private int page;

    public EquipmentConRecordListApi() {
    }

    public EquipmentConRecordListApi setDevice_ID(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public EquipmentConRecordListApi setPage(int page) {
        this.page = page;
        return this;
    }
}
