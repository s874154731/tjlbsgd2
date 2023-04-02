package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

/**
 * 施工记录
 */
public class ConstructionRecordBean {
   private String status_img;//施工图片
   private String status_info;//施工内容
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
}
