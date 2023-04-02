package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 宣传消防列表
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PopularizeListApi implements IRequestApi {

    private int news_type_id;//分类ID
    private int page;//页码

    @Override
    public String getApi() {
        return "re_news_list";
    }

    public PopularizeListApi setNewsTypeId(int news_type_id) {
        this.news_type_id = news_type_id;
        return this;
    }

    public PopularizeListApi setPage(int page) {
        this.page = page;
        return this;
    }
}
