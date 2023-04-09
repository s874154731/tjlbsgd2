package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordData;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.http.api.MyWarrantyRecordListApi;
import com.sgd.tjlb.zhxf.http.api.WarrantyListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.ConstructionRecordAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 施单记录 我的施工单 -- activity
 */
public class ConstructionRecordActivity extends AppActivity {

    private RecyclerView mRvConstructionRecord;

    private ConstructionRecordAdapter mAdapter;

    private List<ConstructionRecordData> dataList = new ArrayList<>();
    private int mPage = ConstantUtil.PAGE_INDEX;

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

        initRecycler();

    }

    private void initRecycler() {
        mAdapter = new ConstructionRecordAdapter(this);
        mRvConstructionRecord.setLayoutManager(new LinearLayoutManager(this));
        mRvConstructionRecord.setAdapter(mAdapter);
        mAdapter.setmCallBack(new ConstructionRecordAdapter.CallBack() {
            @Override
            public void onItemClick(ConstructionRecordData data) {
                WorkRecordActivity.start(ConstructionRecordActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 10; i++) {
            ConstructionRecordData recordData = new ConstructionRecordData();
            recordData.setAddress("地址" + i);
            recordData.setName("店铺名称" + i);
            recordData.setTime("2023-01-" + i);
            dataList.add(recordData);
        }
        mAdapter.initData(dataList);

        findMyWarrantyRecordList();
    }

    //获取我的施工单记录
    private void findMyWarrantyRecordList() {
        EasyHttp.post(this)
                .api(new MyWarrantyRecordListApi()
                        .setPage(mPage)
                )
                .request(new HttpCallback<HttpData<List<ShopInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<ShopInfo>> data) {
                        if (data.getData() != null) {


                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
