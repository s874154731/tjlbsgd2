package com.sgd.tjlb.zhxf.utils;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * @ProjectName: JuXin2.0
 * @Package: com.duanfeng.assemblechat.utils
 * @ClassName: SmartRefreshLayoutUtil
 * @Description: 刷新组件工具类
 * @CreateDate: 2022/3/18/018 21:45
 * @UpdateUser: shi
 * @UpdateDate: 2022/3/18/018 21:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SmartRefreshLayoutUtil {

    /**
     * 结束刷新动画
     * @param refreshLayout 刷新控件
     */
    public static void complete(SmartRefreshLayout refreshLayout){
        if (refreshLayout != null){
            if (refreshLayout.isRefreshing())
                refreshLayout.finishRefresh();
            if (refreshLayout.isLoading())
                refreshLayout.finishLoadMore();
        }
    }
}
