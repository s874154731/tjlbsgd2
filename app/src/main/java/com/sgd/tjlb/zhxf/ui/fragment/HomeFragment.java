package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.google.android.material.tabs.TabLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.shape.view.ShapeTextView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.HomeBannerData;
import com.sgd.tjlb.zhxf.entity.OrderData;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.WarrantyListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.MapActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.InputTipsAdapter;
import com.sgd.tjlb.zhxf.ui.adapter.OrderAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;
import com.youth.banner.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private MapView mMapView;
    private SmartRefreshLayout mRefreshLayout;
    private TextView tv_area_address;//当前区域
    private TabLayout tab_home;

    private OrderAdapter mAdapter;

    private List<OrderData> orderDataList = new ArrayList<>();// 订单信息

    //地图
    private AMapLocationClient locationClientSingle;
    private AMapLocation mCurrentAMapLocation;//当前定位数据
    private AMap mAMap;
    private UiSettings mUiSettings;//地图ui设置
    private Marker mPoiMarker;
    private double mineLatitude, mineLongitude;

    private final String[] mTabs = {
            "列表",
            "地图",
    };

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
        tv_area_address = findViewById(R.id.tv_area_address);
        tab_home = findViewById(R.id.tab_home);
        mTvOrdernum = (ShapeTextView) findViewById(R.id.tv_home_ordernum);
        mRvOrder = (RecyclerView) findViewById(R.id.rv_home_order);
        mMapView = findViewById(R.id.map_home);

        intTabLayout();
        initSmartRefreshLayout();
        initRecycler();
        initListener();
        initMap();

        refreshUI();
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return !super.isStatusBarDarkFont();
    }

    private void initMap() {
        //高德地图
        //初始化定位  用户合规检查
        AMapLocationClient.updatePrivacyShow(getContext(), true, true);
        AMapLocationClient.updatePrivacyAgree(getContext(), true);
        //初始化搜索 用户合规检查
        ServiceSettings.updatePrivacyShow(getContext(), true, true);
        ServiceSettings.updatePrivacyAgree(getContext(), true);
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        // 添加点击marker监听事件
        mAMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            return false;
        });
        // 添加显示infowindow监听事件
        mAMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.poikeywordsearch_uri,
                        null);
                TextView title = (TextView) view.findViewById(R.id.title);
                title.setText(marker.getTitle());

                TextView snippet = (TextView) view.findViewById(R.id.snippet);
                snippet.setText(marker.getSnippet());
                return view;
            }
        });

        mAMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        //设置 地图 UI
        //设置地图默认的比例尺是否显示
        mUiSettings = mAMap.getUiSettings();
        mUiSettings.setScaleControlsEnabled(false);
        //设置地图默认的缩放按钮是否显示
        mUiSettings.setZoomControlsEnabled(false);
        //设置地图默认的指南针是否显示
        mUiSettings.setCompassEnabled(false);
        //设置地图默认的定位按钮是否显示
        mUiSettings.setMyLocationButtonEnabled(false);
        //设置logo位置
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// 设置地图logo显示在左下方
        //设置地图是否可以手势滑动
        mUiSettings.setScrollGesturesEnabled(true);
        //设置地图是否可以手势缩放大小
        mUiSettings.setZoomGesturesEnabled(true);

    }

    private void intTabLayout() {
        tab_home.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mRvOrder.setVisibility(View.VISIBLE);
                    mMapView.setVisibility(View.GONE);
                } else {
                    mRvOrder.setVisibility(View.GONE);
                    mMapView.setVisibility(View.VISIBLE);
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

                            mTvOrdernum.setText("施工单：共(" + data.getData().size() + ")个");
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

                });
    }

    /**
     * 启动单次客户端定位
     */
    void startSingleLocation() {
        if (locationClientSingle == null) {
            try {
                locationClientSingle = new AMapLocationClient(getContext());
                AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
                //使用单次定位
                locationClientOption.setOnceLocation(true);
                // 地址信息
                locationClientOption.setNeedAddress(true);
                locationClientOption.setLocationCacheEnable(false);
                locationClientSingle.setLocationOption(locationClientOption);
                locationClientSingle.setLocationListener(locationSingleListener);
                locationClientSingle.startLocation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 单次客户端的定位监听
     */
    AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location != null) {
                mCurrentAMapLocation = location;
                if (location.getErrorCode() == 0) {
                    moveCurrentLocalAndShowList(location);
                }
            }
        }
    };

    //移动到当前位置，并显示list
    private void moveCurrentLocalAndShowList(AMapLocation location) {
        if (location == null) {
            return;
        }

        mPoiMarker = mAMap.addMarker(new MarkerOptions());
        mineLatitude = location.getLatitude();
        mineLongitude = location.getLongitude();
        LatLng markerPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mPoiMarker.setPosition(markerPosition);
        mPoiMarker.setTitle(location.getAddress());
        mPoiMarker.showInfoWindow();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 17));

        String currentCity = location.getCity();

        String aoi = location.getAoiName();
        String keyword = TextUtils.isEmpty(location.getAoiName()) ? "" : location.getAoiName();
        LogUtils.d("定位关键字：" + keyword);

//        searchNearlyAddress(keyword,currentCity);
    }

    /**
     * 根据关键字搜索附近位置
     * @param keyword 关键字
     * @param currentCity 当前城市
     */
    private void searchNearlyAddress(String keyword, String currentCity) {
        InputtipsQuery inputquery = new InputtipsQuery(keyword, currentCity);
        Inputtips inputTips = new Inputtips(getContext(), inputquery);
        inputTips.setInputtipsListener((tipList, rCode) -> {
            if (rCode == 1000) {// 正确返回

            } else {
                LogUtils.e("错误码：" + rCode);
            }
        });
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
            mMapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
            mMapView.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
            mMapView.onDestroy();
    }
}
