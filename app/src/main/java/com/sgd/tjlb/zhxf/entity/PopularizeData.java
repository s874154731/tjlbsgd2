package com.sgd.tjlb.zhxf.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 消防宣传bean
 */
public class PopularizeData {
   @SerializedName("news_id")
   private int id;//新闻id
   @SerializedName("news_title")
   private String title;// 新闻标题
   private String news_tags;// 新闻标签
   private int is_video;//是否为视频  0为新闻，1为视频 加上视频标
   private String content;
   @SerializedName("news_img")
   private String imgUrl;//新闻图片
   @SerializedName("news_url")
   private String htmlUrl;//// 跳转地址
   @SerializedName("create_time")
   private String time;//

   private String videoUrl;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getImgUrl() {
      return imgUrl;
   }

   public void setImgUrl(String imgUrl) {
      this.imgUrl = imgUrl;
   }

   public String getHtmlUrl() {
      return htmlUrl;
   }

   public void setHtmlUrl(String htmlUrl) {
      this.htmlUrl = htmlUrl;
   }

   public String getVideoUrl() {
      return videoUrl;
   }

   public void setVideoUrl(String videoUrl) {
      this.videoUrl = videoUrl;
   }

   public String getTime() {
      return time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public boolean isVideo() {
      return is_video == 1;
   }

   public void setVideo(int video) {
      is_video = video;
   }
}
