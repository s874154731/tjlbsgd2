package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.TrafficfeesBean;
import com.sgd.tjlb.zhxf.http.api.CreateTrafficfessOrderApi;
import com.sgd.tjlb.zhxf.http.api.PayTrafficfessOrderApi;
import com.sgd.tjlb.zhxf.http.api.TrafficfessApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.utils.BigDecimalUtils;
import com.sgd.tjlb.zhxf.utils.GlideUtils;
import com.sgd.tjlb.zhxf.widget.InterceptLinearLayout;

import java.math.BigDecimal;

/**
 * 合同管理 activity
 */
public class CommunicationFlowActivity extends AppActivity {

    private AppCompatImageView mIvFlowImg;
    private TextView mTvFlowName;
    private TextView mTvFlowMoney;
    private ImageView mIvBtnFlowMinus;
    private RegexEditText mEtFlowBuynum;
    private ImageView mIvBtnFlowAdd;
    private LinearLayout mLlFlowOneyears;
    private AppCompatCheckBox mCbFlowOneyears;
    private TextView mTvFlowOneyearsmoney;
    private InterceptLinearLayout mLlFlowTwoyears;
    private AppCompatCheckBox mCbFlowTwoyears;
    private TextView mTvFlowTwoyearsmoney;
    private InterceptLinearLayout mLlFlowThreeyears;
    private AppCompatCheckBox mCbFlowThreeyears;
    private TextView mTvFlowThreeyearsmoney;
    private AppCompatButton mTvFlowPay;

    private TrafficfeesBean mTrafficfeesBean;//通讯流量

    String totalPrice; //总价

