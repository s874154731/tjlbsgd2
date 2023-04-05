package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordData;
import com.sgd.tjlb.zhxf.ui.adapter.ConstructionRecordAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 施单记录
 */
public class ConstructionRecordActivity extends AppActivity {

    private RecyclerView mRvConstructionRecord;

    private ConstructionRecordAdapter mAdapter;

    private List<ConstructionRecordData> dataList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, ConstructionRecordActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
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
    }

}
