package com.sgd.tjlb.zhxf.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppFragment;
import com.sgd.tjlb.zhxf.entity.AppConfigBean;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.entity.HomeBannerData;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;
import com.sgd.tjlb.zhxf.entity.PopularizeData;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.AppConfigApi;
import com.sgd.tjlb.zhxf.http.api.EquipmentInfoListApi;
import com.sgd.tjlb.zhxf.http.api.GetShopApi;
import com.sgd.tjlb.zhxf.http.api.HomeMsgsApi;
import com.sgd.tjlb.zhxf.http.api.PopularizeListApi;
import com.sgd.tjlb.zhxf.http.api.PopularizeTypeApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.NoticeActivity;
import com.sgd.tjlb.zhxf.ui.activity.NoticeDetailActivity;
import com.sgd.tjlb.zhxf.ui.activity.StoreDataActivity;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;
import com.sgd.tjlb.zhxf.ui.adapter.EquipmentAdapter;
import com.sgd.tjlb.zhxf.ui.adapter.PopularizeAdapter;
import com.sgd.tjlb.zhxf.utils.ConstantUtil;
import com.sgd.tjlb.zhxf.utils.SmartRefreshLayoutUtil;
import com.sgd.tjlb.zhxf.widget.NoticeTextView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 消防宣传页
 */
public final class HomeFragment extends AppFragment<HomeActivity> {

    private SmartRefreshLayout mRefreshLayout;
    private Banner mBanner;
    private CircleIndicator mCircleIndicator;
    private RecyclerView mRVPopularize, mRVShopEquipment;
    private Group mGroup, mGroupShop;
    private TextView mTvShopName, mTvBtnAddShop, mTvNoticeMore;//店铺名，添加店铺,更多消息
    private NoticeTextView mNoticeTv;//通知消息

    private BannerImageAdapter<HomeBannerData> mBannerAdapter;
    private PopularizeAdapter mAdapterPopularize;
    private EquipmentAdapter mAdapterEquipment;//店铺设备

    private final int mPageSize = ConstantUtil.PAGE_SIZE_20;
    private int mRefreshType = ConstantUtil.REFRESH_INIT;
    private int mPage = ConstantUtil.PAGE_INDEX;

    private List<HomeBannerData> mBannerDatas = new ArrayList<>();
    private List<String> mNoticeMessageDatas = new ArrayList<>();
    private int mPopularizeTypeID;//宣传分类id

    private ShopInfo mShopInfo;
    private AppConfigBean mAppConfig;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mRefreshLayout = findViewById(R.id.srl_home);
        mBanner = findViewById(R.id.banner_home);
        mCircleIndicator = findViewById(R.id.cv_home_indicator);
        mRVPopularize = findViewById(R.id.rv_equipment);
        mGroup = findViewById(R.id.home_group);

        mGroupShop = findViewById(R.id.group_shop);
        mRVShopEquipment = findViewById(R.id.rv_shop_equipment);
        mTvShopName = findViewById(R.id.tv_shop_name);
        mTvBtnAddShop = findViewById(R.id.tv_btn_add_shop);

        mTvNoticeMore = findViewById(R.id.tv_notice_more);
        mNoticeTv = findViewById(R.id.ntv_notice);

