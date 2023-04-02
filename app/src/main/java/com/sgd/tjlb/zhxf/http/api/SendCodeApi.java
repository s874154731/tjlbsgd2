package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.annotation.HttpIgnore;
import com.hjq.http.config.IRequestApi;

/**
 * 发送验证码
 */
public final class SendCodeApi implements IRequestApi {

    @HttpIgnore
    public static final int TYPE_REGISTER = 1;//注册
    @HttpIgnore
    public static final int TYPE_FORGET_PW = 2;//忘记密码
    @HttpIgnore
    public static final int TYPE_CHANGEE_TEL = 3;//更换手机号

    @Override
    public String getApi() {
        return "send_code";
    }

    /** 用户名 */
    private String user_tel;

    /** 类型：1注册 2忘记密码 3更换手机号 */
    private int type;

    public SendCodeApi setPhone(String phone) {
        this.user_tel = phone;
        return this;
    }

    public SendCodeApi setType(int type) {
        this.type = type;
        return this;
    }

    public final static class Bean {

        private String code;

        public String getCode() {
            return code;
        }
    }
}