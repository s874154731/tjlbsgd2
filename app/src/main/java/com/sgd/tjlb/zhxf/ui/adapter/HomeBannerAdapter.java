package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;

import androidx.annotation.NonNull;

/**
 * 首页banner图
 */
public final class HomeBannerAdapter extends AppAdapter<Integer> {

    public HomeBannerAdapter(Context context) {
        super(context);
        addItem(R.mipmap.bg_fj_3);
        addItem(R.mipmap.bg_fj_1);
        addItem(R.mipmap.bg_fj_2);
        addItem(R.mipmap.bg_fj_3);
        addItem(R.mipmap.bg_fj_1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    /*@Override
    public int getItemCount() {
//        if (getItemCount() > 1){
//            return Integer.MAX_VALUE;
//        }
//        return getItemCount();
        return Integer.MAX_VALUE;
    }*/

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final ImageView mImageView;

        private ViewHolder() {
            super(R.layout.item_home_banner);
            mImageView = (ImageView) getItemView();
        }

        @Override
        public void onBindView(int position) {
            mImageView.setImageResource(getItem(position));
        }
    }
}