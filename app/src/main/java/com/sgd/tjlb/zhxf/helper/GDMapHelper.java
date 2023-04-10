package com.sgd.tjlb.zhxf.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;

/**
 * @ProjectName: tjlbsgd2
 * @Package: com.sgd.tjlb.zhxf.helper
 * @ClassName: GDMapHelper
 * @Description: 高德地图api使用工具类-使用枚举类型单例
 * @CreateDate: 2023/4/10/010 11:27
 * @UpdateUser: shi
 * @UpdateDate: 2023/4/10/010 11:27
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public enum GDMapHelper {
    INSTANCE;

    private Context mContext;
    private MapView mMapView;
    private AMap mAMap;
    //定位服务类
    private AMapLocationClient mLocationClient;
    //定位参数设置，通过这个类可以对定位的相关参数进行设置在AMapLocationClient进行定位时需要这些参数
    private AMapLocationClientOption mLocationOption;

    public static GDMapHelper getInstance(){
        return INSTANCE;
    }

    public void init(Context context,MapView mapView) throws Exception {
        mContext = context;
        mMapView = mapView;
        mAMap = mapView.getMap();
        initLocation();
    }

    // 初始化定位相关参数
    private void initLocation() throws Exception {
        mLocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setHttpTimeOut(20000);
        mLocationClient.setLocationOption(mLocationOption);
    }

    // 请求定位权限
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions((FragmentActivity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    // 检查是否有定位权限
    private boolean hasLocationPermission() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    // 开始定位
    public void startLocation() {
        if (!hasLocationPermission()) {
            requestLocationPermission();
            return;
        }
        mLocationClient.startLocation();
    }

    // 停止定位
    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    // 获取当前位置的经纬度
    public LatLng getCurrentLocation() {
        Location location = mAMap.getMyLocation();
        if (location != null) {
            return new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            return null;
        }
    }

    // 搜索POI
    public void searchPOI(String keyword, LatLonPoint point, RouteSearch.OnRouteSearchListener listener) {
        /*RouteSearch routeSearch = new RouteSearch(mContext);
        RouteSearch.Query query = new RouteSearch.Query(keyword, "", "");
        query.setFromAndTo(point, point);
        routeSearch.setQuery(query);
        routeSearch.searchRouteAsyn();
        routeSearch.setRouteSearchListener(listener);*/
    }

}
