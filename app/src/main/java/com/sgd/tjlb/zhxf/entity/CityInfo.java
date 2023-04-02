package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

//地区信息
public class CityInfo implements IPickerViewData {
   private String title;//名称
   private int value;//编码
   private List<CityInfo> cities;//

   public String getTitle() {
      return TextUtils.isEmpty(title) ? "" : title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }

   public List<CityInfo> getCities() {
      return cities;
   }

   public void setCities(List<CityInfo> cities) {
      this.cities = cities;
   }

   @Override
   public String getPickerViewText() {
      return getTitle();
   }
}
