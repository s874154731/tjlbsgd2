package com.sgd.tjlb.zhxf.entity;

/**
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.entity
 * @ClassName: VersionInfoBean
 * @Description: 版本信息
 * @CreateDate: 2023/3/28/028 11:10
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/28/028 11:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class VersionInfoBean {

    private int version_id;//版本号id
    private String version_name;//版本名称
    private String version_no;//版本号
    private String version_url;//下载地址

    public int getVersion_id() {
        return version_id;
    }

    public void setVersion_id(int version_id) {
        this.version_id = version_id;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }
}
