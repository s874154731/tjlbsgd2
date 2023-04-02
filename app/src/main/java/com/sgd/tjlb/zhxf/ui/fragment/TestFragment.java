package com.sgd.tjlb.zhxf.ui.fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppFragment;
import com.sgd.tjlb.zhxf.entity.HomeBannerData;
import com.sgd.tjlb.zhxf.entity.PopularizeData;
import com.sgd.tjlb.zhxf.ui.adapter.PopularizeAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends AppFragment {

   private Banner mBanner;
   private CircleIndicator mCircleIndicator;
   private RecyclerView mRecyclerView;
   private PopularizeAdapter mAdapter;

   public static TestFragment newInstance() {
      return new TestFragment();
   }


   @Override
   protected int getLayoutId() {
      return R.layout.fragment_test;
   }

   @Override
   protected void initView() {
      mBanner = (Banner) findViewById(R.id.banner_home);
      mCircleIndicator = (CircleIndicator) findViewById(R.id.cv_home_indicator);
      mRecyclerView = (RecyclerView) findViewById(R.id.rv_equipment);

      initRecyclerView();
   }

   private void initRecyclerView() {
      mAdapter = new PopularizeAdapter(getContext());
      LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
      mRecyclerView.setLayoutManager(layoutManager);
      mRecyclerView.setAdapter(mAdapter);
   }

   @Override
   protected void initData() {
      initBanner();
      findPopularizeData();
   }

   private void initBanner() {
      int[] imgs = {R.mipmap.bg_fj_1, R.mipmap.bg_fj_2, R.mipmap.bg_fj_3};
      List<HomeBannerData> banners = new ArrayList<>();
      HomeBannerData banner;
      for (int img : imgs) {
         banner = new HomeBannerData();
         banner.setResourcesId(img);
         banners.add(banner);
      }
      mBanner.setAdapter(
              new BannerImageAdapter<HomeBannerData>(banners) {
                 @Override
                 public void onBindView(BannerImageHolder holder, HomeBannerData data, int position, int size) {
                    //图片加载自己实现
                    Glide.with(holder.itemView)
                            .load(data.getResourcesId())
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                            .into(holder.imageView);
                 }
              })
              .addBannerLifecycleObserver(this)//添加生命周期观察者
              .setIndicator(mCircleIndicator, false);
   }

   /**
    * 获取消防安全数据
    */
   private void findPopularizeData() {
      List<PopularizeData> tempDatas = new ArrayList<>();
      PopularizeData data;
      for (int i = 0; i < 10; i++) {
         data = new PopularizeData();
         data.setTitle("石家庄消防安全宣传周"+i);
         data.setContent("石家庄消防安全宣传周"+i);
         data.setImgUrl("");
         data.setVideoUrl("");
         tempDatas.add(data);
      }
      mAdapter.initData(tempDatas);
   }
}
