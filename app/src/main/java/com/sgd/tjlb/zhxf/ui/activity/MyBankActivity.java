package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.BankInfo;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.FindBankListByAreaIDApi;
import com.sgd.tjlb.zhxf.http.api.FindMyBankListApi;
import com.sgd.tjlb.zhxf.http.api.GetShopApi;
import com.sgd.tjlb.zhxf.http.api.SaveBankRecordApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.ApplyBankAdapter;
import com.sgd.tjlb.zhxf.ui.adapter.MyBankAdapter;
import com.youth.banner.util.LogUtils;

import java.util.List;

/**
 * 我的银行  activity
 */
public class MyBankActivity extends AppActivity {

    private SettingBar mSbBtnBankApplybank;
    private RecyclerView mRvBank;
    private RecyclerView mRvBankApply;

    private MyBankAdapter mAdapter;
    private ApplyBankAdapter mAdapterApply;//申请

    private ShopInfo mShopInfo;

    public static void start(Context context) {
        Intent starter = new Intent(context, MyBankActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mybank;
    }

    @Override
    protected void initView() {

        mSbBtnBankApplybank = (SettingBar) findViewById(R.id.sb_btn_bank_applybank);
        mRvBank = (RecyclerView) findViewById(R.id.rv_bank);
        mRvBankApply = (RecyclerView) findViewById(R.id.rv_apply_bank);

        initListener();

        initRecyclerView();

    }

    @Override
    public void onRightClick(View view) {
        super.onRightClick(view);
        ApplyBankActivity.start(this);
    }

    private void initListener() {
        //申请银行
        mSbBtnBankApplybank.setOnClickListener(view ->
                ApplyForBankActivity.start(this, null)
        );
    }

    private void initRecyclerView() {
        //申请
        mAdapterApply = new ApplyBankAdapter(this);
        mRvBankApply.setLayoutManager(new LinearLayoutManager(this));
        mRvBankApply.setAdapter(mAdapterApply);

        mAdapterApply.setmCallBack(info -> {
            if (info == null)
                return;
            saveBankRecord(info);
            BrowserActivity.start(getContext(), info.getBank_url());
        });

        //我的
        mAdapter = new MyBankAdapter(this);
        mRvBank.setLayoutManager(new LinearLayoutManager(this));
        mRvBank.setAdapter(mAdapter);

        mAdapter.setmCallBack(info -> {
            if (info == null)
                return;
            ApplyForBankActivity.start(this, info);
        });
    }

    @Override
    protected void initData() {
        findBankList();
//        findShopInfo();
        findApplyBankList();
    }

    //获取店铺信息
    private void findShopInfo() {
        EasyHttp.post(this)
                .api(new GetShopApi())
                .request(new HttpCallback<HttpData<ShopInfo>>(this) {

                    @Override
                    public void onStart(Call call) {
                    }

                    @Override
                    public void onSucceed(HttpData<ShopInfo> data) {
                        if (data.getData() != null) {
                            mShopInfo = data.getData();
                        }
                        findApplyBankList();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        findApplyBankList();
                    }
                });
    }

    //获取银行卡列表申请
    private void findApplyBankList() {
        UserInfo userInfo = MMKVHelper.getInstance().getUserInfo();
        if (userInfo.getArea_id() == -1) {
            mRvBankApply.setVisibility(View.GONE);
            return;
        }
        mRvBankApply.setVisibility(View.VISIBLE);

        EasyHttp.post(this)
                .api(new FindBankListByAreaIDApi()
                        .setAreaId(userInfo.getArea_id())
                )
                .request(new HttpCallback<HttpData<List<BankInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<BankInfo>> data) {
                        if (data.getData() != null) {
                            mAdapterApply.initData(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取银行卡列表
    private void findBankList() {
        EasyHttp.post(this)
                .api(new FindMyBankListApi())
                .request(new HttpCallback<HttpData<List<BankInfo>>>(this) {

                    @Override
                    public void onStart(Call call) {

                    }

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
