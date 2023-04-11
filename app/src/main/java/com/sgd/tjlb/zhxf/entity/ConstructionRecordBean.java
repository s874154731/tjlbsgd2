package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

/**
 * 施工记录
 */
public class ConstructionRecordBean {

   private String device_work_id;//记录ID
   private String device_id;//设备ID
   private String status_img;//施工图片
   private String status_info;//描述
   private int status;//0成功，2安装异常，4维修异常
   private String shop_name;//地址名称
   private String address;//门店地址
   private String create_time;//时间

   public String getStatus_img() {
      return status_img;
   }

   public void setStatus_img(String status_img) {
      this.status_img = status_img;
   }

   public String getStatus_info() {
      return TextUtils.isEmpty(status_info) ? "" : status_info;
   }

   public void setStatus_info(String status_info) {
      this.status_info = status_info;
   }

   public String getCreate_time() {
      return create_time;
   }

   public void setCreate_time(String create_time) {
      this.create_time = create_time;
   }

   public String getDevice_work_id() {
      return device_work_id;
   }

   public void setDevice_work_id(String device_work_id) {
      this.device_work_id = device_work_id;
   }

   public String getDevice_id() {
      return device_id;
   }

   public void setDevice_id(String device_id) {
      this.device_id = device_id;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public String getShop_name() {
      return shop_name;
   }

   public void setShop_name(String shop_name) {
      this.shop_name = shop_name;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }
}
