package com.sgd.tjlb.zhxf.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 首页banner 数据
 */
public class HomeBannerData {

    private String title;
    private int resourcesId;
    @SerializedName("imgurl")
    private String imageUrl;
    private String htmlUrl;

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public int getResourcesId() {
      return resourcesId;
   }

   public void setResourcesId(int resourcesId) {
      this.resourcesId = resourcesId;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public String getHtmlUrl() {
      return htmlUrl;
   }

   public void setHtmlUrl(String htmlUrl) {
      this.htmlUrl = htmlUrl;
   }
}
