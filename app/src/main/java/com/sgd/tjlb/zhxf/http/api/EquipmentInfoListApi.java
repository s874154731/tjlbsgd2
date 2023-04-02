package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 我的设备列表
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EquipmentInfoListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_device_list";
    }

    private String user_id;

    public EquipmentInfoListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }
}
