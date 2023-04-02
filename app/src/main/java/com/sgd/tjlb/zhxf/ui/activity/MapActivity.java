package com.sgd.tjlb.zhxf.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.hjq.base.BaseActivity;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.LocationBean;
import com.sgd.tjlb.zhxf.ui.adapter.InputTipsAdapter;
import com.sgd.tjlb.zhxf.utils.PoiOverlay;
import com.youth.banner.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class MapActivity extends AppActivity implements Inputtips.InputtipsListener, OnItemClickListener {


    public static final String KEY_RETURN_AREA = "KEY_RETURN_AREA";

    private LinearLayout mLayoutRoot;
    private MapView mapView;
    private RelativeLayout mRlTop;
    private TextView mTvCancel;
    private AppCompatButton mBtnSend;
    private RegexEditText mEtMapSearch;
    private ListView mInputListView;
    private RelativeLayout mRlLocation;

    private AMap mAMap;
    private String mKeyWords = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 1;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    private UiSettings mUiSettings;//地图ui设置

    //定位
    private AMapLocationClient locationClientSingle = null;

    private List<Tip> mCurrentTipList = new ArrayList<>();
    private InputTipsAdapter mIntipAdapter;

    private Marker mPoiMarker;
    //39.90403, 116.407525 北京市经纬度
    private double mineLatitude /*= 39.90403*/;
    private double mineLongitude /*= 116.407525*/;

    private final int On_Create = 1;
    private final int My_Location = 2;
    public final static int Location_Data = 1;//回调的code

    private LocationBean locationBean;//位置
    private String mChooseAreaName;//选中的地图位置

    private AMapLocation mCurrentAMapLocation;//当前定位数据

    public static void start(BaseActivity activity, OnChooseAreaListener listener) {
        Intent intent = new Intent(activity, MapActivity.class);
        activity.startActivityForResult(intent, (resultCode, data) -> {

            if (listener == null || data == null) {
                return;
            }

            if (resultCode == RESULT_OK) {
                listener.onSucceed((LocationBean) data.getSerializableExtra(KEY_RETURN_AREA));
            } else {
                listener.onCancel();
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

        mLayoutRoot = (LinearLayout) findViewById(R.id.layout_root);
        mRlTop = (RelativeLayout) findViewById(R.id.rl_top);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mBtnSend = (AppCompatButton) findViewById(R.id.btn_send);
        mEtMapSearch = (RegexEditText) findViewById(R.id.et_map_search);
        mInputListView = (ListView) findViewById(R.id.rv_map);
        mRlLocation = (RelativeLayout) findViewById(R.id.rl_location);

        mInputListView.setOnItemClickListener(this);

        //高德地图
        //初始化定位  用户合规检查
        AMapLocationClient.updatePrivacyShow(this, true, true);
        AMapLocationClient.updatePrivacyAgree(this, true);
        //初始化搜索 用户合规检查
        ServiceSettings.updatePrivacyShow(this, true, true);
        ServiceSettings.updatePrivacyAgree(this, true);

        //获取当前位置
        getPermissions(On_Create);

        initMapView();

        initListener();

    }

    @Override
    protected void initView() {

    }

    private void initMapView() {
        if (mAMap == null) {
            mAMap = mapView.getMap();
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

    private void initListener() {
        mEtMapSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                String newText = charSequence.toString();
                if (!TextUtils.isEmpty(newText)) {

                    //当前城市
                    String mCurrentCity = mCurrentAMapLocation == null ? "" : mCurrentAMapLocation.getCity();

                    searchNearlyAddress(newText,mCurrentCity);
                    /*InputtipsQuery inputquery = new InputtipsQuery(newText, mCurrentCity);
                    Inputtips inputTips = new Inputtips(MapActivity.this.getApplicationContext(), inputquery);
                    inputTips.setInputtipsListener((tipList, rCode) -> {
                        if (rCode == 1000) {// 正确返回
                            mCurrentTipList.clear();
                            mCurrentTipList.addAll(tipList);
                            List<String> listString = new ArrayList<String>();
                            for (int i = 0; i < tipList.size(); i++) {
                                listString.add(tipList.get(i).getName());
                            }
                            mIntipAdapter = new InputTipsAdapter(
                                    getApplicationContext(),
                                    mCurrentTipList);
                            mInputListView.setAdapter(mIntipAdapter);
                            mIntipAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.show(rCode);
                        }

                    });
                    inputTips.requestInputtipsAsyn();*/
                } else {
                    if (mIntipAdapter != null && mCurrentTipList != null) {
                        mCurrentTipList.clear();
                        mIntipAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //我的位置
        mRlLocation.setOnClickListener(view -> {
            if (mineLatitude != 0 && mineLongitude != 0) {
                getPermissions(My_Location);
            } else {
                getPermissions(On_Create);
            }
        });

        mTvCancel.setOnClickListener(v -> {
            finish();
        });

        mBtnSend.setOnClickListener(view -> {
            if (locationBean != null) {
                setResult(RESULT_OK, new Intent()
                        .putExtra(KEY_RETURN_AREA, locationBean));
                finish();
            }
        });

    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keywords) {
        currentPage = 1;
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keywords, "", "");
        // 设置每页最多返回多少条poiitem
        query.setPageSize(50);
        // 设置查第一页
        query.setPageNum(currentPage);

        try {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
                @Override
                public void onPoiSearched(PoiResult result, int rCode) {
                    if (rCode == 1000) {
                        if (result != null && result.getQuery() != null) {// 搜索poi的结果
                            if (result.getQuery().equals(query)) {// 是否是同一条
                                poiResult = result;
                                // 取得搜索到的poiitems有多少页
                                List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                                List<SuggestionCity> suggestionCities = poiResult
                                        .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                                if (poiItems != null && poiItems.size() > 0) {
                                    mAMap.clear();// 清理之前的图标
                                    PoiOverlay poiOverlay = new PoiOverlay(mAMap, poiItems);
                                    poiOverlay.removeFromMap();
                                    poiOverlay.addToMap();
                                    poiOverlay.zoomToSpan();
                                } else if (suggestionCities != null
                                        && suggestionCities.size() > 0) {
                                    showSuggestCity(suggestionCities);
                                } else {
                                    ToastUtils.show("没搜索到数据");
                                }
                            }
                        } else {
                            ToastUtils.show("没搜索到数据");
                        }
                    } else {
                        ToastUtils.show(rCode);
                    }
                }

                @Override
                public void onPoiItemSearched(PoiItem poiItem, int i) {

                }
            });
            poiSearch.searchPOIAsyn();
        } catch (AMapException e) {
            e.printStackTrace();
        }

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
//        ToastUtils.show(infomation);
    }

    @Override
    protected void initData() {

    }

    /**
     * 输入提示回调
     *
     * @param tipList
     * @param rCode
     */
    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
            mCurrentTipList = tipList;
            List<String> listString = new ArrayList<String>();
            for (int i = 0; i < tipList.size(); i++) {
                listString.add(tipList.get(i).getName());
            }
            mIntipAdapter = new InputTipsAdapter(
                    getApplicationContext(),
                    mCurrentTipList);
            mInputListView.setAdapter(mIntipAdapter);
            mIntipAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.show(rCode);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mCurrentTipList != null) {
            mAMap.clear();
            Tip tip = (Tip) adapterView.getItemAtPosition(i);
            locationBean = new LocationBean();

            if (tip == null || tip.getPoint() == null) {
                return;
            }

            locationBean.setLatitude(tip.getPoint().getLatitude());
            locationBean.setLongitude(tip.getPoint().getLongitude());
            locationBean.setName(tip.getName());
            locationBean.setValue(tip.getTypeCode());
            String detailLocal = /*tip.getDistrict() +*/ tip.getAddress();
            locationBean.setDetailLocal(detailLocal);
            if (tip.getPoiID() == null || tip.getPoiID().equals("")) {
                doSearchQuery(tip.getName());
            } else {
                addTipMarker(tip);
            }
        }
    }

    /**
     * 用marker展示输入提示list选中数据
     *
     * @param tip
     */
    private void addTipMarker(Tip tip) {
        if (tip == null) {
            return;
        }
        mPoiMarker = mAMap.addMarker(new MarkerOptions());
        LatLonPoint point = tip.getPoint();
        if (point != null) {
            LatLng markerPosition = new LatLng(point.getLatitude(), point.getLongitude());
            mPoiMarker.setPosition(markerPosition);
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 17));
        }
        mPoiMarker.setTitle(tip.getName());
        mPoiMarker.setSnippet(tip.getAddress());
        mPoiMarker.showInfoWindow();
    }

    /**
     * 获取位置权限
     *
     * @param type 1 初始 2 点击我的位置
     */
    void getPermissions(int type) {

        XXPermissions.with(this)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.ACCESS_FINE_LOCATION)
                // 如果不需要在后台使用定位功能，请不要申请此权限
//                .permission(Permission.ACCESS_BACKGROUND_LOCATION)
//                .interceptor(new PermissionInterceptor())
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (!allGranted) {
                            return;
                        }
                        switch (type) {
                            case On_Create:
                                startSingleLocation();
                                break;
                            case My_Location:
                                moveCurrentLocalAndShowList(mCurrentAMapLocation);
                                /*mPoiMarker = mAMap.addMarker(new MarkerOptions());
                                LatLng markerPosition = new LatLng(mineLatitude, mineLongitude);
                                mPoiMarker.setPosition(markerPosition);
                                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 17));*/
                                break;
                        }
                    }
                });
    }

    /**
     * 启动单次客户端定位
     */
    void startSingleLocation() {
        if (locationClientSingle == null) {
            try {
                locationClientSingle = new AMapLocationClient(this.getApplicationContext());
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

            /*long callBackTime = System.currentTimeMillis();
            StringBuffer sb = new StringBuffer();
            sb.append("单次定位完成\n");
            sb.append("回调时间: " + Utils.formatUTC(callBackTime, null) + "\n");
            if(null == location){
                sb.append("定位失败：location is null!!!!!!!");
            } else {
                sb.append(Utils.getLocationStr(location));
            }
            tvResultSingle.setText(sb.toString());*/
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

        searchNearlyAddress(keyword,currentCity);
    }

    /**
     * 根据关键字搜索附近位置
     * @param keyword 关键字
     * @param currentCity 当前城市
     */
    private void searchNearlyAddress(String keyword, String currentCity) {
        InputtipsQuery inputquery = new InputtipsQuery(keyword, currentCity);
        Inputtips inputTips = new Inputtips(MapActivity.this.getApplicationContext(), inputquery);
        inputTips.setInputtipsListener((tipList, rCode) -> {
            if (rCode == 1000) {// 正确返回
                mCurrentTipList.clear();
                mCurrentTipList.addAll(tipList);
                mIntipAdapter = new InputTipsAdapter(
                        getApplicationContext(),
                        mCurrentTipList);
                mInputListView.setAdapter(mIntipAdapter);
                mIntipAdapter.notifyDataSetChanged();
            } else {
                LogUtils.e("错误码：" + rCode);
            }
        });
        inputTips.requestInputtipsAsyn();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    public interface OnChooseAreaListener {

        /**
         * 选中地点回调
         *
         * @param locationBean 选中的地理位置
         */
        void onSucceed(LocationBean locationBean);

        /**
         * 取消注册
         */
        default void onCancel() {
        }
    }

}
