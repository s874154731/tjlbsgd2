package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 门店行业list   ShopTypeInfo
 */
public final class ShopTypeListApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_shop_type_list";
    }

    public final static class Bean {

    }
}