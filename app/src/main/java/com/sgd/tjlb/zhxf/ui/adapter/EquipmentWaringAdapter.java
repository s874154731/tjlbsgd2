package com.sgd.tjlb.zhxf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.EquipmentWaringBean;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * 警报adapter
 */
public final class EquipmentWaringAdapter extends AppAdapter<HomeMsgBean> {

    /**
     * 空布局 与正常布局
     */
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

    private List<EquipmentWaringBean> mDatas = new ArrayList<>();

    public EquipmentWaringAdapter(Context context) {
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
    public void initData(List<EquipmentWaringBean> datas) {
        if (datas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    private final class NoticeViewHolder extends ViewHolder {

        private TextView tv_content;
        private TextView tv_time;

        private NoticeViewHolder() {
            super(R.layout.item_equipment_waring);
            tv_content = findViewById(R.id.tv_content);
            tv_time = findViewById(R.id.tv_time);
        }

        @Override
        public void onBindView(int position) {
            EquipmentWaringBean bean = mDatas.get(position);
            if (bean != null) {
                tv_content.setText(bean.getAlarm_msg());
                tv_time.setText(bean.getCreate_time());
            }
        }
    }

    private final class EmptyViewHolder extends AppAdapter<?>.ViewHolder{

        public EmptyViewHolder() {
            super(R.layout.item_empty_page);
        }

        @Override
        public void onBindView(int position) {

        }
    }

}