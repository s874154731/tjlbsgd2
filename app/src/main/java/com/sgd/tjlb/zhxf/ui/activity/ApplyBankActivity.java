package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.BankInfo;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.http.api.FindBankListByAreaIDApi;
import com.sgd.tjlb.zhxf.http.api.GetShopApi;
import com.sgd.tjlb.zhxf.http.api.SaveBankRecordApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.ApplyBankAdapter;
import com.youth.banner.util.LogUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 申请银行卡
 */
public class ApplyBankActivity extends AppActivity {

    private SettingBar mSbBtnBankApplybank;
    private RecyclerView mRvBank;

    private ApplyBankAdapter mAdapter;

    private ShopInfo mShopInfo;

    public static void start(Activity context) {
        Intent starter = new Intent(context, ApplyBankActivity.class);
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_bank;
    }

    @Override
    public void onLeftClick(View view) {
        super.onLeftClick(view);
        setResult(RESULT_OK);
    }

    @Override
    protected void initView() {

        mSbBtnBankApplybank = (SettingBar) findViewById(R.id.sb_btn_bank_applybank);
        mRvBank = (RecyclerView) findViewById(R.id.rv_bank);

        initListener();

        initRecyclerView();

    }

    private void initListener() {
        //申请银行
        mSbBtnBankApplybank.setOnClickListener(view ->
                ApplyForBankActivity.start(ApplyBankActivity.this,null)
        );

    }

    private void initRecyclerView() {
        mAdapter = new ApplyBankAdapter(this);
        mRvBank.setLayoutManager(new LinearLayoutManager(this));
        mRvBank.setAdapter(mAdapter);

        mAdapter.setmCallBack(info -> {
            if (info == null)
                return;

            saveBankRecord(info);
            BrowserActivity.start(getContext(), info.getBank_url());
        });
    }

    @Override
    protected void initData() {
        findShopInfo();
    }

    //获取店铺信息
    private void findShopInfo() {
        EasyHttp.post(this)
                .api(new GetShopApi())
                .request(new HttpCallback<HttpData<ShopInfo>>(this) {

                    @Override
                    public void onSucceed(HttpData<ShopInfo> data) {
                        if (data.getData() != null) {
                            mShopInfo = data.getData();
                        }
                        finishBankList();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        finishBankList();
                    }
                });
    }

    //获取银行卡列表
    private void finishBankList() {
        if (mShopInfo == null)
            return;

        EasyHttp.post(this)
                .api(new FindBankListByAreaIDApi()
                        .setAreaId(mShopInfo.getArea_id())
                )
                .request(new HttpCallback<HttpData<List<BankInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<BankInfo>> data) {
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

    //保存银行卡记录
    private void saveBankRecord(BankInfo info) {
        EasyHttp.post(this)
                .api(new SaveBankRecordApi()
                        .setBankID(info.getBank_id())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        LogUtils.d(">>>>> 申请银行卡成功");
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        LogUtils.e(e.toString());
                    }
                });
    }


}
