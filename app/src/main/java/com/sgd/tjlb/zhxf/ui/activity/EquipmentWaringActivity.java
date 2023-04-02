package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.CheckNet;
import com.sgd.tjlb.zhxf.aop.Log;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.EquipmentWaringBean;
import com.sgd.tjlb.zhxf.http.api.EquipmentWaringListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.EquipmentWaringAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 我的设备--警报信息页面
 */
public class EquipmentWaringActivity extends AppActivity {

    private static final String INTENT_KEY_PARAME = "INTENT_KEY_PARAME";

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private EquipmentWaringAdapter mAdapter;

    private int mPage = ConstantUtil.PAGE_INDEX;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;

    private String mDeviceSn;

    @CheckNet
    @Log
    public static void start(Context context, String dv_sn) {
        Intent intent = new Intent(context, EquipmentWaringActivity.class);
        intent.putExtra(INTENT_KEY_PARAME, dv_sn);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_equipment_waring;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.rv_eq_waring);
        mRefreshLayout = findViewById(R.id.srl_eq_waring);

        mDeviceSn = getIntent().getStringExtra(INTENT_KEY_PARAME);

        initSmartRefreshLayout();
        initRecyclerView();
        initListener();
    }

    private void initSmartRefreshLayout() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
                mPage++;
                mRefreshType = ConstantUtil.REFRESH_MORE;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下滑
                mPage = 1;
                mRefreshType = ConstantUtil.REFRESH_FIRST;
                initData();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new EquipmentWaringAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {

    }

    @Override
    protected void initData() {
        findEquipmentWaringInfos();
    }

    //获取设备警报列表
    private void findEquipmentWaringInfos() {
        EasyHttp.post(this)
                .api(new EquipmentWaringListApi()
                        .setDevice_sn(mDeviceSn)
                        .setPage(mPage)
                )
                .request(new HttpCallback<HttpData<List<EquipmentWaringBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<EquipmentWaringBean>> data) {
                        if (data.getData() != null) {
                            mAdapter.initData(data.getData());
                            //关闭刷新
                            SmartRefreshLayoutUtil.complete(mRefreshLayout);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        //关闭刷新
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                    }
                });
    }
}
