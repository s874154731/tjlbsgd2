package com.sgd.tjlb.zhxf.ui.activity.init;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.SingleClick;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AppConfigApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.ActivityManager;
import com.sgd.tjlb.zhxf.ui.activity.func.AgreementActivity;
import com.sgd.tjlb.zhxf.ui.activity.login.LoginActivity;
import com.sgd.tjlb.zhxf.ui.adapter.GuideAdapter;
import com.sgd.tjlb.zhxf.ui.dialog.PrivacyGreementDialog;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;
import me.relex.circleindicator.CircleIndicator3;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/09/21
 * desc   : 应用引导页
 */
public final class GuideActivity extends AppActivity {

    private ViewPager2 mViewPager;
    private CircleIndicator3 mIndicatorView;
    private View mCompleteView;
    private TextView mSkipBtn;

    private GuideAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.guide_activity;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, GuideActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_guide_pager);
        mIndicatorView = findViewById(R.id.cv_guide_indicator);
        mCompleteView = findViewById(R.id.btn_guide_complete);
        mSkipBtn = findViewById(R.id.tv_btn_skip);
        setOnClickListener(mCompleteView, mSkipBtn);
    }

    @Override
    protected void initData() {
        mAdapter = new GuideAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.registerOnPageChangeCallback(mCallback);
        mIndicatorView.setViewPager(mViewPager);

        //是否同意使用
        boolean isAgreeUse = MMKVHelper.getInstance().getBooleanByKey(MMKVHelper.KEY_FIRST_AGREE_USE);
        if (!isAgreeUse)
            openPrivacyGreementDialog();
    }

    private void openPrivacyGreementDialog() {
        new PrivacyGreementDialog.Builder(getContext())
                .setCanceledOnTouchOutside(false)
                .setCancelable(false)
                .setListener(new PrivacyGreementDialog.OnListener() {
                    @Override
                    public void onAgreement(BaseDialog dialog) {
                        MMKVHelper.getInstance().putBoolean(MMKVHelper.KEY_FIRST_AGREE_USE, true);
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        exitApp();
                    }

                    @Override
                    public void onSee() {
                        findAppConfig();
                    }
                }).show();
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCompleteView || view == mSkipBtn) {
            //设置首次打开
            MMKVHelper.getInstance().putBoolean(MMKVHelper.KEY_FIRST_OPEN_APP, true);
            LoginActivity.start(getContext());
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.unregisterOnPageChangeCallback(mCallback);
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    private final ViewPager2.OnPageChangeCallback mCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPager.getCurrentItem() != mAdapter.getCount() - 1 || positionOffsetPixels <= 0) {
                return;
            }

            mIndicatorView.setVisibility(View.VISIBLE);
            mCompleteView.setVisibility(View.INVISIBLE);
            mCompleteView.clearAnimation();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state != ViewPager2.SCROLL_STATE_IDLE) {
                return;
            }

            boolean lastItem = mViewPager.getCurrentItem() == mAdapter.getCount() - 1;
            mIndicatorView.setVisibility(lastItem ? View.INVISIBLE : View.VISIBLE);
            mCompleteView.setVisibility(lastItem ? View.VISIBLE : View.INVISIBLE);
            if (lastItem) {
                // 按钮呼吸动效
                ScaleAnimation animation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(350);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setRepeatCount(Animation.INFINITE);
                mCompleteView.startAnimation(animation);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void exitApp() {
        postDelayed(() -> {
            // 进行内存优化，销毁掉所有的界面
            ActivityManager.getInstance().finishAllActivities();
            // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
            // System.exit(0);
        }, 300);
    }

    //获取app常量
    private void findAppConfig() {
        AppConfigBean appConfig = MMKVHelper.getInstance().findAppConfig();
        if (appConfig != null && !TextUtils.isEmpty(appConfig.getApp_agree())) {
            AgreementActivity.start(getActivity(), appConfig.getApp_agree());
            return;
        }

        EasyHttp.post(this)
                .api(new AppConfigApi())
                .request(new HttpCallback<HttpData<AppConfigBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<AppConfigBean> data) {
                        if (data.getData() != null) {
                            MMKVHelper.getInstance().saveAppConfig(data.getData());

                            AgreementActivity.start(getActivity(), data.getData().getApp_agree());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }
}