package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.CheckNet;
import com.sgd.tjlb.zhxf.aop.Log;
import com.sgd.tjlb.zhxf.app.AppActivity;

/**
 * 通知消息-详情
 */
public class NoticeDetailActivity extends AppActivity {

    private static final String INTENT_KEY_IN_URL = "url";

    private TextView tv_msg;

    @CheckNet
    @Log
    public static void start(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(context, NoticeDetailActivity.class);
        intent.putExtra(INTENT_KEY_IN_URL, url);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView() {
        tv_msg = findViewById(R.id.tv_msg);
    }

    @Override
    protected void initData() {
        String msg = getIntent().getStringExtra(INTENT_KEY_IN_URL);
        tv_msg.setText(TextUtils.isEmpty(msg) ? "未知消息" : msg);
    }
}
