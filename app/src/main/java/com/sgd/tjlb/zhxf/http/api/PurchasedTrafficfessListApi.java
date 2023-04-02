package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 已购买流量记录
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PurchasedTrafficfessListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_product_order_list";
    }

    private String user_id;
    private int product_id;
    private int page;

    public PurchasedTrafficfessListApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public PurchasedTrafficfessListApi setProduct_id(int product_id) {
        this.product_id = product_id;
        return this;
    }

    public PurchasedTrafficfessListApi setPage(int page) {
        this.page = page;
        return this;
    }
}
