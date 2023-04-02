package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 宣传消防分类
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PopularizeTypeApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_news_type_list";
    }

}
