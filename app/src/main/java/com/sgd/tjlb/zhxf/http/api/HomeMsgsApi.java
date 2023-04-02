package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 消息列表
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomeMsgsApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_msg_list";
    }

    private String user_id;
    private int page;

    public HomeMsgsApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public HomeMsgsApi setPage(int page) {
        this.page = page;
        return this;
    }
}
