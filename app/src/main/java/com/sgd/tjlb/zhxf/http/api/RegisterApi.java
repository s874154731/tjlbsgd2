package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 用户注册
 */
public final class RegisterApi implements IRequestApi {

    @Override
    public String getApi() {
        return "register_user";
    }

    /** 手机号 */
    private String user_tel;
    /** 新密码 */
    private String new_user_pwd;
    /** 确认新密码 */
    private String rep_user_pwd;
    /** 验证码 */
    private String validate_code;

    public RegisterApi setPhone(String phone) {
        this.user_tel = phone;
        return this;
    }

    public RegisterApi setNewPwd(String newPwd) {
        this.new_user_pwd = newPwd;
        return this;
    }

    public RegisterApi setRepPwd(String repPwd) {
        this.rep_user_pwd = repPwd;
        return this;
    }

    public RegisterApi setCode(String code) {
        this.validate_code = code;
        return this;
    }

    public final static class Bean {

    }
}