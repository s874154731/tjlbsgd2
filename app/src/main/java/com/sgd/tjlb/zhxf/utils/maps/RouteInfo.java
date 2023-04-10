package com.sgd.tjlb.zhxf.utils.maps;

/**
 * @ProjectName: tjlbsgd2
 * @Package: com.sgd.tjlb.zhxf.utils.maps
 * @ClassName: RouteInfo
 * @Description: 路线等封装
 * @CreateDate: 2023/4/10/010 11:55
 * @UpdateUser: shi
 * @UpdateDate: 2023/4/10/010 11:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RouteInfo {
    /**
     * 两点之间的距离
     */
    private float distance;

    /**
     * 预计行车时间
     */

    private int duration;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "distance=" + distance +
                ", duration=" + duration +
                '}';
    }
}
