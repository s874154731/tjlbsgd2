package com.sgd.tjlb.zhxf.entity;

import java.math.BigDecimal;

/**
 * 合同管理
 */
public class AgreementInfo {

   private String contract;//合同内容
   private int product_id;//产品ID
   private String product_title;//产品标题
   private String product_img;//产品图片
   private BigDecimal product_price;//产品价格
   private String remarks;//产品备注

   public String getContract() {
      return contract;
   }

   public void setContract(String contract) {
      this.contract = contract;
   }

   public int getProduct_id() {
      return product_id;
   }

   public void setProduct_id(int product_id) {
      this.product_id = product_id;
   }

   public String getProduct_title() {
      return product_title;
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
      return product_price;
   }

   public void setProduct_price(BigDecimal product_price) {
      this.product_price = product_price;
   }

   public String getRemarks() {
      return remarks;
   }

   public void setRemarks(String remarks) {
      this.remarks = remarks;
   }
}
