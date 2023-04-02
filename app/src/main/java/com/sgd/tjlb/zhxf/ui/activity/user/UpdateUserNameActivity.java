package com.sgd.tjlb.zhxf.ui.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.SubmitButton;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.UpdateUserNameApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.InputTextManager;

import okhttp3.Call;

public class UpdateUserNameActivity extends AppActivity {

    private ClearEditText mEtUserNameView;
    private SubmitButton mCommitView;

    private UserInfo mSelf;

    public static void start(Activity context) {
        Intent starter = new Intent(context, UpdateUserNameActivity.class);
        context.startActivityForResult(starter, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_uaer_name;
    }

    @Override
    protected void initView() {

        mEtUserNameView = (ClearEditText) findViewById(R.id.et_updateupdate_user_name_username);
        mCommitView = (SubmitButton) findViewById(R.id.btn_update_user_name_commit);

        initListener();

        InputTextManager.with(this)
                .addView(mEtUserNameView)
                .setMain(mCommitView)
                .build();
    }

    private void initListener() {

        mCommitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtUserNameView.getText())) {
                    toast("用户名不能为空");
                    return;
                }

                // 提交注册
                EasyHttp.post(UpdateUserNameActivity.this)
                        .api(new UpdateUserNameApi()
                                .setUserName(mEtUserNameView.getText().toString().trim()))
                        .request(new HttpCallback<HttpData<UpdateUserNameApi.Bean>>(UpdateUserNameActivity.this) {

                            @Override
                            public void onStart(Call call) {
                                mCommitView.showProgress();
                            }

                            @Override
                            public void onEnd(Call call) {
                            }

                            @Override
                            public void onSucceed(HttpData<UpdateUserNameApi.Bean> data) {
                                postDelayed(() -> {
                                    mCommitView.showSucceed();
                                    postDelayed(() -> {

                                        mSelf.setUserName(mEtUserNameView.getText().toString());
                                        MMKVHelper.getInstance().saveUserInfo(mSelf);

                                        setResult(RESULT_OK);
                                        finish();
                                    }, 1000);
                                }, 1000);
                            }

                            @Override
                            public void onFail(Exception e) {
                                super.onFail(e);
                                postDelayed(() -> {
                                    mCommitView.showError(3000);
                                }, 1000);
                            }
                        });
            }
        });
    }

    @Override
    protected void initData() {
        mSelf = MMKVHelper.getInstance().getUserInfo();

        mEtUserNameView.setText(mSelf.getUserName());
    }


}
