package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordData;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordBean;
import com.sgd.tjlb.zhxf.http.api.MyConstructionRecordListApi;
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
        mAdapter.setmCallBack(data -> {
            if (data != null) {
                WorkRecordActivity.start(ConstructionRecordActivity.this, data.getDevice_work_id());
            }
        });
    }

    @Override
    protected void initData() {
        List<ConstructionRecordBean> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ConstructionRecordBean recordData = new ConstructionRecordBean();
            recordData.setDevice_id("" + i + 1);
            recordData.setAddress("地址" + i);
            recordData.setShop_name("店铺名称" + i);
            recordData.setCreate_time("2023-01-" + i);
            dataList.add(recordData);
        }
        mAdapter.initData(dataList);
        findMyWarrantyRecordList();
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
//                            mAdapter.initData(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
