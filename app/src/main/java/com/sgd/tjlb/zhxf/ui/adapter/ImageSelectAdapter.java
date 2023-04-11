package com.sgd.tjlb.zhxf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.http.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/07/24
 * desc   : 图片选择适配器
 */
public final class ImageSelectAdapter extends AppAdapter<String> {

    private List<String> mSelectImages = new ArrayList<>();

    public static final int No_Check = 1;//没有选择

    private int type;

    public ImageSelectAdapter(Context context, List<String> images) {
        super(context);
        mSelectImages = images;
    }

    public ImageSelectAdapter(Context context,int type) {
        super(context);
        this.type = type;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<String> images) {
        if (mSelectImages != null) {
            mSelectImages = images;
        }
        setData(mSelectImages);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private final ImageView mImageView;
        private final CheckBox mCheckBox;

        private ViewHolder() {
            super(R.layout.image_select_item);
            mImageView = findViewById(R.id.iv_image_select_image);
            mCheckBox = findViewById(R.id.iv_image_select_check);
        }

        @Override
        public void onBindView(int position) {
            String imagePath = getItem(position);
            GlideApp.with(getContext())
                    .asBitmap()
                    .load(imagePath)
                    .into(mImageView);
            if (type == No_Check) {
                mCheckBox.setVisibility(View.GONE);
            }
            mCheckBox.setChecked(mSelectImages.contains(imagePath));
        }
    }

    @Override
    protected RecyclerView.LayoutManager generateDefaultLayoutManager(Context context) {
        return new GridLayoutManager(context, 3);
    }
}