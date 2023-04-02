package com.sgd.tjlb.zhxf.ui.activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.PurchasedTrafficfessBean;
import com.sgd.tjlb.zhxf.entity.TrafficfeesBean;
import com.sgd.tjlb.zhxf.http.api.PurchasedTrafficfessListApi;
import com.sgd.tjlb.zhxf.http.api.TrafficfessApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.MyInsuranceAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.List;

/**
 * 我的保险 activity
 */
public class MyInsuranceActivity extends AppActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvMyinsurance;
    private MyInsuranceAdapter mAdapter;

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private TrafficfeesBean mTrafficfeesBean;//通讯流量

    @Override
    protected int getLayoutId() {
        return R.layout.activity_myinsurance;
    }

    @Override
    protected void initView() {

        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_myinsurance);
        mRvMyinsurance = (RecyclerView) findViewById(R.id.rv_myinsurance);

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
                findTrafficfessList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下滑
                mPage = 1;
                mRefreshType = ConstantUtil.REFRESH_FIRST;
                findTrafficfessList();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new MyInsuranceAdapter(this);
        mRvMyinsurance.setLayoutManager(new LinearLayoutManager(this));
        mRvMyinsurance.setAdapter(mAdapter);
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
                            findTrafficfessList();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取通讯流量list
    private void findTrafficfessList() {
        if (mTrafficfeesBean == null) {
            toast("获取流量信息失败");
            return;
        }

        EasyHttp.post(this)
                .api(new PurchasedTrafficfessListApi()
                        .setPage(mPage)
                        .setProduct_id(mTrafficfeesBean.getProduct_id())
                )
                .request(new HttpCallback<HttpData<List<PurchasedTrafficfessBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<PurchasedTrafficfessBean>> data) {
                        if (mPage == 1) {
                            mAdapter.initData(data.getData());
                        } else {
                            mAdapter.addDatas(data.getData());
                        }
                        //关闭刷新
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
