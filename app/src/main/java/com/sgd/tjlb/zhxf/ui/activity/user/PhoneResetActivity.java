package com.sgd.tjlb.zhxf.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.widget.view.PasswordEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.Log;
import com.sgd.tjlb.zhxf.aop.SingleClick;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.SendCodeApi;
import com.sgd.tjlb.zhxf.http.api.UpdatePhoneApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.InputTextManager;
import com.sgd.tjlb.zhxf.ui.dialog.TipsDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.CountdownView;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/04/20
 * desc   : 设置手机号
 */
public final class PhoneResetActivity extends AppActivity
        implements TextView.OnEditorActionListener {

    private static final String INTENT_KEY_IN_CODE = "code";

    @Log
    public static void start(Activity context, String code) {
        Intent intent = new Intent(context, PhoneResetActivity.class);
        intent.putExtra(INTENT_KEY_IN_CODE, code);
        context.startActivityForResult(intent, 0);
    }


    private EditText mPhoneView;
    private EditText mCodeView;
    private CountdownView mCountdownView;
    private Button mCommitView;
    private PasswordEditText mLoginPwdView;

    @Override
    protected int getLayoutId() {
        return R.layout.phone_reset_activity;
    }

    @Override
    protected void initView() {
        mPhoneView = findViewById(R.id.et_phone_reset_phone);
        mCodeView = findViewById(R.id.et_phone_reset_code);
        mCountdownView = findViewById(R.id.cv_phone_reset_countdown);
        mCommitView = findViewById(R.id.btn_phone_reset_commit);
        mLoginPwdView = findViewById(R.id.et_phone_reset_loginpassword);

        setOnClickListener(mCountdownView, mCommitView);

        mCodeView.setOnEditorActionListener(this);

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .addView(mLoginPwdView)
                .setMain(mCommitView)
                .build();
    }

    @Override
    protected void initData() {
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCountdownView) {

            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                toast(R.string.common_phone_input_error);
                return;
            }

            /*if (true) {
                toast(R.string.common_code_send_hint);
                mCountdownView.start();
                return;
            }*/

            // 获取验证码
            EasyHttp.post(this)
                    .api(new SendCodeApi()
                            .setPhone(mPhoneView.getText().toString())
                            .setType(SendCodeApi.TYPE_CHANGEE_TEL))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            toast(R.string.common_code_send_hint);
                            mCountdownView.start();
                        }
                    });
        } else if (view == mCommitView) {

            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                toast(R.string.common_phone_input_error);
                return;
            }

            if (mCodeView.getText().toString().length() != getResources().getInteger(R.integer.sms_code_length)) {
                ToastUtils.show(R.string.common_code_error_hint);
                return;
            }

            // 隐藏软键盘
            hideKeyboard(getCurrentFocus());

            /*if (true) {
                new TipsDialog.Builder(this)
                        .setIcon(TipsDialog.ICON_FINISH)
                        .setMessage(R.string.phone_reset_commit_succeed)
                        .setDuration(2000)
                        .addOnDismissListener(dialog -> finish())
                        .show();
                return;
            }*/

            // 更换手机号
            EasyHttp.post(this)
                    .api(new UpdatePhoneApi()
                            .setPwd(mLoginPwdView.getText().toString())
                            .setTel(mPhoneView.getText().toString())
                            .setCode(mCodeView.getText().toString()))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {

                            UserInfo self = MMKVHelper.getInstance().getUserInfo();
                            self.setTel(mPhoneView.getText().toString());
                            MMKVHelper.getInstance().saveUserInfo(self);

                            new TipsDialog.Builder(getActivity())
                                    .setIcon(TipsDialog.ICON_FINISH)
                                    .setMessage(R.string.password_reset_success)
                                    .setDuration(1000)
                                    .addOnDismissListener(dialog -> {
                                        setResult(RESULT_OK);
                                        finish();
                                    })
                                    .show();
                        }
                    });
        }
    }

    /**
     * {@link TextView.OnEditorActionListener}
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && mCommitView.isEnabled()) {
            // 模拟点击提交按钮
            onClick(mCommitView);
            return true;
        }
        return false;
    }
}