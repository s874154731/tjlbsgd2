package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 忘记密码
 */
public final class UpdatePwApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_user_pwd";
    }

    /**用户 id*/
    private String user_id;//
    /**旧密码*/
    private String user_pwd;//
    /** 新密码 */
    private String new_user_pwd;
    /** 确认新密码 */
    private String rep_user_pwd;

    public UpdatePwApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    /**
     * 旧密码
     * @param oldPwd
     */
    public UpdatePwApi setOldPwd(String oldPwd) {
        this.user_pwd = oldPwd;
        return this;
    }
    /**
     * 新密码
     * @param newPwd
     */
    public UpdatePwApi setNewPwd(String newPwd) {
        this.new_user_pwd = newPwd;
        return this;
    }

    /**
     * 确认新密码
     * @param repPwd
     */
    public UpdatePwApi setRepPwd(String repPwd) {
        this.rep_user_pwd = repPwd;
        return this;
    }
}