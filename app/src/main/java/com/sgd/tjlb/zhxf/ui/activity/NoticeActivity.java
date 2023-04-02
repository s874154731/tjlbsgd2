package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.CheckNet;
import com.sgd.tjlb.zhxf.aop.Log;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;
import com.sgd.tjlb.zhxf.http.api.HomeMsgsApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.NoticeAdapter;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通知消息
 */
public class NoticeActivity extends AppActivity {

    private static final String INTENT_KEY_IN_URL = "url";

    private RecyclerView mRecyclerView;

    private NoticeAdapter mAdapter;

    @CheckNet
    @Log
    public static void start(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent(context, NoticeActivity.class);
        intent.putExtra(INTENT_KEY_IN_URL, url);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_msg);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new NoticeAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setmCallBack(bean -> {
            String msg = bean.getMsg_info();
            NoticeDetailActivity.start(getContext(), msg);
        });
    }

    @Override
    protected void initData() {
        findMessageData();
    }

    /**
     * 获取消息内容
     */
    private void findMessageData() {
        EasyHttp.post(this)
                .api(new HomeMsgsApi()
                        .setPage(1)
                )
                .request(new HttpCallback<HttpData<List<HomeMsgBean>>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<List<HomeMsgBean>> data) {
                        if (data.getData() != null) {

                            mAdapter.initData(data.getData());

                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }
}
