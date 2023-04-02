package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 支付通讯流量订单
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PayTrafficfessOrderApi implements IRequestApi {
    @Override
    public String getApi() {
        return "modify_product_order";
    }

    private String order_id;

    public PayTrafficfessOrderApi setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }
}
