package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.PopularizeTypeApi;
import com.sgd.tjlb.zhxf.http.api.WarrantyListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.OrderAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 施工单
 */
public final class ConstructionOrderFragment extends TitleBarFragment<HomeActivity>
        implements SwitchButton.OnCheckedChangeListener {

    private final String TAB_TITLE_NEWS = "消防新闻";
    private final String TAB_TITLE_FIND = "消防法现";
    private final String TAB_TITLE_KNOLWLEDGE = "消防知识";
    private final String[] mTabs = {
            TAB_TITLE_NEWS,
            TAB_TITLE_FIND,
            TAB_TITLE_KNOLWLEDGE
    };

    private int mPage = ConstantUtil.PAGE_INDEX;

    private TabLayout mTabLayout;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private OrderAdapter mAdapter;

    public static ConstructionOrderFragment newInstance() {
        return new ConstructionOrderFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_construction_order;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tab_popularize);
        mRefreshLayout = findViewById(R.id.srl_construction);
        mRecyclerView = findViewById(R.id.rv_construction_order);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
//        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new OrderAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean isStatusBarEnabled() {
        return !super.isStatusBarEnabled();
    }

    @Override
    protected void initData() {
        findWarrantyList();
    }

    //报修单list
    private void findWarrantyList() {
        EasyHttp.post(this)
                .api(new WarrantyListApi()
                        .setPage(mPage)
                )
                .request(new HttpCallback<HttpData<List<ShopInfo>>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<List<ShopInfo>> data) {
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                        if (data.getData() != null) {
                            mAdapter.initData(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                    }
                });
    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean checked) {

    }

}
