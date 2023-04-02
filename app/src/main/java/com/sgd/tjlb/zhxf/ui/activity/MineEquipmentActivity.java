package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.CheckNet;
import com.sgd.tjlb.zhxf.aop.Log;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.http.api.EquipmentInfoListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.EquipmentAdapter;

import java.util.List;

/**
 * 我的设备页面
 */
public class MineEquipmentActivity extends AppActivity {

    private static final String INTENT_KEY_IN_URL = "url";

    private RecyclerView rvEquipment;

    private EquipmentAdapter mAdapter;

    @CheckNet
    @Log
    public static void start(Context context) {
        Intent intent = new Intent(context, MineEquipmentActivity.class);
//        if (!(context instanceof Activity)) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_equipment;
    }

    @Override
    protected void initView() {
        rvEquipment = findViewById(R.id.rv_mine_equipment);

        initListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new EquipmentAdapter(this, true);
        rvEquipment.setLayoutManager(new LinearLayoutManager(this));
        rvEquipment.setAdapter(mAdapter);
    }

    private void initListener() {

    }

    @Override
    protected void initData() {
        findEquipmentInfos();
    }

    //获取设备列表
    private void findEquipmentInfos() {
        EasyHttp.post(this)
                .api(new EquipmentInfoListApi())
                .request(new HttpCallback<HttpData<List<EquipmentInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<EquipmentInfo>> data) {
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
