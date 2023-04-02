package com.sgd.tjlb.zhxf.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺信息
 */
public class ShopInfo3 {

   private int id;
   private String name;
   private String imgUrl;
   private String address;
   private float longitude;//经度
   private float latitude;//维度

   private List<EquipmentInfo> equipmentDatas;//设备信息

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getImgUrl() {
      return imgUrl;
   }

   public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public float getLongitude() {
      return longitude;
   }

   public void setLongitude(float longitude) {
      this.longitude = longitude;
   }

   public float getLatitude() {
      return latitude;
   }

   public void setLatitude(float latitude) {
      this.latitude = latitude;
   }

   public List<EquipmentInfo> getEquipmentDatas() {
      return equipmentDatas;
   }

   public void setEquipmentDatas(List<EquipmentInfo> equipmentDatas) {
      this.equipmentDatas = equipmentDatas;
   }

   //模拟数据添加设备
   public void addEquipmentInfo(EquipmentInfo equipmentInfo){
      if (equipmentDatas == null)
         equipmentDatas = new ArrayList<>();
      equipmentDatas.add(equipmentInfo);
   }
}
