package com.sgd.tjlb.zhxf.ui.activity;

import androidx.core.content.ContextCompat;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.action.StatusAction;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.ui.dialog.MenuDialog;
import com.sgd.tjlb.zhxf.widget.StatusLayout;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/04/17
 *    desc   : 加载使用案例
 */
public final class StatusActivity extends AppActivity
        implements StatusAction {

    private StatusLayout mStatusLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.status_activity;
    }

    @Override
    protected void initView() {
        mStatusLayout = findViewById(R.id.hl_status_hint);
    }

    @Override
    protected void initData() {
        new MenuDialog.Builder(this)
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList("加载中", "请求错误", "空数据提示", "自定义提示")
                .setListener((dialog, position, object) -> {
                    switch (position) {
                        case 0:
                            showLoading();
                            postDelayed(this::showComplete, 2500);
                            break;
                        case 1:
                            showError(listener -> {
                                showLoading();
                                postDelayed(this::showEmpty, 2500);
                            });
                            break;
                        case 2:
                            showEmpty();
                            break;
                        case 3:
                            showLayout(ContextCompat.getDrawable(getActivity(), R.drawable.status_order_ic), "暂无订单", null);
                            break;
                        default:
                            break;
                    }
                })
                .show();
    }

    @Override
    public StatusLayout getStatusLayout() {
        return mStatusLayout;
    }
}