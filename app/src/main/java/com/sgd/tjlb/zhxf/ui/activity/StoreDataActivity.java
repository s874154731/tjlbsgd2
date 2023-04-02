package com.sgd.tjlb.zhxf.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.CityInfo;
import com.sgd.tjlb.zhxf.entity.LocationBean;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.entity.ShopTypeInfo;
import com.sgd.tjlb.zhxf.entity.UserInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AddShopApi;
import com.sgd.tjlb.zhxf.http.api.AdressCitiesApi;
import com.sgd.tjlb.zhxf.http.api.GetShopApi;
import com.sgd.tjlb.zhxf.http.api.ShopTypeListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.dialog.AddressDialog;
import com.sgd.tjlb.zhxf.ui.dialog.MenuDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 门店信息 activity
 */
public class StoreDataActivity extends AppActivity {

    private ClearEditText etStorename;
    private SettingBar sbBtnIndustry;
    private ClearEditText etPrincipal;
    private ClearEditText etPrincipalTel;//电话
    private RegexEditText et_store_data_code;//销售编码
    private SettingBar sbBtnLocation;
//    private SettingBar sbBtnMerchantaddress;
    private RegexEditText et_store_data_merchantaddress;
    private LinearLayout layout_btn_chooseArea;
    private RegexEditText etFloorspace;
    private RegexEditText etOtherdetails;
    private AppCompatButton btnCommit;

    /**
     * 行业数组
     */
    private List<ShopTypeInfo> mShopTypeList = new ArrayList<>();

    /**
     * 城市信息
     */
    private List<CityInfo> mCities = new ArrayList<>();
    private List<List<CityInfo>> mCountyList = new ArrayList<>();

    private ShopTypeInfo mShopType;
    private ShopInfo mSubmitShopInfo;//待提交店铺信息
    private int mCityID, mCountyID;//城市id，区id
    private double mLongitude, mLatitude;//经纬度
    private String mAddress;//具体位置
    private LocationBean locationBean;
    private ShopInfo mShopInfo;

