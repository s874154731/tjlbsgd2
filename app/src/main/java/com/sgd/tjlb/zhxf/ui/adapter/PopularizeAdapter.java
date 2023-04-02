package com.sgd.tjlb.zhxf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.PopularizeData;
import com.sgd.tjlb.zhxf.ui.activity.PopularizeDetailActivity;
import com.sgd.tjlb.zhxf.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

public class PopularizeAdapter extends AppAdapter<PopularizeData> {

    private final static int TYPE_EMPTY = -1;
    private final static int TYPE_NORMAL = 0;

    private boolean isShowEmptyPage;//是否需要显示空页面显示

    private List<PopularizeData> mDatas = new ArrayList<>();

    public PopularizeAdapter(@NonNull Context context) {
        super(context);
    }

    public PopularizeAdapter(@NonNull Context context, boolean isShowEmptyPage) {
        super(context);
        this.isShowEmptyPage = isShowEmptyPage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY)
            return new PoizeEmptyViewHolder();
        return new PoizeViewHolder();
    }

    @Override
    public int getItemViewType(int position) {
        if (!isShowEmptyPage)
            return TYPE_NORMAL;

        //如果集合为空，使用空布局
        if (mDatas == null || mDatas.size() == 0) {
            return TYPE_EMPTY;
        }
        //如果有数据则，加载item布局
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (!isShowEmptyPage){
            return mDatas.size();
        }

        //如果集合为空，则加载一个布局，空布局
        if (mDatas == null || mDatas.size() == 0) {
            return 1;
        }
        //如果不为空，正常加载
        return mDatas.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<PopularizeData> data) {
        if (data != null) {
            mDatas = data;
        }
        setData(data);
    }

    private final class PoizeEmptyViewHolder extends AppAdapter<?>.ViewHolder{

        public PoizeEmptyViewHolder() {
            super(R.layout.item_empty_page);
        }

        @Override
        public void onBindView(int position) {

        }
    }

    private final class PoizeViewHolder extends AppAdapter<?>.ViewHolder {

        private AppCompatImageView mIvTop;
        private AppCompatImageView mBtnPlay;
        private TextView mTitle;
        private TextView mTime;

        private PoizeViewHolder() {
            super(R.layout.item_popularize);
            mIvTop = findViewById(R.id.iv_popularize);
            mBtnPlay = findViewById(R.id.iv_play);
            mTitle = findViewById(R.id.tv_title);
            mTime = findViewById(R.id.tv_time);
        }

        @Override
        public void onBindView(int position) {

            PopularizeData bean = mDatas.get(position);

            GlideUtils.loadImage(getContext(), bean.getImgUrl(), mIvTop);
            mTime.setText(TextUtils.isEmpty(bean.getTime()) ? getContext().getResources().getString(R.string.tip_time) : bean.getTime());
            mTitle.setText(TextUtils.isEmpty(bean.getTitle()) ? "" : bean.getTitle());
            mBtnPlay.setVisibility(bean.isVideo() ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(v -> {
                PopularizeDetailActivity.start(getContext(), bean.getHtmlUrl());
            });
        }
    }
}
