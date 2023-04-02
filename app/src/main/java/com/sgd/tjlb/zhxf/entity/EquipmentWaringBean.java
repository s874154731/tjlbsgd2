package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

/***
 * 设备警报
 */
public class EquipmentWaringBean {

   private String alarm_msg;//警报内容
   private String create_time;//警报内容

   public String getAlarm_msg() {
      return TextUtils.isEmpty(alarm_msg) ? "未知警报" : alarm_msg;
   }

   public void setAlarm_msg(String alarm_msg) {
      this.alarm_msg = alarm_msg;
   }

   public String getCreate_time() {
      return TextUtils.isEmpty(create_time) ? "1900-01-01 00:00:00" : create_time;
   }

   public void setCreate_time(String create_time) {
      this.create_time = create_time;
   }
}
