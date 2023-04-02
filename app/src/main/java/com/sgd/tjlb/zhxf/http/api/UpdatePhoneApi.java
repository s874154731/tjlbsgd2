package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 修改手机号
 */
public final class UpdatePhoneApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_user_tel";
    }

    /**用户 id*/
    private String user_id;//
    /**旧密码*/
    private String user_pwd;//
    /** 新手机号 */
    private String user_tel;
    /** 验证码 */
    private String validate_code;

    public UpdatePhoneApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    /**
     * 旧密码
     * @param pwd
     */
    public UpdatePhoneApi setPwd(String pwd) {
        this.user_pwd = pwd;
        return this;
    }
    /**
     * 手机号
     * @param phone
     */
    public UpdatePhoneApi setTel(String phone) {
        this.user_tel = phone;
        return this;
    }

    /**
     * 验证码
     * @param code
     * @return
     */
    public UpdatePhoneApi setCode(String code) {
        this.validate_code = code;
        return this;
    }
}