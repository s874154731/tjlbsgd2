package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 用户登录
 */
public final class LoginApi implements IRequestApi {

    @Override
    public String getApi() {
        return "login";
    }

    /** 用户名 */
    private String worker_tel;
    /** 登录密码 */
    private String worker_pwd;//密码（md5加密 32位大写）

    public LoginApi setPhone(String phone) {
        this.worker_tel = phone;
        return this;
    }

    public LoginApi setPassword(String password) {
//        this.user_pwd = MD5Util.MD5(password);
        this.worker_pwd = password;
        return this;
    }

    public final static class Bean {

        private String token;

        private String user_id;
        private String user_name;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getToken() {
            return token;
        }
    }
}