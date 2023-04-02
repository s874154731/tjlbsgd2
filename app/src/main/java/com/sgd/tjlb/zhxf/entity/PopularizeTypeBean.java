package com.sgd.tjlb.zhxf.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.entity
 * @ClassName: PopularizeTypeBean
 * @Description: 消防宣传type
 * @CreateDate: 2023/3/27/027 13:43
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PopularizeTypeBean implements Serializable {

    private int news_type_id;//分类ID
    private String news_type_name;//

    public int getNews_type_id() {
        return news_type_id;
    }

    public void setNews_type_id(int news_type_id) {
        this.news_type_id = news_type_id;
    }

    public String getNews_type_name() {
        return TextUtils.isEmpty(news_type_name) ? "未知类型" : news_type_name;
    }

    public void setNews_type_name(String news_type_name) {
        this.news_type_name = news_type_name;
    }
}
