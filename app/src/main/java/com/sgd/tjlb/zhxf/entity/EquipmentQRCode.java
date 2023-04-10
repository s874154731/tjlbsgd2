package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

public class EquipmentQRCode {

//   ID:2207220C023F IMEI:867405045278903

   private String ID;
   private String IMEI;

   public String getID() {
      return TextUtils.isEmpty(ID) ? "" : ID;
   }

   public void setID(String ID) {
      this.ID = ID;
   }

   public String getIMEI() {
      return IMEI;
   }

   public void setIMEI(String IMEI) {
      this.IMEI = IMEI;
   }
}