    //新地区选择
    private OptionsPickerView<CityInfo> mAreaOptions;
    private int mOptions1, mOptions2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_data;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView() {

        etStorename = (ClearEditText) findViewById(R.id.et_store_data_storename);
        sbBtnIndustry = (SettingBar) findViewById(R.id.sb_btn_store_data_industry);
        etPrincipal = (ClearEditText) findViewById(R.id.et_store_data_principal);
        etPrincipalTel = (ClearEditText) findViewById(R.id.et_store_data_principal_tel);
        sbBtnLocation = (SettingBar) findViewById(R.id.sb_btn_store_data_location);
//        sbBtnMerchantaddress = (SettingBar) findViewById(R.id.sb_btn_store_data_merchantaddress);
        etFloorspace = (RegexEditText) findViewById(R.id.et_store_data_floorspace);
        etOtherdetails = findViewById(R.id.et_store_data_otherdetails);
        et_store_data_code = findViewById(R.id.et_store_data_code);
        et_store_data_merchantaddress = findViewById(R.id.et_store_data_merchantaddress);
        layout_btn_chooseArea = findViewById(R.id.layout_btn_chooseArea);
        btnCommit = (AppCompatButton) findViewById(R.id.btn_store_data_commit);

        initListener();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initListener() {
        //选择行业
        sbBtnIndustry.setOnClickListener(view -> showShopTypeDialog());
        //选择地区
        sbBtnLocation.setOnClickListener(view -> {
//            showChooseAddress();
            showChooseAddress2();
        });

        btnCommit.setOnClickListener(view -> {
            doAddShopInfo();
        });
        layout_btn_chooseArea.setOnClickListener(view -> {
            MapActivity.start(this, locationBean -> {
                mAddress = locationBean.getName();
//                mAddress = locationBean.getDetailLocal();
                mLongitude = locationBean.getLongitude();
                mLatitude = locationBean.getLatitude();


                et_store_data_merchantaddress.setText(TextUtils.isEmpty(mAddress) ? "" : mAddress);
//                sbBtnMerchantaddress.setRightText(TextUtils.isEmpty(mAddress) ? "" : mAddress);
            });
        });
    }

    //展示城市列表
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showChooseAddress() {
        new AddressDialog.Builder(this)
                .setTitle("选择地区")
                // 设置默认省份
                .setProvince("河北省")
                // 设置默认城市（必须要先设置默认省份）
//                        .setCity(mCity)
                // 不选择县级区域
//                .setIgnoreArea()
                .setListener((dialog, province, city, area) -> {
                    String address = province + city + area;
                    //匹配地址 获取城市id，区id
                    matchingArea(city, area);
                    sbBtnLocation.setRightText(address);
                })
                .show();
    }

    //展示城市列表
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showChooseAddress2() {
        if (mCities.size() == 0 || mCountyList.size() == 0)
            return;

        if (mAreaOptions == null) {
            mAreaOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
                mOptions1 = options1;
                mOptions2 = options2;
                //城市
                String cityName = mCities.get(options1).getTitle();
                mCityID = mCities.get(options1).getValue();
                //区县
                String countyName = mCountyList.get(options1).get(options2).getTitle();
                mCountyID = mCountyList.get(options1).get(options2).getValue();
                //设置显示
                sbBtnLocation.setRightText(cityName + countyName);
            })
                    .setTitleText(getResources().getString(R.string.address_title))//标题文字
                    .setCancelText("取消")//取消按钮文字
                    .setSubmitText(getResources().getString(R.string.common_confirm))//确认按钮文字
                    .setLineSpacingMultiplier(3.0f)//间距
                    .setItemVisibleCount(7)//设置最大可见数目
                    .isAlphaGradient(true)//设置透明度是否渐变
                    .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                    .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                    .setTextColorCenter(Color.parseColor("#333333"))//滚轮文字颜色
                    .setTextColorOut(Color.parseColor("#333333"))//滚轮外文字颜色
                    .setTitleColor(Color.parseColor("#333333"))//标题文字颜色
                    .setSubmitColor(Color.parseColor("#FF9113"))//确定按钮文字颜色
                    .setCancelColor(Color.parseColor("#999999"))//取消按钮文字颜色
                    .setDividerColor(Color.parseColor("#EFEFEF"))//分割线颜色
                    .setTitleSize(14)//标题文字大小
                    .setSubCalSize(12)//取消/确定文字大小
                    .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                    .setContentTextSize(14)//滚轮文字大小
                    .setOutSideCancelable(true)
                    .setLabels("", "", "")
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .isDialog(false)//是否显示为对话框样式
                    .isRestoreItem(true)//切换选项时，是否还原第一项
                    .build();
        }
        mAreaOptions.setPicker(mCities, mCountyList);//二级选择器
        //默认选中项
        mAreaOptions.setSelectOptions(mOptions1, mOptions2);
        mAreaOptions.show();
    }

    /**
     * 匹配地址 获取城市id，区id
     *
     * @param city   市
     * @param county 区
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void matchingArea(String city, String county) {

        if (mCities == null || mCities.size() == 0)
            return;
        //获取选中的城市
        CityInfo finalCity = mCities.stream().filter(tempCity -> {
            return city.contains(tempCity.getTitle());
        }).findFirst().orElse(null);

        if (finalCity != null) {

            //所选城市的区集合
            List<CityInfo> tempCounty = finalCity.getCities();
            //获取选中的区
            CityInfo finalCounty = tempCounty.stream().filter(tempCity -> {
                return county.contains(tempCity.getTitle());
            }).findFirst().orElse(null);

            //设置城市id，区id
            mCityID = finalCity.getValue();
            mCountyID = finalCounty != null ? finalCounty.getValue() : 0;
        }
    }

    /**
     * 根据城市id,区id 获取具体城市、区
     *
     * @param cityID   城市id
     * @param countyID 区id
     * @return 城市+区
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getMatchingArea(int cityID, int countyID) {

        if (mCities == null)
            return "";

        CityInfo finalCity = mCities.stream().filter(tempCity -> {
            return cityID == tempCity.getValue();
        }).findFirst().orElse(null);

        if (finalCity != null) {
            //所选城市的区集合
            List<CityInfo> tempCounty = finalCity.getCities();
            //获取选中的区
            CityInfo finalCounty = tempCounty.stream().filter(tempCity -> {
                return countyID == tempCity.getValue();
            }).findFirst().orElse(null);

            if (finalCounty != null) {
                return finalCity.getTitle() + finalCounty.getTitle();
            }
        } else {
            return "";
        }
        return "";
    }


    //添加用户信息
    private void doAddShopInfo() {

        if (TextUtils.isEmpty(etStorename.getText().toString())) {
            etStorename.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_store_data_storename));
            return;
        }
        //店铺名称
        mSubmitShopInfo.setShop_name(etStorename.getText().toString());

        if (mShopType == null || TextUtils.isEmpty(sbBtnIndustry.getRightView().getText().toString())) {
            sbBtnIndustry.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast("请选择所属行业");
            return;
        }
        //店铺行业id
        mSubmitShopInfo.setShop_type_id(mShopType.getShop_type_id());

        if (TextUtils.isEmpty(etPrincipal.getText().toString())) {
            etPrincipal.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_store_data_principal));
            return;
        }
        //紧急联系人
        mSubmitShopInfo.setContact(etPrincipal.getText().toString());

        if (TextUtils.isEmpty(etPrincipalTel.getText().toString())) {
            etPrincipalTel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_store_data_principal_tel));
            return;
        }
        //紧急联系人电话
        mSubmitShopInfo.setContact_tel(etPrincipalTel.getText().toString());

        if (TextUtils.isEmpty(sbBtnLocation.getRightView().getText().toString())) {
            sbBtnLocation.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast("请选择所属地区");
            return;
        }
        //所属地区
        mSubmitShopInfo.setCity_id(mCityID);//城市
        mSubmitShopInfo.setArea_id(mCountyID);//区县

        if (TextUtils.isEmpty(et_store_data_merchantaddress.getText().toString())
                || TextUtils.isEmpty(mAddress)) {
            et_store_data_merchantaddress.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast("请选择店铺地址");
            return;
        }
        //具体位置
        mSubmitShopInfo.setLongitude(mLongitude);//经度
        mSubmitShopInfo.setLatitude(mLatitude);//纬度
        mSubmitShopInfo.setAddress(mAddress);

        if (TextUtils.isEmpty(etFloorspace.getText().toString())) {
            etFloorspace.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_store_data_floorspace));
            return;
        }
        //房屋面积
        mSubmitShopInfo.setShop_area(etFloorspace.getText().toString());

        if (TextUtils.isEmpty(et_store_data_code.getText().toString())) {
            et_store_data_code.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast(getResources().getString(R.string.input_store_data_code));
            return;
        }
        //销售编码
        mSubmitShopInfo.setSell_no(et_store_data_code.getText().toString());

        if (TextUtils.isEmpty(etOtherdetails.getText().toString())) {
            etOtherdetails.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast("请输入店铺详情");
            return;
        }
        //销售编码
        mSubmitShopInfo.setShop_info(etOtherdetails.getText().toString());

        //提交店铺信息
        submitShopInfo();
    }

    private void showShopTypeDialog() {
        // 居中选择框
        new MenuDialog.Builder(this)
                .setGravity(Gravity.CENTER)
                // 设置 null 表示不显示取消按钮
                //.setCancel(null)
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setList(mShopTypeList)
                .setListener((MenuDialog.OnListener<ShopTypeInfo>) (dialog, position, shopType) -> {
                    mShopType = shopType;
                    sbBtnIndustry.setRightText(shopType.getShop_type_name());
                })
                .show();
    }

    @Override
    protected void initData() {
        mSubmitShopInfo = new ShopInfo();
        findShopTypes();
        findCities();
        findShopInfo();
    }

    //获取店铺信息
    private void findShopInfo() {
        EasyHttp.post(this)
                .api(new GetShopApi())
                .request(new HttpCallback<HttpData<ShopInfo>>(this) {

                    @Override
                    public void onSucceed(HttpData<ShopInfo> data) {
                        if (data.getData() != null) {

                            mShopInfo = data.getData();
                            refreshUI();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    private void refreshUI() {
        if (mShopInfo != null) {
            //店铺名称
            etStorename.setText(mShopInfo.getShop_name());
            //店铺行业
            if (mShopTypeList != null && mShopTypeList.size() > 0) {
                ShopTypeInfo shopType = mShopTypeList.stream()
                        .filter(type -> type.getShop_type_id() == mShopInfo.getShop_type_id())
                        .findFirst().orElse(null);
                if (shopType != null) {
                    mShopType = shopType;
                    sbBtnIndustry.setRightText(shopType.getShop_type_name());
                }
            }
            //负责人
            etPrincipal.setText(mShopInfo.getContact());
            //电话
            etPrincipalTel.setText(mShopInfo.getContact_tel());
            //编码
            et_store_data_code.setText(mShopInfo.getSell_no());
            //所选地区
            mCityID = mShopInfo.getCity_id();
            mCountyID = mShopInfo.getArea_id();
            String area = getMatchingArea(mCityID, mCountyID);
            sbBtnLocation.setRightText(area);
            //设置选择器id
            initAreaOptions();
//            //店铺具体地址
            mLatitude = mShopInfo.getLatitude();
            mLongitude = mShopInfo.getLongitude();
            mAddress = mShopInfo.getAddress();
            et_store_data_merchantaddress.setText(mAddress);
            //面积
            etFloorspace.setText(mShopInfo.getShop_area());
            //其他信息
            etOtherdetails.setText(mShopInfo.getShop_info());
        }
    }

    //获取城市信息
    private void findCities() {
        mCities.clear();

        List<CityInfo> cityInfos = MMKVHelper.getInstance().findCities();
        /*if (cityInfos != null && cityInfos.size() > 0) {
            mCities.addAll(cityInfos);
            return;
        }*/

        EasyHttp.post(this)
                .api(new AdressCitiesApi())
                .request(new HttpCallback<HttpData<List<CityInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<CityInfo>> data) {

                        if (data.getData() == null)
                            return;

                        //过滤掉二级菜单是空的情况
                        for (CityInfo city : data.getData()){
                            if (city.getCities() == null){
                                continue;
                            }
                            mCities.add(city);
                        }
                        MMKVHelper.getInstance().saveCities(data.getData());
                        refreshUI();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //设置地区选择对应id
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initAreaOptions() {
        mCountyList.clear();

        /*for (int i = 0; i < mCities.size(); i++) {
            mCountyList.add(mCities.get(i).getCities());
        }*/
        for (CityInfo tempCity : mCities) {
            if (tempCity.getCities() == null){
                continue;
            }
            mCountyList.add(tempCity.getCities());
        }

        //获取默认城市
        CityInfo defaultCity = mCities.stream()
                .filter(tempCity -> tempCity.getValue() == mCityID)
                .findFirst()
                .orElse(null);

        if (defaultCity != null){
            //获取默认城市的position
            mOptions1 = mCities.indexOf(defaultCity);

            if (defaultCity.getCities() == null)
                return;

            //获取默认区县
            CityInfo defaultCounty = defaultCity.getCities().stream()
                    .filter(tempCity -> tempCity.getValue() == mCountyID)
                    .findFirst()
                    .orElse(null);

            if (defaultCounty != null){
                //获取默认城市的position
                mOptions2 = defaultCity.getCities().indexOf(defaultCounty);
            }

        }
    }

    //获取门店行业信息
    private void findShopTypes() {

        mShopTypeList.clear();

        List<ShopTypeInfo> tempShopTypes = MMKVHelper.getInstance().findShopTypes();
        /*if (tempShopTypes != null && tempShopTypes.size() > 0) {
            mShopTypeList.addAll(tempShopTypes);
            return;
        }*/

        EasyHttp.post(this)
                .api(new ShopTypeListApi())
                .request(new HttpCallback<HttpData<List<ShopTypeInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<ShopTypeInfo>> data) {
                        sbBtnIndustry.setVisibility(View.VISIBLE);

                        if (data.getData() == null)
                            return;
                        List<ShopTypeInfo> tempList = data.getData();
                        //存内存
                        mShopTypeList.addAll(tempList);
                        //保存到本地
                        MMKVHelper.getInstance().saveShopTypes(tempList);
                        refreshUI();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        sbBtnIndustry.setVisibility(View.GONE);
                    }
                });
    }

    //提交店铺信息
    private void submitShopInfo() {
        EasyHttp.post(this)
                .api(new AddShopApi(mSubmitShopInfo))
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        toast("添加成功");
                        setResult(RESULT_OK);

                        UserInfo self = MMKVHelper.getInstance().getUserInfo();
                        self.setCity_id(mCityID);
                        self.setArea_id(mCountyID);
                        MMKVHelper.getInstance().saveUserInfo(self);

                        finish();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
