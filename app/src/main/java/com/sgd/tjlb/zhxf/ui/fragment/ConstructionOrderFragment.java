package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps2d.model.Marker;
import com.google.android.material.tabs.TabLayout;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.widget.view.SwitchButton;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordBean;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.http.api.AddDeviceMaintenanceRecordApi;
import com.sgd.tjlb.zhxf.http.api.MyOrderListApi;
import com.sgd.tjlb.zhxf.http.api.ShopEquipmentListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.AddEquipmentActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.MyConstructionOrderAdapter;
import com.sgd.tjlb.zhxf.ui.dialog.ShopInfoDialog;
import com.sgd.tjlb.zhxf.ui.dialog.UpdateWorkRecordDialog;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;
import com.sgd.tjlb.zhxf.utils.maps.GaodeLbsLayerImpl;
import com.sgd.tjlb.zhxf.utils.maps.ILbsLayer;
import com.sgd.tjlb.zhxf.utils.maps.LocationInfo;
import com.youth.banner.util.LogUtils;

import java.util.List;
import java.util.stream.Collectors;

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
    private LinearLayout layout_map;

    private MyConstructionOrderAdapter mAdapter;
    private GaodeLbsLayerImpl mGaoDeMap;

    public static ConstructionOrderFragment newInstance() {
        return new ConstructionOrderFragment();
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

    @Override
    public void onResume() {
        super.onResume();
        if (mGaoDeMap != null)
            mGaoDeMap.onResume();
        initData();
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
        return R.layout.fragment_construction_order;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tab_home);
        mRefreshLayout = findViewById(R.id.srl_construction);
        mRecyclerView = findViewById(R.id.rv_construction_order);
        layout_map = findViewById(R.id.layout_map);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
//        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);
        initTabLayout();
        initSmartRefreshLayout();
        initRecyclerView();
    }

    private void initTabLayout() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mRefreshLayout.setEnableRefresh(true);//开启刷新
                    mRecyclerView.setVisibility(View.VISIBLE);
                    layout_map.setVisibility(View.GONE);
                } else {
                    mRefreshLayout.setEnableRefresh(false);//关闭刷新
                    mRecyclerView.setVisibility(View.GONE);
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
                    if (mGaoDeMap != null) {
                        mGaoDeMap.setUpLocation();
                    }
                });
    }

    //地图点击marker 弹窗
    private void openShopInfoDialog(ShopInfo shopInfo) {
        new ShopInfoDialog.Builder(getContext())
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setType(ShopInfoDialog.TYPE_MY_ORDER)
                .setShopInfo(shopInfo)
                .setListener(new ShopInfoDialog.OnListener() {
                    @Override
                    public void onShopBtn(int type, ShopInfo shopInfo) {
                        if (shopInfo.isAddDeviceStatus()) {
                            AddEquipmentActivity.start(getContext(), shopInfo.getUser_id(), "");
                        }
                    }

                    @Override
                    public void onUpdateDevice(EquipmentInfo device) {

                    }

                    @Override
                    public void onAddRecord(EquipmentInfo device) {
                        AddWorkRecord(device);
                    }
                }).show();
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

        mAdapter.setmCallBack(new MyConstructionOrderAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(ShopInfo shopInfo) {
                if (shopInfo != null) {
                    if (shopInfo.isAddDeviceStatus()) {
                        AddEquipmentActivity.start(getContext(), shopInfo.getUser_id(), "");
                    }
                }
            }

            @Override
            public void onItemAddRecord(EquipmentInfo info) {
                AddWorkRecord(info);
            }
        });
    }

    /**
     * 添加工作记录弹窗
     */
    private void AddWorkRecord(EquipmentInfo deviceInfo) {
        //添加安装维修记录
        new UpdateWorkRecordDialog.Builder(getAttachActivity())
                .setData(null)
                .setBaseActivity(getAttachActivity())
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new UpdateWorkRecordDialog.OnListener() {
                    @Override
                    public void onSubmit(BaseDialog dialog, ConstructionRecordBean data) {
                        data.setDevice_id(deviceInfo.getId());
                        addDeviceMaintenance(dialog, data);
                    }
                })
                .show();
    }

    private void addDeviceMaintenance(BaseDialog dialog, ConstructionRecordBean data) {

        EasyHttp.post(this)
                .api(new AddDeviceMaintenanceRecordApi()
                        .setDevice_id(data.getDevice_id())
                        .setStatus_img(data.getStatus_img())
                        .setStatus_info(data.getStatus_info())
                        .setStatus(data.getStatus())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        toast("添加成功");
                        dialog.dismiss();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
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
                .api(new MyOrderListApi()
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

                            List<LocationInfo> locationInfoList = data.getData().stream().map(shopInfo -> {
                                LocationInfo locationInfo = new LocationInfo(shopInfo.getLatitude(), shopInfo.getLongitude());
                                locationInfo.setName(shopInfo.getShop_name());
                                locationInfo.setOil(shopInfo.getAddress());
                                locationInfo.setShopInfo(shopInfo);
                                return locationInfo;
                            }).collect(Collectors.toList());

                            if (mGaoDeMap != null) {
                                mGaoDeMap.clearAllMarkers();
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

    private void findEquipmentListByShopID(ShopInfo shopInfo) {
        EasyHttp.post(this)
                .api(new ShopEquipmentListApi()
                        .setUserID(shopInfo.getUser_id())
                )
                .request(new HttpCallback<HttpData<List<EquipmentInfo>>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<List<EquipmentInfo>> data) {
                        if (data.getData() != null) {
                            shopInfo.setDevicelist(data.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
//                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean checked) {

    }
}
