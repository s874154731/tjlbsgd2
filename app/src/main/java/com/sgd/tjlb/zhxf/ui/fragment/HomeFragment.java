package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.shape.view.ShapeTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.entity.HomeBannerData;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;
import com.sgd.tjlb.zhxf.entity.OrderData;
import com.sgd.tjlb.zhxf.entity.PopularizeData;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AppConfigApi;
import com.sgd.tjlb.zhxf.http.api.EquipmentInfoListApi;
import com.sgd.tjlb.zhxf.http.api.GetShopApi;
import com.sgd.tjlb.zhxf.http.api.HomeMsgsApi;
import com.sgd.tjlb.zhxf.http.api.PopularizeListApi;
import com.sgd.tjlb.zhxf.http.api.PopularizeTypeApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.NoticeActivity;
import com.sgd.tjlb.zhxf.ui.activity.NoticeDetailActivity;
import com.sgd.tjlb.zhxf.ui.activity.StoreDataActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.EquipmentAdapter;
import com.sgd.tjlb.zhxf.ui.adapter.OrderAdapter;
import com.sgd.tjlb.zhxf.ui.adapter.PopularizeAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;
import com.sgd.tjlb.zhxf.widget.NoticeTextView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Group;
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
        /*mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
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
        });*/
    }

    private void initRecycler() {
        mAdapter = new OrderAdapter(getContext());
        mRvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrder.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
//        findAppConfig();

        for (int i = 0; i < 5; i++) {
            OrderData orderData = new OrderData();
            orderData.setType(i % 2 == 1 ? 1 : 2);
            orderData.setAddress("地址" + i);
            orderData.setName("门店名称" + i);
            orderDataList.add(orderData);
        }
        mAdapter.initData(orderDataList);
    }


    /*//获取app常量
    private void findAppConfig() {
        mAppConfig = MMKVHelper.getInstance().findAppConfig();
        if (mAppConfig != null) {
            mBannerDatas.clear();
            if (mAppConfig.getApp_slide() != null) {
                mBannerDatas.addAll(mAppConfig.getApp_slide());
                mBannerAdapter.notifyDataSetChanged();
            }
            return;
        }

        EasyHttp.post(this)
                .api(new AppConfigApi())
                .request(new HttpCallback<HttpData<AppConfigBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<AppConfigBean> data) {
                        if (data.getData() != null) {

                            mBannerDatas.clear();
                            if (data.getData().getApp_slide() != null) {
                                mBannerDatas.addAll(data.getData().getApp_slide());
                                mBannerAdapter.notifyDataSetChanged();
                            }
                            MMKVHelper.getInstance().saveAppConfig(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }*/
}
