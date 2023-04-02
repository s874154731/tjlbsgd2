package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 忘记密码
 */
public final class ForgetPwApi implements IRequestApi {

    @Override
    public String getApi() {
        return "forget_user_pwd";
    }

    /**用户 手机号*/
    private String user_tel;//
    /** 新密码 */
    private String new_user_pwd;
    /** 确认新密码 */
    private String rep_user_pwd;

    /**
     * 手机号
     * @param user_tel
     */
    public ForgetPwApi setPhone(String user_tel) {
        this.user_tel = user_tel;
        return this;
    }

    /**
     * 新密码
     * @param newPwd
     */
    public ForgetPwApi setNewPwd(String newPwd) {
        this.new_user_pwd = newPwd;
        return this;
    }

    /**
     * 确认新密码
     * @param repPwd
     */
    public ForgetPwApi setRepPwd(String repPwd) {
        this.rep_user_pwd = repPwd;
        return this;
    }
}