        initBanner();
        initSmartRefreshLayout();
        initRecyclerView();
        initListener();
    }

    private void initListener() {
        mTvBtnAddShop.setOnClickListener(v -> {
//            ToastUtils.show("添加店铺");
//            ShopInfo shopInfo = createShopInfo();
//            MMKVHelper.getInstance().saveShopInfo(shopInfo);
//            initData();

            startActivity(StoreDataActivity.class);

        });
        mTvNoticeMore.setOnClickListener(v -> {
            NoticeActivity.start(getContext(), "aaa");
        });
    }

    private void initBanner() {
        mBannerAdapter = new BannerImageAdapter<HomeBannerData>(mBannerDatas) {
            @Override
            public void onBindView(BannerImageHolder holder, HomeBannerData data, int position, int size) {
                //图片加载自己实现
//                GlideUtils.loadImage(holder.itemView.getContext(),data.getImageUrl(),holder.imageView);
                Glide.with(holder.itemView)
                        .load(data.getImageUrl())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                        .into(holder.imageView);
            }
        };
        mBanner.setAdapter(mBannerAdapter)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(mCircleIndicator, false);
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
        //设备
        mAdapterEquipment = new EquipmentAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRVShopEquipment.setLayoutManager(layoutManager);
        mRVShopEquipment.setAdapter(mAdapterEquipment);

        //消防
        mAdapterPopularize = new PopularizeAdapter(getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        mRVPopularize.setLayoutManager(layoutManager2);
        mRVPopularize.setAdapter(mAdapterPopularize);
    }

    @Override
    protected void initData() {
        findAppConfig();
        findPopularizeType();
//        findBannerData();
//        findShopAndEquipmentData();
        findMessageData();
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

                            findEquipmentDatas();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        refreshUI();
                    }
                });
    }

    //获取设备信息
    private void findEquipmentDatas() {
        EasyHttp.post(this)
                .api(new EquipmentInfoListApi())
                .request(new HttpCallback<HttpData<List<EquipmentInfo>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<EquipmentInfo>> data) {
                        if (data.getData() != null) {
                            mAdapterEquipment.initData(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }


    /**
     * 获取消息内容
     */
    private void findMessageData() {
        mNoticeMessageDatas.clear();

        /*mNoticeMessageDatas.add("消防安全不容马虎");
        mNoticeMessageDatas.add("消防安全宣传周");
        mNoticeMessageDatas.add("一小区发生电动车起火,起火原因巴拉巴拉巴拉保留保留保留保留");

        ArrayList<String> tempArrayList = new ArrayList<>();
        tempArrayList.addAll(mNoticeMessageDatas);

        mNoticeTv.setTextList(tempArrayList);
        mNoticeTv.setText(12f, 0, Color.BLACK, 16);
        mNoticeTv.setTextStillTime(3000);//设置停留时长间隔
        mNoticeTv.setAnimTime(300);//设置进入和退出的时间间隔
        mNoticeTv.setOnItemClickListener(position -> {
            NoticeDetailActivity.start(getContext(), "aaa");
        });*/

        /*if (true)
            return;*/

        EasyHttp.post(this)
                .api(new HomeMsgsApi()
                        .setPage(1)
                )
                .request(new HttpCallback<HttpData<List<HomeMsgBean>>>(this) {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSucceed(HttpData<List<HomeMsgBean>> data) {
                        if (data.getData() != null) {
                            List<HomeMsgBean> msgList = data.getData();
                            mNoticeMessageDatas = msgList.stream()
                                    .map(msg -> msg.getMsg_info())
                                    .collect(Collectors.toList());

                            ArrayList<String> tempArrayList = new ArrayList<>();
                            tempArrayList.addAll(mNoticeMessageDatas);

                            mNoticeTv.setTextList(tempArrayList);
                            mNoticeTv.setText(12f, 0, Color.BLACK, 16);
                            mNoticeTv.setTextStillTime(3000);//设置停留时长间隔
                            mNoticeTv.setAnimTime(300);//设置进入和退出的时间间隔
                            mNoticeTv.setOnItemClickListener(position -> {
                                NoticeDetailActivity.start(getContext(), msgList.get(position).getMsg_info());
                            });

                            if (mNoticeTv != null && mNoticeMessageDatas.size() > 0) {
                                mNoticeTv.startAutoScroll();
                            }

                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void findBannerData() {
        int[] imgs = {R.mipmap.bg_fj_1, R.mipmap.bg_fj_2, R.mipmap.bg_fj_3};
        List<HomeBannerData> tempBannerData = new ArrayList<>();
        HomeBannerData banner;
        for (int img : imgs) {
            banner = new HomeBannerData();
            banner.setResourcesId(img);
            tempBannerData.add(banner);
        }
        mBannerDatas.clear();
        mBannerDatas.addAll(tempBannerData);
        mBannerAdapter.notifyDataSetChanged();
    }

    /**
     * 获取店铺和设备数据
     */
    private void findShopAndEquipmentData() {
//        mShopInfo = MMKVHelper.getInstance().getShopInfo();
//        refreshUI();
    }

    //刷新UI
    private void refreshUI() {
        if (mShopInfo != null) {
            //添加店铺的按钮隐藏
            mTvBtnAddShop.setVisibility(View.GONE);
            mRVShopEquipment.setVisibility(View.VISIBLE);
            //店铺名称
            mTvShopName.setText(mShopInfo.getShop_name());
        } else {
            //添加店铺的按钮显示
            mTvBtnAddShop.setVisibility(View.VISIBLE);
            mRVShopEquipment.setVisibility(View.GONE);
            mTvShopName.setText("未添加店铺");
        }
    }

    //获取app常量
    private void findAppConfig() {
        mAppConfig = MMKVHelper.getInstance().findAppConfig();
        if (mAppConfig != null) {
            mBannerDatas.clear();
            if (mAppConfig.getApp_slide() != null) {
                mBannerDatas.addAll(mAppConfig.getApp_slide());
                mBannerAdapter.notifyDataSetChanged();
            }
            return;
        }

        EasyHttp.post(this)
                .api(new AppConfigApi())
                .request(new HttpCallback<HttpData<AppConfigBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<AppConfigBean> data) {
                        if (data.getData() != null) {

                            mBannerDatas.clear();
                            if (data.getData().getApp_slide() != null) {
                                mBannerDatas.addAll(data.getData().getApp_slide());
                                mBannerAdapter.notifyDataSetChanged();
                            }
                            MMKVHelper.getInstance().saveAppConfig(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    //获取宣传分类
    private void findPopularizeType() {
        List<PopularizeTypeBean> tempList = MMKVHelper.getInstance().findPopularizeTypes();

        if (tempList != null && tempList.size() > 0) {
            this.mPopularizeTypeID = tempList.get(0).getNews_type_id();
            findPopularizeDatas();
            return;
        }

        EasyHttp.post(this)
                .api(new PopularizeTypeApi())
                .request(new HttpCallback<HttpData<List<PopularizeTypeBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<PopularizeTypeBean>> data) {
                        if (data.getData() != null) {
                            if (data.getData().size() > 0) {
                                mPopularizeTypeID = data.getData().get(0).getNews_type_id();
                                findPopularizeDatas();
                            }
                            MMKVHelper.getInstance().savePopularizeTypes(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
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
                            mAdapterPopularize.initData(data.getData());
                            mGroup.setVisibility(data.getData().size() > 0 ? View.VISIBLE : View.GONE);
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


    @Override
    public void onResume() {
        super.onResume();
        if (mNoticeTv != null && mNoticeMessageDatas.size() > 0) {
            mNoticeTv.startAutoScroll();
        }
        findShopAndEquipmentData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNoticeTv != null) {
            mNoticeTv.stopAutoScroll();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNoticeTv.remove();
    }
}
