package com.sgd.tjlb.zhxf.http.api;

import com.hjq.http.config.IRequestApi;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.http.api
 * @ClassName: AppVersionApi
 * @Description: 版本信息
 * @CreateDate: 2023/3/27/027 13:54
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/27/027 13:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AppVersionApi implements IRequestApi {
    @Override
    public String getApi() {
        return "re_app_version";
    }

    public final class Bean {
        public int version_id;//版本号id
        public String version_name;//版本名称
        public String version_no;//版本号
        public String version_url;//下载地址
    }
}
