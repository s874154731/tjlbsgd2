package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 修改用户名
 */
public final class UpdateUserNameApi implements IRequestApi {

    @Override
    public String getApi() {
        return "modify_user_name";
    }

    /** id */
    private String user_id;
    /** name */
    private String user_name;

    public UpdateUserNameApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public UpdateUserNameApi setUserName(String name) {
        this.user_name = name;
        return this;
    }

    public final static class Bean {

    }
}