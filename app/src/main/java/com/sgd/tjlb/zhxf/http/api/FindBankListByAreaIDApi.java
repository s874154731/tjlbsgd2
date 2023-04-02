package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 根据区域id 获取银行卡列表
 */
public final class FindBankListByAreaIDApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_bank_url";
    }

    private int area_id;

    /**
     * 设置区域id
     * @param areaID id
     * @return this
     */
    public FindBankListByAreaIDApi setAreaId(int areaID) {
        this.area_id = areaID;
        return this;
    }
}