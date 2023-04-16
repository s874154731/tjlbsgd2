package com.sgd.tjlb.zhxf.ui.activity.init;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.app.AppFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.CityInfo;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.entity.VersionInfoBean;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AdressCitiesApi;
import com.sgd.tjlb.zhxf.http.api.AppConfigApi;
import com.sgd.tjlb.zhxf.http.api.AppVersionApi;
import com.sgd.tjlb.zhxf.http.api.HomeMsgsApi;
import com.sgd.tjlb.zhxf.http.api.PopularizeTypeApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.ActivityManager;
import com.sgd.tjlb.zhxf.other.AppConfig;
import com.sgd.tjlb.zhxf.other.DoubleClickHelper;
import com.sgd.tjlb.zhxf.ui.adapter.NavigationAdapter;
import com.sgd.tjlb.zhxf.ui.dialog.UpdateDialog;
import com.sgd.tjlb.zhxf.ui.fragment.ConstructionOrderFragment;
import com.sgd.tjlb.zhxf.ui.fragment.FuncFragment;
import com.sgd.tjlb.zhxf.ui.fragment.HomeFragment;
import com.sgd.tjlb.zhxf.ui.fragment.MineFragment;
import com.sgd.tjlb.zhxf.ui.fragment.PopularizeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 首页界面
 */
public final class HomeActivity extends AppActivity
        implements NavigationAdapter.OnNavigationListener {

    private static final String INTENT_KEY_IN_FRAGMENT_INDEX = "fragmentIndex";
    private static final String INTENT_KEY_IN_FRAGMENT_CLASS = "fragmentClass";

    private ViewPager mViewPager;
    private RecyclerView mNavigationView;

    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;
    private NavigationAdapter mNavigationAdapter;

    private List<HomeMsgBean> mMsgList = new ArrayList<>();

    public static void start(Context context) {
        start(context, FuncFragment.class);
    }

    public static void start(Context context, Class<? extends AppFragment<?>> fragmentClass) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(INTENT_KEY_IN_FRAGMENT_CLASS, fragmentClass);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_home_pager);
        mNavigationView = findViewById(R.id.rv_home_navigation);

        mNavigationAdapter = new NavigationAdapter(this);
        mNavigationAdapter.addItem(new NavigationAdapter.MenuItem(getString(R.string.home_nav_index),
                ContextCompat.getDrawable(this, R.drawable.nav_home_selector)));
        mNavigationAdapter.addItem(new NavigationAdapter.MenuItem(getString(R.string.home_nav_sgd),
                ContextCompat.getDrawable(this, R.drawable.nav_popularize_selector)));
        mNavigationAdapter.addItem(new NavigationAdapter.MenuItem(getString(R.string.home_nav_me),
                ContextCompat.getDrawable(this, R.drawable.nav_mine_selector)));
        mNavigationAdapter.setOnNavigationListener(this);
        mNavigationView.setAdapter(mNavigationAdapter);
    }

    @Override
    protected void initData() {
        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(HomeFragment.newInstance());
        mPagerAdapter.addFragment(ConstructionOrderFragment.newInstance());
//        mPagerAdapter.addFragment(TestFragment.newInstance());
        mPagerAdapter.addFragment(MineFragment.newInstance());
        mViewPager.setAdapter(mPagerAdapter);

        onNewIntent(getIntent());

        findAppVersion();
        findAppConfig();
//        findHttpDatas();
    }

    private void findHttpDatas() {
        findAreaDatas();
        findAppVersion();
        findAppConfig();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        switchFragment(mPagerAdapter.getFragmentIndex(getSerializable(INTENT_KEY_IN_FRAGMENT_CLASS)));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 保存当前 Fragment 索引位置
        outState.putInt(INTENT_KEY_IN_FRAGMENT_INDEX, mViewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 恢复当前 Fragment 索引位置
        switchFragment(savedInstanceState.getInt(INTENT_KEY_IN_FRAGMENT_INDEX));
    }

    private void switchFragment(int fragmentIndex) {
        if (fragmentIndex == -1) {
            return;
        }

        switch (fragmentIndex) {
            case 0:
            case 1:
            case 2:
            case 3:
                mViewPager.setCurrentItem(fragmentIndex);
                mNavigationAdapter.setSelectedPosition(fragmentIndex);
                break;
            default:
                break;
        }
    }

    /**
     * {@link NavigationAdapter.OnNavigationListener}
     */

    @Override
    public boolean onNavigationItemSelected(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                mViewPager.setCurrentItem(position);
                return true;
            default:
                return false;
        }
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white);
    }

    @Override
    public void onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            toast(R.string.home_exit_hint);
            return;
        }

        // 移动到上一个任务栈，避免侧滑引起的不良反应
        moveTaskToBack(false);
        postDelayed(() -> {
            // 进行内存优化，销毁掉所有的界面
            ActivityManager.getInstance().finishAllActivities();
            // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
            // System.exit(0);
        }, 300);
    }

    //获取地区数据
    private void findAreaDatas() {
        List<CityInfo> tempCities = MMKVHelper.getInstance().findCities();
        if (tempCities != null && tempCities.size() > 0)
            return;

        EasyHttp.post(this)
                .api(new AdressCitiesApi())
                .request(new HttpCallback<HttpData<List<CityInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<CityInfo>> data) {

                        if (data.getData() == null)
                            return;
                        //保存到本地
                        MMKVHelper.getInstance().saveCities(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取版本信息
    private void findAppVersion() {
        EasyHttp.post(this)
                .api(new AppVersionApi())
                .request(new HttpCallback<HttpData<VersionInfoBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<VersionInfoBean> data) {
                        if (data.getData() != null)
                            MMKVHelper.getInstance().saveVersionInfo(data.getData());
                        checkUpdate(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //检查更新
    private void checkUpdate(VersionInfoBean data) {
        int versionCode = data.getVersion_no();
        // 本地的版本码和服务器的进行比较
        if (versionCode > AppConfig.getVersionCode()) {
            new UpdateDialog.Builder(getContext())
                    .setVersionName(data.getVersion_name())
                    .setForceUpdate(false)
                    .setUpdateLog(data.getVersion_content())
                    .setDownloadUrl(data.getVersion_url())
//                    .setFileMd5("8782953268e7ac8242414c1e5d55f50b")
                    .setFileMd5(null)
                    .show();
        }
    }

    //获取消息
    private void findMsgsDatas() {
        mMsgList.clear();

        EasyHttp.post(this)
                .api(new HomeMsgsApi()
                        .setPage(1)
                )
                .request(new HttpCallback<HttpData<List<HomeMsgBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<HomeMsgBean>> data) {
                        if (data.getData() != null) {
                            mMsgList.addAll(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取app常量
    private void findAppConfig() {
        EasyHttp.post(this)
                .api(new AppConfigApi())
                .request(new HttpCallback<HttpData<AppConfigBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<AppConfigBean> data) {
                        if (data.getData() != null) {
                            MMKVHelper.getInstance().saveAppConfig(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取宣传分类
    private void findPopularizeType() {
        EasyHttp.post(this)
                .api(new PopularizeTypeApi())
                .request(new HttpCallback<HttpData<List<PopularizeTypeBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<PopularizeTypeBean>> data) {
                        if (data.getData() != null) {
                            MMKVHelper.getInstance().savePopularizeTypes(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mNavigationView.setAdapter(null);
        mNavigationAdapter.setOnNavigationListener(null);
    }

    @Override
    protected boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }
}