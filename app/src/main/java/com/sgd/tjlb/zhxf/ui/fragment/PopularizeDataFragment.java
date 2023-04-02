package com.sgd.tjlb.zhxf.ui.fragment;

import android.os.Bundle;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.PopularizeData;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.http.api.PopularizeListApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.adapter.PopularizeAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PopularizeDataFragment extends TitleBarFragment {

    public static String KEY_TYPE = "KEY_TYPE";

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRVPopularize;
    private PopularizeAdapter mAdapter;

    private PopularizeTypeBean mTypeBean;
    private int mPopularizeTypeID;//宣传分类id

    /**
     * 入口
     *
     * @param tabType 类型
     * @return PopularizeDataFragment
     */
    public static PopularizeDataFragment getInstance(PopularizeTypeBean tabType) {
        PopularizeDataFragment fragment = new PopularizeDataFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_TYPE, tabType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_popularize_data;
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_popular);
        mRVPopularize = (RecyclerView) findViewById(R.id.rv_equipment);

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
        //消防
        mAdapter = new PopularizeAdapter(getContext(),true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        mRVPopularize.setLayoutManager(layoutManager2);
        mRVPopularize.setAdapter(mAdapter);
    }


    @Override
    protected void initData() {
        mTypeBean = (PopularizeTypeBean) getArguments().getSerializable(KEY_TYPE);
        mPopularizeTypeID = mTypeBean.getNews_type_id();
        findPopularizeDatas();
    }


    /**
     * 根据id 获取宣传list
     */
    private void findPopularizeDatas() {
        EasyHttp.post(this)
                .api(new PopularizeListApi().setNewsTypeId(mPopularizeTypeID).setPage(mPage))
                .request(new HttpCallback<HttpData<List<PopularizeData>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<PopularizeData>> data) {
                        if (data.getData() != null) {
                            mAdapter.initData(data.getData());
                        }
                        //关闭刷新
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        //关闭刷新
                        SmartRefreshLayoutUtil.complete(mRefreshLayout);
                    }
                });
    }
}
