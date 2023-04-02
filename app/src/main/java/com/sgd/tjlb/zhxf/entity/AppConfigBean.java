package com.sgd.tjlb.zhxf.entity;

import java.util.List;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.entity
 * @ClassName: AppConfigBean
 * @Description: app常量
 * @CreateDate: 2023/3/27/027 14:06
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 14:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AppConfigBean {

    private String app_tel;//客服电话
    private String app_imgup;//上传图片地址
    private String app_info;//关于我们
    private String app_agree;//隐私协议
    private List<HomeBannerData> app_slide;//首页幻灯片

    public String getApp_tel() {
        return app_tel;
    }

    public void setApp_tel(String app_tel) {
        this.app_tel = app_tel;
    }

    public String getApp_imgup() {
        return app_imgup;
    }

    public void setApp_imgup(String app_imgup) {
        this.app_imgup = app_imgup;
    }

    public String getApp_info() {
        return app_info;
    }

    public void setApp_info(String app_info) {
        this.app_info = app_info;
    }

    public String getApp_agree() {
        return app_agree;
    }

    public void setApp_agree(String app_agree) {
        this.app_agree = app_agree;
    }

    public List<HomeBannerData> getApp_slide() {
        return app_slide;
    }

    public void setApp_slide(List<HomeBannerData> app_slide) {
        this.app_slide = app_slide;
    }
}
