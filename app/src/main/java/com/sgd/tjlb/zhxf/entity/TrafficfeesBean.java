package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 流量费
 */
public class TrafficfeesBean {

   private int product_id;//产品ID
   private String product_title;//产品标题
   private String product_img;//产品图片
   private BigDecimal product_price;//产品价格1年
   private BigDecimal product_price2;//产品价格2年
   private BigDecimal product_price3;//产品价格3年
   private String remarks;//产品备注

   public int getProduct_id() {
      return product_id;
   }

   public void setProduct_id(int product_id) {
      this.product_id = product_id;
   }

   public String getProduct_title() {
      return TextUtils.isEmpty(product_title) ? "未知产品" : product_title;
   }

   public void setProduct_title(String product_title) {
      this.product_title = product_title;
   }

   public String getProduct_img() {
      return product_img;
   }

   public void setProduct_img(String product_img) {
      this.product_img = product_img;
   }

   public BigDecimal getProduct_price() {
      return product_price == null ? new BigDecimal(0) : product_price;
   }

   public void setProduct_price(BigDecimal product_price) {
      this.product_price = product_price;
   }

   public BigDecimal getProduct_price2() {
      return product_price2 == null ? new BigDecimal(0) : product_price2;
   }

   public void setProduct_price2(BigDecimal product_price2) {
      this.product_price2 = product_price2;
   }

   public BigDecimal getProduct_price3() {
      return product_price3 == null ? new BigDecimal(0) : product_price3;
   }

   public void setProduct_price3(BigDecimal product_price3) {
      this.product_price3 = product_price3;
   }

   public String getRemarks() {
      return remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }
}
