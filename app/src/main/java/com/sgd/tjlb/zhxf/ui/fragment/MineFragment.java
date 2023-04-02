package com.sgd.tjlb.zhxf.ui.fragment;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.SwitchButton;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.VersionInfoBean;
import com.sgd.tjlb.zhxf.helper.DialogHelper;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AppVersionApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.other.AppConfig;
import com.sgd.tjlb.zhxf.ui.activity.AgreementActivity;
import com.sgd.tjlb.zhxf.ui.activity.CommunicationFlowActivity;
import com.sgd.tjlb.zhxf.ui.activity.MineEquipmentActivity;
import com.sgd.tjlb.zhxf.ui.activity.MyBankActivity;
import com.sgd.tjlb.zhxf.ui.activity.MyInsuranceActivity;
import com.sgd.tjlb.zhxf.ui.activity.user.PersonalDataActivity;
import com.sgd.tjlb.zhxf.ui.activity.StoreDataActivity;
import com.sgd.tjlb.zhxf.ui.activity.func.FuncActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.activity.setting.AboutActivity;
import com.sgd.tjlb.zhxf.ui.dialog.UpdateDialog;

/**
 * 消防宣传页
 */
public final class MineFragment extends TitleBarFragment<HomeActivity>
        implements SwitchButton.OnCheckedChangeListener {

    private TextView tv_btn;
    private LinearLayout llUserdata;//用户信息
    private ImageView ivUserhead;//用户头像
    private TextView tvUsername;//用户名
    private SettingBar sbStore;//门店信息
    private SettingBar sbContractmanagement;//合同管理
    private SettingBar sbMyservice;//我的客服
    private SettingBar sbAboutus;//关于我们
    private SettingBar sbPrivacy;//检查更新
    private TextView tvBtn;//检查更新
    private TextView tvMyEquipment;//我的设备
    private TextView tvMyInsurance;//我的保险
    private SettingBar sbPersonal;//个人信息
    private SettingBar sbAgreement;//个人信息
    private SettingBar sbBank;//我的银行

    private VersionInfoBean mVersionInfo;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        tv_btn = findViewById(R.id.tv_btn);

        llUserdata = (LinearLayout) findViewById(R.id.ll_btn_main_userdata);
        ivUserhead = (ImageView) findViewById(R.id.iv_main_userhead);
        tvUsername = (TextView) findViewById(R.id.tv_main_username);
        sbStore = (SettingBar) findViewById(R.id.sb_btn_main_store);
        sbContractmanagement = (SettingBar) findViewById(R.id.sb_btn_main_contractmanagement);
        sbMyservice = (SettingBar) findViewById(R.id.sb_btn_main_myservice);
        sbAboutus = (SettingBar) findViewById(R.id.sb_btn_main_aboutus);
        sbPrivacy = (SettingBar) findViewById(R.id.sb_btn_main_privacy);
        tvMyEquipment = (TextView) findViewById(R.id.tv_btn_main_myequipment);
        tvMyInsurance = (TextView) findViewById(R.id.tv_btn_main_myinsurance);
        sbPersonal = (SettingBar) findViewById(R.id.sb_btn_main_personal);
        sbAgreement = (SettingBar) findViewById(R.id.sb_btn_main_agreement);
        sbBank = (SettingBar) findViewById(R.id.sb_btn_main_bank);

        initListener();

    }

    private void initListener() {

        llUserdata.setOnClickListener(view -> startActivity(StoreDataActivity.class));
        //个人信息
        sbPersonal.setOnClickListener(view -> startActivity(PersonalDataActivity.class));
        //门店信息
        sbStore.setOnClickListener(view -> startActivity(StoreDataActivity.class));
        //合同管理
//        sbContractmanagement.setOnClickListener(view -> startActivity(ContractManagementActivity.class));
        sbContractmanagement.setOnClickListener(view -> CommunicationFlowActivity.start(getContext()));
        //隐私协议
        sbAgreement.setOnClickListener(view ->
//                BrowserActivity.start(getContext(), "https://github.com/getActivity/Donate")
                        startActivity(AgreementActivity.class)
        );
        //关于我们
        sbAboutus.setOnClickListener(view -> startActivity(AboutActivity.class));


        AppConfigBean appConfigBean = MMKVHelper.getInstance().findAppConfig();

        //我的客服
        sbMyservice.setOnClickListener(view -> DialogHelper.getInstance().showCallDialog(getContext(), appConfigBean.getApp_tel()));
        //我的保险
        tvMyInsurance.setOnClickListener(view -> startActivity(MyInsuranceActivity.class));
        //我的银行
        sbBank.setOnClickListener(view -> MyBankActivity.start(getContext()));
        //我的设备
        tvMyEquipment.setOnClickListener(view ->
                MineEquipmentActivity.start(getContext())
        );

        //框架活动
        tv_btn.setOnClickListener(v -> {
            FuncActivity.start(getContext());
        });

        sbPrivacy.setOnClickListener(v -> findAppVersion());
    }

    //检查更新
    private void checkUpdate() {

        int versionCode = mVersionInfo.getVersion_id();

        // 本地的版本码和服务器的进行比较
        if (versionCode > AppConfig.getVersionCode()) {
            new UpdateDialog.Builder(getContext())
                    .setVersionName(mVersionInfo.getVersion_no())
                    .setForceUpdate(false)
                    .setUpdateLog(mVersionInfo.getVersion_name())
                    .setDownloadUrl(mVersionInfo.getVersion_url())
                    .setFileMd5("560017dc94e8f9b65f4ca997c7feb326")
                    .show();
        } else {
            toast(R.string.update_no_update);
        }
    }

    @Override
    protected void initData() {

    }

    //获取版本信息
    private void findAppVersion() {
        mVersionInfo = MMKVHelper.getInstance().getVersionInfo();
        if (mVersionInfo != null) {
            checkUpdate();
            return;
        }

        EasyHttp.post(this)
                .api(new AppVersionApi())
                .request(new HttpCallback<HttpData<VersionInfoBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<VersionInfoBean> data) {
                        if (data.getData() != null) {
                            mVersionInfo = data.getData();
                            MMKVHelper.getInstance().saveVersionInfo(data.getData());
                            checkUpdate();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean checked) {

    }
}
