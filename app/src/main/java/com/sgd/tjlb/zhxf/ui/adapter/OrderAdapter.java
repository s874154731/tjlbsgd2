package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.OrderData;
import com.sgd.tjlb.zhxf.entity.ShopInfo;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends AppAdapter<ShopInfo> {

    /**
     * 空布局 与正常布局
     */
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;


    private List<ShopInfo> mDatas = new ArrayList<>();

    public OrderAdapter(@NonNull Context context) {
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
    public BaseAdapter<?>.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            return new EmptyViewHolder();
        }
        return new OrderViewHolder();
    }

    /**
     * 初始化数据
     */
    public void initData(List<ShopInfo> data) {
        if (data != null) {
            mDatas = data;
        }
        setData(data);
    }

    private final class EmptyViewHolder extends BaseAdapter.ViewHolder {

        public EmptyViewHolder() {
            super(R.layout.item_empty_page);
        }

        @Override
        public void onBindView(int position) {

        }
    }

    private final class OrderViewHolder extends BaseAdapter.ViewHolder {

        private TextView mTvStorename;
        private TextView mTvOrdertype;
        private TextView mTvStoreaddress;
        private TextView mTvOrderAcceptorder;

        public OrderViewHolder() {
            super(R.layout.item_home_order);

            mTvStorename = (TextView) findViewById(R.id.tv_item_home_order_storename);
            mTvOrdertype = (TextView) findViewById(R.id.tv_item_home_order_ordertype);
            mTvStoreaddress = (TextView) findViewById(R.id.tv_item_home_order_storeaddress);
            mTvOrderAcceptorder = (TextView) findViewById(R.id.tv_home_order_acceptorder);

        }

        @Override
        public void onBindView(int position) {

            ShopInfo shopInfo = mDatas.get(position);
            mTvStorename.setText(shopInfo.getShop_name());
            mTvStoreaddress.setText(shopInfo.getAddress());
            mTvOrdertype.setText(shopInfo.getStatus()+"");
        }

    }

}
