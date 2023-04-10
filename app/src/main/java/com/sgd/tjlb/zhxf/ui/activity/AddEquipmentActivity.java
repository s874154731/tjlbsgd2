package com.sgd.tjlb.zhxf.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.hjq.http.EasyHttp;
import com.hjq.http.config.IRequestApi;
import com.hjq.http.listener.HttpCallback;
import com.hjq.permissions.XXPermissions;
import com.hjq.widget.view.ClearEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.aop.Log;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.http.api.AddEquipmentApi;
import com.sgd.tjlb.zhxf.http.api.FindEquipmentApi;
import com.sgd.tjlb.zhxf.http.api.UpdateEquipmentApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.utils.StringUtil;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 添加或修改设备
 */
public final class AddEquipmentActivity extends AppActivity {

    private static final String TYPE_CID = "TYPE_CID";//客户端id
    private static final String TYPE_DEVICE_ID = "TYPE_DEVICE_ID";//设备id
    private static final int REQUEST_CODE_SCAN = 5;

    private ClearEditText mEditText;
    private AppCompatButton mSubBtn;

    private String mCsUserID;//客户端id
    private String mDeviceID;//设备id
    private EquipmentInfo mEquipmentInfo;//设备信息

    /***
     * 进入
     * @param context 上下
     * @param cID 客户端id
     * @param deviceID 设备id
     */
    @Log
    public static void start(Context context, String cID, String deviceID) {
        Intent intent = new Intent(context, AddEquipmentActivity.class);
        intent.putExtra(TYPE_CID, cID);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                String id = "";
                try {
                    id = StringUtil.subString(content, "ID:", "IMEI");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mEditText.setText(id.trim());
            } else {
                toast("无效二维码");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_equipment_activity;
    }

    @Override
    protected void initView() {
        mEditText = findViewById(R.id.et_equipment_sn);
        mSubBtn = findViewById(R.id.btn_applyforbank_apply);
        initListener();
    }

    private void initListener() {
        mSubBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mEditText.getText().toString())) {
                mEditText.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                return;
            }
            submitAddEquipment();
        });
    }

    private void submitAddEquipment() {
        String snCode = mSubBtn.getText().toString();

        IRequestApi submitApi = null;
        if (!TextUtils.isEmpty(mDeviceID) && mEquipmentInfo != null) {
            submitApi = new UpdateEquipmentApi().setDeviceID(mDeviceID).setDeviceSn(snCode);
        } else {
            submitApi = new AddEquipmentApi().setShopid(mCsUserID).setDeviceSn(snCode);
        }

        EasyHttp.post(this)
                .api(submitApi)
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        toast("保存成功");
                        finish();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onRightClick(View view) {
        XXPermissions.with(this)
                .permission(Manifest.permission.CAMERA)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                // 如果不需要在后台使用定位功能，请不要申请此权限
//                .permission(Permission.ACCESS_BACKGROUND_LOCATION)
//                .interceptor(new PermissionInterceptor())
                .request((permissions, allGranted) -> {
                    if (!allGranted) {
                        toast("缺少必要权限无法打开扫码");
                        return;
                    }

                    openSSCanPage();
                });

    }

    //打开扫码页面
    private void openSSCanPage() {
        Intent intent = new Intent(AddEquipmentActivity.this, CaptureActivity.class);
        /*ZxingConfig是配置类
         *可以设置是否显示底部布局，闪光灯，相册，
         * 是否播放提示音  震动
         * 设置扫描框颜色等
         * 也可以不传这个参数
         * */
        ZxingConfig config = new ZxingConfig();
        config.setPlayBeep(true);//是否播放扫描声音 默认为true
        config.setShake(true);//是否震动  默认为true
        config.setDecodeBarCode(true);//是否扫描条形码 默认为true
        config.setReactColor(R.color.white);//设置扫描框四个角的颜色 默认为白色
//        config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
//        config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    protected void initData() {
        mCsUserID = getString(TYPE_CID);
        mDeviceID = getString(TYPE_DEVICE_ID);

        if (!TextUtils.isEmpty(mDeviceID)) {
            getDeviceInfo();
        }
        refreshUI();
    }

    //获取设备信息
    private void getDeviceInfo() {
        EasyHttp.post(this)
                .api(new FindEquipmentApi()
                        .setDeviceID(mDeviceID)
                )
                .request(new HttpCallback<HttpData<EquipmentInfo>>(this) {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSucceed(HttpData<EquipmentInfo> data) {
                        mEquipmentInfo = data.getData();
                        refreshUI();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //刷新UI
    private void refreshUI() {
        //设置标题
        String title = TextUtils.isEmpty(mDeviceID) ? getResources().getString(R.string.text_tip_add_equipment) : getResources().getString(R.string.text_tip_update_equipment);
        setTitle(title);

        if (mEquipmentInfo != null) {
            mEditText.setText(mEquipmentInfo.getDevice_sn());
        }
    }
}