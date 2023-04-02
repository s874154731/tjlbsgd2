package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

import java.math.BigDecimal;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 创建通讯流量订单
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CreateTrafficfessOrderApi implements IRequestApi {
    @Override
    public String getApi() {
        return "add_product_order";
    }

    private String user_id;
    private int product_id;//产品ID
    private String product_title;//产品标题
    private BigDecimal product_total;//产品总价
    private int years;//几年
    private int number;//数量

    public CreateTrafficfessOrderApi() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();
    }

    public CreateTrafficfessOrderApi setProduct_id(int product_id) {
        this.product_id = product_id;
        return this;
    }

    public CreateTrafficfessOrderApi setProduct_title(String product_title) {
        this.product_title = product_title;
        return this;
    }

    public CreateTrafficfessOrderApi setProduct_total(BigDecimal product_total) {
        this.product_total = product_total;
        return this;
    }

    public CreateTrafficfessOrderApi setYears(int years) {
        this.years = years;
        return this;
    }

    public CreateTrafficfessOrderApi setNumber(int number) {
        this.number = number;
        return this;
    }

    public final static class Bean {
        public String order_id;//订单id
    }
}
