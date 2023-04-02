package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * 地理位置-城市集合
 */
public final class AdressCitiesApi implements IRequestApi {

    @Override
    public String getApi() {
        return "re_city_list";
    }
}