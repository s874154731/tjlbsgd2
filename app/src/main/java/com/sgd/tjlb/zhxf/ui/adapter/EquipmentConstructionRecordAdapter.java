package com.sgd.tjlb.zhxf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordBean;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;
import com.sgd.tjlb.zhxf.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 施工记录
 */
public final class EquipmentConstructionRecordAdapter extends AppAdapter<HomeMsgBean> {

    /**
     * 空布局 与正常布局
     */
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

    private List<ConstructionRecordBean> mDatas = new ArrayList<>();

    public EquipmentConstructionRecordAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY)
            return new EmptyViewHolder();
        return new NoticeViewHolder();
    }


    @Override
    public int getItemViewType(int position) {
        //如果集合为空，使用空布局
        if (mDatas == null || mDatas.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        //如果有数据则，加载item布局
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        //如果集合为空，则加载一个布局，空布局
        if (mDatas == null || mDatas.size() == 0) {
            return 1;
        }
        //如果不为空，正常加载
        return mDatas.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<ConstructionRecordBean> datas) {
        if (datas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    private final class NoticeViewHolder extends ViewHolder {

        private TextView tv_content;
        private TextView tv_time;
        private ImageView iv_record;

        private NoticeViewHolder() {
            super(R.layout.item_equipment_con_record);
            tv_content = findViewById(R.id.tv_content);
            tv_time = findViewById(R.id.tv_time);
            iv_record = findViewById(R.id.iv_record);
        }

        @Override
        public void onBindView(int position) {
            ConstructionRecordBean bean = mDatas.get(position);
            if (bean != null) {
                GlideUtils.loadImage(getContext(), bean.getStatus_img(), iv_record);
                tv_content.setText(bean.getStatus_info());
                tv_time.setText(bean.getCreate_time());
            }
        }
    }

    private final class EmptyViewHolder extends ViewHolder {

        public EmptyViewHolder() {
            super(R.layout.item_empty_page);
        }

        @Override
        public void onBindView(int position) {

        }
    }

}