package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.BankInfo;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordData;
import com.sgd.tjlb.zhxf.entity.ContractData;
import com.sgd.tjlb.zhxf.entity.OrderData;

import java.util.ArrayList;
import java.util.List;

public class ConstructionRecordAdapter extends AppAdapter<ConstructionRecordData> {

    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

    private List<ConstructionRecordData> mDatas = new ArrayList<>();

    private CallBack mCallBack;

    public void setmCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public ConstructionRecordAdapter(@NonNull Context context) {
        super(context);
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY)
            return new EmptyViewHolder();
        return new ConstructionRecordViewHolder();
    }

    /**
     * 初始化数据
     */
    public void initData(List<ConstructionRecordData> data) {
        if (data != null) {
            mDatas = data;
        }
        setData(data);
    }

    private final class EmptyViewHolder extends AppAdapter<?>.ViewHolder {

        public EmptyViewHolder() {
            super(R.layout.item_empty_page);
        }

        @Override
        public void onBindView(int position) {

        }
    }

    private final class ConstructionRecordViewHolder extends ViewHolder {

        private TextView mTvIWorkrecord;
        private TextView mTvITime;
        private TextView mTvIName;
        private TextView mTvIAddress;

        public ConstructionRecordViewHolder() {
            super(R.layout.item_construction_record);

            mTvIWorkrecord = (TextView) findViewById(R.id.tv_item_construction_record_workrecord);
            mTvITime = (TextView) findViewById(R.id.tv_item_construction_record_time);
            mTvIName = (TextView) findViewById(R.id.tv_item_construction_record_name);
            mTvIAddress = (TextView) findViewById(R.id.tv_item_construction_record_address);

        }

        @Override
        public void onBindView(int position) {
            ConstructionRecordData data = mDatas.get(position);
            mTvITime.setText(data.getTime());
            mTvIName.setText(data.getName());
            mTvIAddress.setText(data.getAddress());
            mTvIWorkrecord.setOnClickListener(view ->
                    mCallBack.onItemClick(data)
            );
        }

    }

    public interface CallBack{
        void onItemClick(ConstructionRecordData data);
    }

}
