package com.sgd.tjlb.zhxf.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatButton;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.ClearEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.BankInfo;
import com.sgd.tjlb.zhxf.http.api.SaveMyBankInfoApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.InputTextManager;

public class ApplyForBankActivity extends AppActivity {

    private static final String KEY_BEAN = "KEY_BEAN";

    private ClearEditText mEtBankName;//银行名称
    private ClearEditText mEtOpenBankName;//开户行名称
    private ClearEditText mEtBankNo;//银行卡号
    private ClearEditText mEtOpenAddress;//开户地址

    private AppCompatButton mBtnApply;

    private BankInfo mBankInfo;

    /**
     * 启动
     *
     * @param context  上下文
     * @param bankInfo 银行卡信息
     */
    public static void start(Activity context, BankInfo bankInfo) {
        Intent starter = new Intent(context, ApplyForBankActivity.class);
        starter.putExtra(KEY_BEAN, bankInfo);
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_applyforbank;
    }

    @Override
    protected void initView() {

        mEtBankNo = (ClearEditText) findViewById(R.id.et_applyforbank_cardnumber);
        mEtOpenAddress = (ClearEditText) findViewById(R.id.et_applyforbank_open_account_address);
        mEtBankName = (ClearEditText) findViewById(R.id.et_applyforbank_bank);
        mEtOpenBankName = (ClearEditText) findViewById(R.id.et_applyforbank_open_account_name);
        mBtnApply = (AppCompatButton) findViewById(R.id.btn_applyforbank_apply);

        InputTextManager.with(this)
                .addView(mEtBankName)
                .addView(mEtOpenBankName)
                .addView(mEtBankNo)
                .addView(mEtOpenAddress)
                .setMain(mBtnApply)
                .build();

        initListener();
    }

    private void initListener() {
        mBtnApply.setOnClickListener(view -> {
            judgeSubmit();
        });
    }

    private void judgeSubmit() {
        if (TextUtils.isEmpty(mEtBankName.getText().toString())) {
            mEtBankName.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_tip_produce_bank));
            return;
        }

        if (TextUtils.isEmpty(mEtOpenBankName.getText().toString())) {
            mEtOpenBankName.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_tip_open_account_name));
            return;
        }

        if (TextUtils.isEmpty(mEtBankNo.getText().toString())) {
            mEtBankNo.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_tip_produce_cardnumber));
            return;
        }
        if (TextUtils.isEmpty(mEtOpenAddress.getText().toString())) {
            mEtOpenAddress.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_tip_open_account_bank_address_name));
            return;
        }

        go2Submit();

    }

    //提交银行信息
    private void go2Submit() {

        String bankID = mBankInfo != null ? mBankInfo.getBank_user_id() : "";

        EasyHttp.post(this)
                .api(new SaveMyBankInfoApi()
                        .setBank_user_id(bankID)
                        .setBank_name(mEtBankName.getText().toString().trim())
                        .setBank_user(mEtOpenBankName.getText().toString().trim())
                        .setBank_no(mEtBankNo.getText().toString().trim())
                        .setBank_address(mEtOpenAddress.getText().toString().trim())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        toast("添加成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    protected void initData() {
        mBankInfo = (BankInfo) getIntent().getSerializableExtra(KEY_BEAN);

        refreshUI();
    }

    private void refreshUI() {
        if (mBankInfo != null) {
            mEtBankNo.setText(mBankInfo.getBank_no());
            mEtOpenAddress.setText(mBankInfo.getBank_address());
            mEtBankName.setText(mBankInfo.getBankName());
            mEtOpenBankName.setText(mBankInfo.getBank_user());
        }
    }
}
