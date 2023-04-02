package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

/**
 * 门店行业bean
 */
public class ShopTypeInfo {

   private int shop_type_id;//行业ID
   private String shop_type_name;//行业名称

   public int getShop_type_id() {
      return shop_type_id;
   }

   public void setShop_type_id(int shop_type_id) {
      this.shop_type_id = shop_type_id;
   }

   public String getShop_type_name() {
      return TextUtils.isEmpty(shop_type_name) ? "未知行业" : shop_type_name;
   }

   public void setShop_type_name(String shop_type_name) {
      this.shop_type_name = shop_type_name;
   }
}
