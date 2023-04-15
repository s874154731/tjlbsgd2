package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordBean;
import com.sgd.tjlb.zhxf.http.api.AddDeviceMaintenanceRecordApi;
import com.sgd.tjlb.zhxf.http.api.MyConstructionRecordListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.ConstructionRecordAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.List;

/**
 * 施单记录 我的施工单 -- activity
 */
public class ConstructionRecordActivity extends AppActivity {

    private RecyclerView mRvConstructionRecord;
    private SmartRefreshLayout mRefreshLayout;

    private ConstructionRecordAdapter mAdapter;

    private int mPage = ConstantUtil.PAGE_INDEX;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;

    public static void start(Activity context) {
        Intent starter = new Intent(context, ConstructionRecordActivity.class);
//        starter.putExtra();
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_construction_record;
    }

    @Override
    protected void initView() {

        mRvConstructionRecord = (RecyclerView) findViewById(R.id.rv_construction_record);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_construction_record);

        initSmartRefreshLayout();
        initRecycler();

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

    private void initRecycler() {
        mAdapter = new ConstructionRecordAdapter(this);
        mRvConstructionRecord.setLayoutManager(new LinearLayoutManager(this));
        mRvConstructionRecord.setAdapter(mAdapter);
        mAdapter.setmCallBack(data -> {
            if (data != null) {
                WorkRecordActivity.start(ConstructionRecordActivity.this, data.getDevice_work_id());
            }
        });
    }

    @Override
    protected void initData() {
        /*//添加安装维修记录
        new UpdateWorkRecordDialog.Builder(ConstructionRecordActivity.this)
                .setData(null)
                .setBaseActivity(ConstructionRecordActivity.this)
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new UpdateWorkRecordDialog.OnListener() {
                    @Override
                    public void onSubmit(BaseDialog dialog, ConstructionRecordBean data) {
                        addDeviceMaintenance(dialog, data);
                    }
                })
                .show();*/
        //清空用户信息
        findMyWarrantyRecordList();
    }

    private void addDeviceMaintenance(BaseDialog dialog, ConstructionRecordBean data) {
        EasyHttp.post(this)
                .api(new AddDeviceMaintenanceRecordApi()
                        .setDevice_id("7")
                        .setStatus_img("https://upload-bbs.miyoushe.com/upload/2023/04/11/282500742/7d6eb3e2ad0cd6cb95084a37aed80053_7018267278107824332.jpg")
                        .setStatus_info(data.getStatus_info())
                        .setStatus(data.getStatus())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        ToastUtils.show("已添加");
                        dialog.dismiss();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取我的施工单记录
    private void findMyWarrantyRecordList() {
        EasyHttp.post(this)
                .api(new MyConstructionRecordListApi()
                        .setPage(mPage)
                )
                .request(new HttpCallback<HttpData<List<ConstructionRecordBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<ConstructionRecordBean>> data) {
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
