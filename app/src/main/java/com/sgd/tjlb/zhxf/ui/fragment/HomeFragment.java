package com.sgd.tjlb.zhxf.ui.fragment;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.shape.view.ShapeTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.HomeBannerData;
import com.sgd.tjlb.zhxf.entity.OrderData;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AppConfigApi;
import com.sgd.tjlb.zhxf.http.api.WarrantyListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.OrderAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 消防宣传页
 */
public final class HomeFragment extends AppFragment<HomeActivity> {

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private List<HomeBannerData> mBannerDatas = new ArrayList<>();
    private AppConfigBean mAppConfig;

    private ShapeTextView mTvOrdernum;
    private RecyclerView mRvOrder;
    private SmartRefreshLayout mRefreshLayout;

    private OrderAdapter mAdapter;

    private List<OrderData> orderDataList = new ArrayList<>();// 订单信息

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        mRefreshLayout = findViewById(R.id.srl_home);
        mTvOrdernum = (ShapeTextView) findViewById(R.id.tv_home_ordernum);
        mRvOrder = (RecyclerView) findViewById(R.id.rv_home_order);

//        initBanner();
        initSmartRefreshLayout();
        initRecycler();
        initListener();
    }

    private void initListener() {

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

    private void initRecycler() {
        mAdapter = new OrderAdapter(getContext());
        mRvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrder.setAdapter(mAdapter);
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
}
