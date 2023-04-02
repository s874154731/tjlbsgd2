package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 获取我的银行卡列表
 */
public final class FindMyBankListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_bank_user_list";
    }

    private String user_id;

    public FindMyBankListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }
}