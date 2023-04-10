package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;

import com.google.android.material.tabs.TabLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.http.api.MyConstructionRecordListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.AddEquipmentActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.MyConstructionOrderAdapter;
import com.sgd.tjlb.zhxf.ui.adapter.OrderAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private TabLayout mTabLayout;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private MyConstructionOrderAdapter mAdapter;

    public static ConstructionOrderFragment newInstance() {
        return new ConstructionOrderFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
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
        initSmartRefreshLayout();
        initRecyclerView();
    }

    private void initSmartRefreshLayout() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
                mPage++;
                mRefreshType = ConstantUtil.REFRESH_MORE;
                initData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下滑
                mPage = 1;
                mRefreshType = ConstantUtil.REFRESH_FIRST;
                initData();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new MyConstructionOrderAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setmCallBack(shopInfo -> {
            AddEquipmentActivity.start(getContext(),shopInfo.getUser_id(),"");
        });
    }

    @Override
    public boolean isStatusBarEnabled() {
        return !super.isStatusBarEnabled();
    }

    @Override
    protected void initData() {
        findMyWarrantyList();
    }

    //报修单list
    private void findMyWarrantyList() {
        EasyHttp.post(this)
                .api(new MyConstructionRecordListApi()
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
