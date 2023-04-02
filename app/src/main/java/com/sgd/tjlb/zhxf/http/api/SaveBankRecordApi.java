package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 我的银行卡—申请
 */
public final class SaveBankRecordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "add_bank_record";
    }

    private String user_id;
    private int bank_id;

    public SaveBankRecordApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public SaveBankRecordApi setBankID(int bank_id) {
        this.bank_id = bank_id;
        return this;
    }
}