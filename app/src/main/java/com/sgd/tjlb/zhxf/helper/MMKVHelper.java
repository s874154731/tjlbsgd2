package com.sgd.tjlb.zhxf.helper;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hjq.gson.factory.GsonFactory;
import com.tencent.mmkv.MMKV;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.CityInfo;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.entity.ShopTypeInfo;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.entity.VersionInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: AndroidProject-master
 * @Package: com.sgd.tjlb.zhxf.helper
 * @ClassName: MMKVHelper
 * @Description: mmkv
 * @CreateDate: 2023/3/2/002 19:11
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/2/002 19:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MMKVHelper {

    public static final String KEY_FIRST_OPEN_APP = "KEY_FIRST_OPEN_APP";//首次使用app
    public static final String KEY_USERINFO = "KEY_FIRST_USERINFO";//用户信息
    public static final String KEY_SHOPINFO = "KEY_SHOPINFO";//门店信息
    public static final String KEY_CITIES = "KEY_CITIES";//城市信息
    public static final String KEY_SHOP_TYPE = "KEY_SHOP_TYPE";//店铺类型
    public static final String KEY_APP_CONFIG = "KEY_APP_CONFIG";//app常量
    public static final String KEY_POPULARIZE_TYPE = "KEY_POPULARIZE_TYPE";//宣传分类

    private final String[] userKeys = {KEY_USERINFO, KEY_SHOPINFO};

    //两级缓存--------------------------------
    private UserInfo mUserInfo;
    private List<CityInfo> mCities = new ArrayList<>();
    private List<ShopTypeInfo> mShopTypes = new ArrayList<>();
    private List<PopularizeTypeBean> mPopuTypes = new ArrayList<>();
    private AppConfigBean mAppConfig;//
    //两级缓存--------------------------------

    //一级缓存--------------------------------内存
    private VersionInfoBean mVersionInfo;

    private MMKV mmkv;

    private MMKVHelper() {
    }

    private static class SingletonHolder {
        private static final MMKVHelper instance = new MMKVHelper();
    }

    public static MMKVHelper getInstance() {
        return SingletonHolder.instance;
    }

    public void init() {
        getMMKV();
    }

    private MMKV getMMKV() {
        if (mmkv == null)
            mmkv = MMKV.defaultMMKV();
        return mmkv;
    }

    private void ChangeMMKVByUserID(String userID) {
        mmkv = MMKV.mmkvWithID(userID);
    }

    public boolean getBooleanByKey(String key) {
        return mmkv.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean boo) {
        mmkv.putBoolean(key, boo);
    }

    public String getStringByKey(String key) {
//        mmkv.getString(key, "");
        return getMMKV().decodeString(key);
    }

    public void putString(String key, String str) {
        getMMKV().encode(key, str);
//        getMMKV().putString(key, str).apply();
    }

    /**
     * 保存用户
     *
     * @param userInfo 用户
     */
    public void saveUserInfo(UserInfo userInfo) {
        String userJson = GsonFactory.getSingletonGson().toJson(userInfo);
        putString(KEY_USERINFO, userJson);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public UserInfo getUserInfo() {
        if (mUserInfo != null) {
            return mUserInfo;
        }
        String userJson = getStringByKey(KEY_USERINFO);
        if (TextUtils.isEmpty(userJson)) {
            return null;
        }
        mUserInfo = GsonFactory.getSingletonGson().fromJson(userJson, UserInfo.class);
        return mUserInfo;
    }

    /**
     * 保存门店信息
     *
     * @param shopInfo 门店信息
     */
    public void saveShopInfo(ShopInfo shopInfo) {
        String userJson = GsonFactory.getSingletonGson().toJson(shopInfo);
        putString(KEY_SHOPINFO, userJson);
    }

    /**
     * 获取门店信息
     *
     * @return 门店信息
     */
    public ShopInfo getShopInfo() {
        String userJson = getStringByKey(KEY_SHOPINFO);
        if (TextUtils.isEmpty(userJson)) {
            return null;
        }
        return GsonFactory.getSingletonGson().fromJson(userJson, ShopInfo.class);
    }


    /**
     * 清空用户信息,退出登录
     */
    public void clearUserInfo() {
        getMMKV().removeValueForKey(KEY_USERINFO);
        getMMKV().removeValueForKey(KEY_SHOPINFO);
    }

    public void clearAll() {
        mUserInfo = null;
        getMMKV().removeValuesForKeys(userKeys);
    }

    /**
     * 保存城市信息
     *
     * @param datas d
     */
    public void saveCities(List<CityInfo> datas) {
        String citiesJson = GsonFactory.getSingletonGson().toJson(datas);
        putString(MMKVHelper.KEY_CITIES, citiesJson);
    }

    /**
     * 获取城市信息
     *
     * @return s
     */
    public List<CityInfo> findCities() {
        if (mCities.size() > 0) {
            return mCities;
        }
        String citiesJson = getStringByKey(MMKVHelper.KEY_CITIES);

        if (TextUtils.isEmpty(citiesJson)) {
            return null;
        }

        List<CityInfo> tempCities = GsonFactory.getSingletonGson().fromJson(citiesJson, new TypeToken<List<CityInfo>>() {
        }.getType());

        if (tempCities == null) {
            return null;
        }

        mCities.clear();
        mCities.addAll(tempCities);
        return tempCities;
    }

    /**
     * 保存店铺行业类型list
     *
     * @param datas d
     */
    public void saveShopTypes(List<ShopTypeInfo> datas) {
        String typesJson = GsonFactory.getSingletonGson().toJson(datas);
        putString(MMKVHelper.KEY_SHOP_TYPE, typesJson);
    }

    /**
     * 获取城市信息
     *
     * @return s
     */
    public List<ShopTypeInfo> findShopTypes() {
        if (mShopTypes.size() > 0) {
            return mShopTypes;
        }
        String shopTypesJson = getStringByKey(MMKVHelper.KEY_SHOP_TYPE);

        if (TextUtils.isEmpty(shopTypesJson)) {
            return null;
        }

        List<ShopTypeInfo> tempList = GsonFactory.getSingletonGson().fromJson(shopTypesJson, new TypeToken<List<ShopTypeInfo>>() {
        }.getType());

        if (tempList == null) {
            return null;
        }

        mShopTypes.clear();
        mShopTypes.addAll(tempList);
        return tempList;
    }

    /**
     * 保存app常量
     *
     * @param datas d
     */
    public void saveAppConfig(AppConfigBean datas) {
        String tempJson = GsonFactory.getSingletonGson().toJson(datas);
        putString(MMKVHelper.KEY_APP_CONFIG, tempJson);
    }

    /**
     * 获取app常量
     *
     * @return s
     */
    public AppConfigBean findAppConfig() {
        if (mAppConfig != null && !TextUtils.isEmpty(mAppConfig.getApp_agree())) {
            return mAppConfig;
        }

        String tempJson = getStringByKey(MMKVHelper.KEY_APP_CONFIG);

        if (TextUtils.isEmpty(tempJson)) {
            return null;
        }

        AppConfigBean tempBean = GsonFactory.getSingletonGson().fromJson(tempJson, new TypeToken<AppConfigBean>() {
        }.getType());

        if (tempBean == null) {
            return null;
        }
        mAppConfig = tempBean;
        return tempBean;
    }

    /**
     * 保存宣传分类
     *
     * @param datas d
     */
    public void savePopularizeTypes(List<PopularizeTypeBean> datas) {
        String tempJson = GsonFactory.getSingletonGson().toJson(datas);
        putString(MMKVHelper.KEY_POPULARIZE_TYPE, tempJson);
    }

    /**
     * 获取宣传分类
     *
     * @return s
     */
    public List<PopularizeTypeBean> findPopularizeTypes() {
        if (mPopuTypes != null && mPopuTypes.size() > 0) {
            return mPopuTypes;
        }

        String tempJson = getStringByKey(MMKVHelper.KEY_POPULARIZE_TYPE);

        if (TextUtils.isEmpty(tempJson)) {
            return null;
        }

        List<PopularizeTypeBean> tempList = GsonFactory.getSingletonGson().fromJson(tempJson, new TypeToken<List<PopularizeTypeBean>>() {
        }.getType());

        if (tempList == null) {
            return null;
        }

        mPopuTypes.clear();
        mPopuTypes.addAll(tempList);
        return tempList;
    }

    /**
     * 在内存存储
     *
     * @param versionInfo 版本
     */
    public void saveVersionInfo(VersionInfoBean versionInfo) {
        this.mVersionInfo = versionInfo;
    }

    /**
     * 获取版本信息
     *
     * @return
     */
    public VersionInfoBean getVersionInfo() {
        return this.mVersionInfo;
    }
}
