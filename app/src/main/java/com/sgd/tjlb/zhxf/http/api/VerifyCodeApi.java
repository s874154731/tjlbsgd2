package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 验证码校验
 */
public final class VerifyCodeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "next_code";
    }

    /** 手机号 */
    private String user_tel;
    /** 验证码 */
    private String code;

    public VerifyCodeApi setPhone(String phone) {
        this.user_tel = phone;
        return this;
    }

    public VerifyCodeApi setCode(String code) {
        this.code = code;
        return this;
    }
}