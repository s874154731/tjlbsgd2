package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.utils.MD5Util;

/**
 * 更换手机号
 */
public final class ChangePhoneApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_user_tel";
    }

    /** id */
    private String user_id;
    /** pwd */
    private String user_pwd;
    /** tel */
    private String user_tel;

    public ChangePhoneApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public ChangePhoneApi setPassword(String pwd) {
        this.user_pwd = MD5Util.MD5(pwd);;
//        this.user_pwd = pwd;
        return this;
    }

    public void setUserTel(String user_tel) {
        this.user_tel = user_tel;
    }

    public final static class Bean {

    }
}