    public static void start(Context context) {
        Intent starter = new Intent(context, CommunicationFlowActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_communication_flow;
    }

    @Override
    protected void initView() {

        mIvFlowImg = (AppCompatImageView) findViewById(R.id.iv_communication_flow_img);
        mTvFlowName = (TextView) findViewById(R.id.tv_communication_flow_name);
        mTvFlowMoney = (TextView) findViewById(R.id.tv_communication_flow_money);
        mIvBtnFlowMinus = (ImageView) findViewById(R.id.iv_btn_communication_flow_minus);
        mEtFlowBuynum = (RegexEditText) findViewById(R.id.et_communication_flow_buynum);
        mIvBtnFlowAdd = (ImageView) findViewById(R.id.iv_btn_communication_flow_add);
        mLlFlowOneyears = (LinearLayout) findViewById(R.id.ll_communication_flow_oneyears);
        mCbFlowOneyears = (AppCompatCheckBox) findViewById(R.id.cb_communication_flow_oneyears);
        mTvFlowOneyearsmoney = (TextView) findViewById(R.id.tv_communication_flow_oneyearsmoney);
        mLlFlowTwoyears = (InterceptLinearLayout) findViewById(R.id.ll_communication_flow_twoyears);
        mCbFlowTwoyears = (AppCompatCheckBox) findViewById(R.id.cb_communication_flow_twoyears);
        mTvFlowTwoyearsmoney = (TextView) findViewById(R.id.tv_communication_flow_twoyearsmoney);
        mLlFlowThreeyears = (InterceptLinearLayout) findViewById(R.id.ll_communication_flow_threeyears);
        mCbFlowThreeyears = (AppCompatCheckBox) findViewById(R.id.cb_communication_flow_threeyears);
        mTvFlowThreeyearsmoney = (TextView) findViewById(R.id.tv_communication_flow_threeyearsmoney);
        mTvFlowPay = (AppCompatButton) findViewById(R.id.tv_communication_flow_pay);

        initListener();

    }

    private void initListener() {
        //加
        mIvBtnFlowAdd.setOnClickListener(view -> {
            int num = Integer.parseInt(mEtFlowBuynum.getText().toString().trim()) + 1;
            mEtFlowBuynum.setText(String.valueOf(num));
            sumUp();
        });
        //减
        mIvBtnFlowMinus.setOnClickListener(view -> {
            int buyNum = Integer.parseInt(mEtFlowBuynum.getText().toString().trim());
            if (buyNum <= 1) {
                return;
            }
            int num = buyNum - 1;
            mEtFlowBuynum.setText(String.valueOf(num));
            sumUp();
        });
        //一年
        mLlFlowOneyears.setOnClickListener(view -> buyNumYears(true, false, false));
        //二年
        mLlFlowTwoyears.setOnClickListener(view -> buyNumYears(false, true, false));
        //三年
        mLlFlowThreeyears.setOnClickListener(view -> buyNumYears(false, false, true));
        mTvFlowPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        });
    }

    /**
     * 选择预缴年限
     */
    private void buyNumYears(boolean oneYears, boolean twoYears, boolean threeYears) {
        mCbFlowOneyears.setChecked(oneYears);
        mCbFlowTwoyears.setChecked(twoYears);
        mCbFlowThreeyears.setChecked(threeYears);
        sumUp();
    }

    /**
     * 计算总价
     */
    private void sumUp() {
        if (mTrafficfeesBean == null) {
            return;
        }
        totalPrice = "0.00";
        String buyNum = mEtFlowBuynum.getText().toString().trim();
        BigDecimal money = mTrafficfeesBean.getProduct_price();
        if (mCbFlowOneyears.isChecked()) {
            money = mTrafficfeesBean.getProduct_price();
        }
        if (mCbFlowTwoyears.isChecked()) {
            money = mTrafficfeesBean.getProduct_price2();
        }
        if (mCbFlowThreeyears.isChecked()) {
            money = mTrafficfeesBean.getProduct_price3();
        }
        totalPrice = BigDecimalUtils.multiply(money, new BigDecimal(buyNum)).toString();
        mTvFlowMoney.setText("￥" + totalPrice);
    }

    @Override
    protected void initData() {
        findTrafficfees();
    }

    //获取通讯流量
    private void findTrafficfees() {
        EasyHttp.post(this)
                .api(new TrafficfessApi())
                .request(new HttpCallback<HttpData<TrafficfeesBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<TrafficfeesBean> data) {
                        if (data.getData() != null) {
                            mTrafficfeesBean = data.getData();
                            GlideUtils.loadRoundCircleImage(CommunicationFlowActivity.this,
                                    mTrafficfeesBean.getProduct_img(), mIvFlowImg, 4);
                            mTvFlowName.setText(mTrafficfeesBean.getProduct_title());
                            totalPrice = mTrafficfeesBean.getProduct_price().toString();
                            mTvFlowMoney.setText("￥" + mTrafficfeesBean.getProduct_price().toString());
                            mTvFlowOneyearsmoney.setText("￥" + mTrafficfeesBean.getProduct_price().toString());
                            mTvFlowTwoyearsmoney.setText("￥" + mTrafficfeesBean.getProduct_price2().toString());
                            mTvFlowThreeyearsmoney.setText("￥" + mTrafficfeesBean.getProduct_price3().toString());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //点击下订单
    private void createOrder() {
        if (mTrafficfeesBean == null) {
            toast("获取流量信息失败");
            return;
        }

        int count = Integer.parseInt(mEtFlowBuynum.getText().toString().trim());
        int years = 1;
        /*if (mCbFlowOneyears.isChecked()) {
            years = 1;
        }*/
        if (mCbFlowTwoyears.isChecked()) {
            years = 2;
        }
        if (mCbFlowThreeyears.isChecked()) {
            years = 3;
        }
        BigDecimal totalMoney = new BigDecimal(totalPrice);

        EasyHttp.post(this)
                .api(new CreateTrafficfessOrderApi()
                        .setProduct_id(mTrafficfeesBean.getProduct_id())
                        .setProduct_title(mTrafficfeesBean.getProduct_title())
                        .setNumber(count)
                        .setYears(years)
                        .setProduct_total(totalMoney)
                )
                .request(new HttpCallback<HttpData<CreateTrafficfessOrderApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<CreateTrafficfessOrderApi.Bean> data) {
                        if (data.getData() != null) {

                            String orderID = data.getData().order_id;

                            doPayOrder(orderID);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //支付订单
    private void doPayOrder(String orderID) {
        EasyHttp.post(this)
                .api(new PayTrafficfessOrderApi()
                        .setOrder_id(orderID)
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {

                        toast("支付成功");
                        finish();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
