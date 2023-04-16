package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps2d.model.Marker;
import com.google.android.material.tabs.TabLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.shape.view.ShapeTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.entity.HomeBannerData;
import com.sgd.tjlb.zhxf.entity.OrderData;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.ShopApplayWarrantyListApi;
import com.sgd.tjlb.zhxf.http.api.TakeOrderApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.AddEquipmentActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.OrderAdapter;
import com.sgd.tjlb.zhxf.ui.dialog.ShopInfoDialog;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;
import com.sgd.tjlb.zhxf.utils.maps.GaodeLbsLayerImpl;
import com.sgd.tjlb.zhxf.utils.maps.ILbsLayer;
import com.sgd.tjlb.zhxf.utils.maps.LocationInfo;
import com.youth.banner.util.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 消防宣传页
 */
public final class HomeFragment extends TitleBarFragment<HomeActivity> {

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private List<HomeBannerData> mBannerDatas = new ArrayList<>();
    private AppConfigBean mAppConfig;

    private ShapeTextView mTvOrdernum;
    private RecyclerView mRvOrder;
    private SmartRefreshLayout mRefreshLayout;
    private TextView tv_area_address;//当前区域
    private TabLayout tab_home;
    private LinearLayout layout_map;

    private OrderAdapter mAdapter;

    private List<OrderData> orderDataList = new ArrayList<>();// 订单信息

    private final String[] mTabs = {
            "列表",
            "地图",
    };

    private GaodeLbsLayerImpl mGaoDeMap;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMapClient(savedInstanceState);
    }

    private void initMapClient(Bundle savedInstanceState) {
        try {
            mGaoDeMap = new GaodeLbsLayerImpl(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mGaoDeMap != null) {
            mGaoDeMap.onCreate(savedInstanceState);
            mGaoDeMap.setLocationRes(R.mipmap.img_icon_location_blue);
            mGaoDeMap.setLocationChangeListener(new ILbsLayer.CommonLocationChangeListener() {
                @Override
                public void onLocationChanged(LocationInfo locationInfo) {

                }

                @Override
                public void onLocation(LocationInfo locationInfo) {

                }
            });

            //标记点击事件
            mGaoDeMap.setMarkClickListener(marker -> {
                LocationInfo locationInfo = (LocationInfo) marker.getObject();
                if (locationInfo != null){
                    ShopInfo shopInfo = locationInfo.getShopInfo();

                    //展示店铺弹窗，接单，其他等
                    openShopInfoDialog(shopInfo);
                }
            });
        }
    }

    //地图点击marker 弹窗
    private void openShopInfoDialog(ShopInfo shopInfo) {
        new ShopInfoDialog.Builder(getContext())
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setType(ShopInfoDialog.TYPE_AREA)
                .setShopInfo(shopInfo)
                .setListener(new ShopInfoDialog.OnListener() {
                    @Override
                    public void onShopBtn(int type, ShopInfo shopInfo) {
                        takeOrders(shopInfo);
                    }

                    @Override
                    public void onUpdateDevice(EquipmentInfo device) {

                    }

                    @Override
                    public void onAddRecord(EquipmentInfo device) {

                    }
                }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGaoDeMap != null)
            mGaoDeMap.onResume();
//        initData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGaoDeMap != null)
            mGaoDeMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGaoDeMap != null)
            mGaoDeMap.onDestroy();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        mRefreshLayout = findViewById(R.id.srl_home);
        tv_area_address = findViewById(R.id.tv_area_address);
        tab_home = findViewById(R.id.tab_home);
        mTvOrdernum = (ShapeTextView) findViewById(R.id.tv_home_ordernum);
        mRvOrder = (RecyclerView) findViewById(R.id.rv_home_order);
        layout_map = findViewById(R.id.layout_map);

        intTabLayout();
        initSmartRefreshLayout();
        initRecycler();
        initListener();

        refreshUI();
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return !super.isStatusBarDarkFont();
    }

    private void intTabLayout() {
        tab_home.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mRefreshLayout.setEnableRefresh(true);//启用刷新
                    mRvOrder.setVisibility(View.VISIBLE);
                    layout_map.setVisibility(View.GONE);
                } else {
                    mRefreshLayout.setEnableRefresh(false);//关闭刷新
                    mRvOrder.setVisibility(View.GONE);
                    layout_map.setVisibility(View.VISIBLE);
                    getPermissions();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LogUtils.d("onTabUnselected: " + tab.getText().toString());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtils.d("onTabReselected: " + tab.getText().toString());
            }
        });
    }


    private void refreshUI() {
        UserInfo self = MMKVHelper.getInstance().getUserInfo();
        tv_area_address.setText(self.getArea_name());
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

        //            AddEquipmentActivity.start(getContext(),shopInfo.getUser_id());
        mAdapter.setmCallBack(this::takeOrders);
    }

    //接单
    private void takeOrders(ShopInfo shopInfo) {
        EasyHttp.post(this)
                .api(new TakeOrderApi()
                        .setShopID(shopInfo.getShop_id())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        toast("接单成功");
                        findWarrantyList();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    protected void initData() {
        findWarrantyList();
    }

    //接单申请list
    private void findWarrantyList() {
        EasyHttp.post(this)
                .api(new ShopApplayWarrantyListApi()
                        .setPage(mPage)
                )
                .request(new HttpCallback<HttpData<List<ShopInfo>>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<List<ShopInfo>> data) {
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                        if (data.getData() != null) {
                            mAdapter.initData(data.getData());
                            mTvOrdernum.setText("派单：共(" + data.getData().size() + ")个");

                            List<LocationInfo> locationInfoList = data.getData().stream().map(shopInfo -> {
                                LocationInfo locationInfo = new LocationInfo(shopInfo.getLatitude(), shopInfo.getLongitude());
                                locationInfo.setName(shopInfo.getShop_name());
                                locationInfo.setOil(shopInfo.getAddress());
                                return locationInfo;
                            }).collect(Collectors.toList());

                            if (mGaoDeMap != null) {
                                mGaoDeMap.addPoiOverlay(locationInfoList);
                            }

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
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取位置权限
     * 1 初始 2 点击我的位置
     *
     * @param
     */
    private void getPermissions() {
        XXPermissions.with(this)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.ACCESS_FINE_LOCATION)
                // 如果不需要在后台使用定位功能，请不要申请此权限
//                .permission(Permission.ACCESS_BACKGROUND_LOCATION)
//                .interceptor(new PermissionInterceptor())
                .request((permissions, allGranted) -> {
                    if (!allGranted) {
                        toast("缺少必要权限无法定位");
                        return;
                    }
                    if (layout_map.getChildCount() == 0) {
                        layout_map.addView(mGaoDeMap.getMapView());
                    }
                    if (mGaoDeMap != null){
                        mGaoDeMap.setUpLocation();
                    }
                });
    }
}
