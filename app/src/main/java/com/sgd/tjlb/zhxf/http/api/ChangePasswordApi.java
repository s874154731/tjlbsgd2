package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.utils.MD5Util;

/**
 * 修改密码
 */
public final class ChangePasswordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_user_pwd";
    }

    /** id */
    private String user_id;
    /** pwd */
    private String user_pwd;
    /** 新密码 */
    private String new_user_pwd;
    /** 确认新密码 */
    private String rep_user_pwd;

    public ChangePasswordApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public ChangePasswordApi setPassword(String pwd) {
        this.user_pwd = MD5Util.MD5(pwd);;
        return this;
    }

    /**
     * 新密码
     * @param newPwd
     */
    public void setNewPwd(String newPwd) {
        this.new_user_pwd = newPwd;
    }

    /**
     * 确认新密码
     * @param repPwd
     */
    public void setRepPwd(String repPwd) {
        this.rep_user_pwd = repPwd;
    }

    public final static class Bean {

    }
}