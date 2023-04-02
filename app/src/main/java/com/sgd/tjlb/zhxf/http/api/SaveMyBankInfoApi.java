package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 提交我的银行卡信息
 */
public final class SaveMyBankInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "add_bank_user";
    }

    private String bank_user_id;//银行ID
    private String user_id;//用户ID
    private String bank_name;//银行名称
    private String bank_user;//开户名称
    private String bank_no;//卡号
    private String bank_address;//开户行地址

    public SaveMyBankInfoApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public SaveMyBankInfoApi setBank_user_id(String bank_user_id) {
        this.bank_user_id = bank_user_id;
        return this;
    }

    public SaveMyBankInfoApi setBank_name(String bank_name) {
        this.bank_name = bank_name;
        return this;
    }

    public SaveMyBankInfoApi setBank_user(String bank_user) {
        this.bank_user = bank_user;
        return this;
    }

    public SaveMyBankInfoApi setBank_no(String bank_no) {
        this.bank_no = bank_no;
        return this;
    }

    public SaveMyBankInfoApi setBank_address(String bank_address) {
        this.bank_address = bank_address;
        return this;
    }
}