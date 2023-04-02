package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;

/**
 * 添加店铺
 */
public final class AddShopApi implements IRequestApi {

    @Override
    public String getApi() {
        return "add_shop";
    }

    private String user_id;
    private String shop_name;//门店名称
    private int shop_type_id;//所属行业    选中
    private int city_id;//市
    private int area_id;//area_id
    private String address;//地址
    private double longitude;//经
    private double latitude;//纬
    private String contact;//紧急联系人
    private String contact_tel;//紧急联系人电话
    private String shop_area;//门店面积
    private String shop_info;//门店详情
    private String sell_no;//销售编码

    public AddShopApi(ShopInfo shop) {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        this.user_id = userInfo.getUserID();

        this.shop_name = shop.getShop_name();//门店名称
        this.shop_type_id = shop.getShop_type_id();//所属行业    选中
        this.city_id = shop.getCity_id();//市
        this.area_id = shop.getArea_id();//area_id
        this.address = shop.getAddress();//地址
        this.longitude = shop.getLongitude();//经
        this.latitude = shop.getLatitude();//纬
        this.contact = shop.getContact();//紧急联系人
        this.contact_tel = shop.getContact_tel();//紧急联系人电话
        this.shop_area = shop.getShop_area();//门店面积
        this.shop_info = shop.getShop_info();//门店详情
        this.sell_no = shop.getSell_no();//销售编码
    }
}