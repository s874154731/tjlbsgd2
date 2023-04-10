package com.sgd.tjlb.zhxf.utils.maps;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import java.util.List;

/**
 * @ProjectName: tjlbsgd2
 * @Package: com.sgd.tjlb.zhxf.utils.maps
 * @ClassName: ILbsLayer
 * @Description: java类作用描述
 * @CreateDate: 2023/4/10/010 11:55
 * @UpdateUser: shi
 * @UpdateDate: 2023/4/10/010 11:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ILbsLayer {
    /**
     *  获取地图
     */
    View getMapView();

    /**
     *  设置位置变化监听
     */
    void setLocationChangeListener(CommonLocationChangeListener locationChangeListener);

    /**
     *  设置定位图标
     */
    void setLocationRes(int res);
    /**
     *  添加，更新标记点，包括位置、角度（通过 id 识别）
     */
    void addOrUpdateMarker(LocationInfo locationInfo, Bitmap bitmap);

    /**
     * 添加多窗体气泡效果
     */
    void addPoiOverlay(List<LocationInfo> locationInfo);

    /**
     *   获取当前城市
     */
    String getCity();

    /**
     * 联动搜索附近的位置
     */
    void poiSearch(String key, OnSearchedListener listener);

    /**
     * 绘制两点之间行车路径
     * @param start 开始的位置
     * @param end 结束的位置
     * @param color 颜色
     * @param listener 事件
     */
    void driverRoute(LocationInfo start,
                     LocationInfo end,
                     int color,
                     OnRouteCompleteListener listener);

    /**
     *  生命周期函数
     */

    void onCreate(Bundle state);
    void onResume();
    void onSaveInstanceState(Bundle outState);
    void onPause();
    void onDestroy();

    void clearAllMarkers();


    interface CommonLocationChangeListener {
        void onLocationChanged(LocationInfo locationInfo);
        void onLocation(LocationInfo locationInfo);
    }
    /**
     * POI 搜索结果监听器
     */
    interface OnSearchedListener {
        void onSearched(List<LocationInfo> results);

        void onError(int rCode);
    }
    /**
     * 路径规划完成监听
     */
    interface OnRouteCompleteListener {
        void onComplete(RouteInfo result);
    }

    /**
     *  移动相机到两点之间的视野范围
     */
    void moveCamera(LocationInfo locationInfo1,
                    LocationInfo locationInfo2);

    /**
     *  移动动相机到某个点，
     * @param locationInfo
     * @param scale 缩放系数
     */
    void moveCameraToPoint(LocationInfo locationInfo, int scale);

}
