package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * desc   : 我的施工单记录
 */
public final class MyConstructionRecordListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_device_work_list";
    }

    private String worker_id;//     用户状态获取
    private int page;           //页码

    public MyConstructionRecordListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.worker_id = userInfo.getUserID();
    }

    public MyConstructionRecordListApi setPage(int page) {
        this.page = page;
        return this;
    }

    public final static class Bean {

    }
}