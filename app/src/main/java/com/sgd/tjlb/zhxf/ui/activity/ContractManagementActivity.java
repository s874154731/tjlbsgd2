package com.sgd.tjlb.zhxf.ui.activity;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.TrafficfeesBean;
import com.sgd.tjlb.zhxf.http.api.CreateTrafficfessOrderApi;
import com.sgd.tjlb.zhxf.http.api.PayTrafficfessOrderApi;
import com.sgd.tjlb.zhxf.http.api.TrafficfessApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.ContractAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;

import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 合同管理 activity
 */
public class ContractManagementActivity extends AppActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvContractManagement;

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private ContractAdapter mAdapter;
    private TrafficfeesBean mTrafficfeesBean;//通讯流量

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contract_management;
    }

    @Override
    protected void initView() {

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_contract_management);
        mRvContractManagement = (RecyclerView) findViewById(R.id.rv_contract_management);

        initSmartRefreshLayout();
        initRecyclerView();

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

        mAdapter = new ContractAdapter(this);
        mRvContractManagement.setLayoutManager(new LinearLayoutManager(this));
        mRvContractManagement.setAdapter(mAdapter);

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

        int count = 1;
        int years = 1;
        BigDecimal totalMoney = new BigDecimal(100);

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
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
