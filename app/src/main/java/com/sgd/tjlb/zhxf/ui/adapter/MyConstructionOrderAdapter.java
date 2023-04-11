package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.ui.activity.AddEquipmentActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//我的施工单
public class MyConstructionOrderAdapter extends AppAdapter<ShopInfo> {

    /**
     * 空布局 与正常布局
     */
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

    private List<ShopInfo> mDatas = new ArrayList<>();

    private ItemClickCallBack mCallBack;

    public void setmCallBack(ItemClickCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public MyConstructionOrderAdapter(@NonNull Context context) {
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
        if (viewType == VIEW_TYPE_EMPTY) {
            return new EmptyViewHolder();
        }
        return new ConstructionViewHolder();
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

    /**
     * 设置·
     * @param shopInfo
     * @param equipmentInfoList
     */
    public void setEquipmentDatas(ShopInfo shopInfo ,List<EquipmentInfo> equipmentInfoList){

    }


    private final class EmptyViewHolder extends ViewHolder {

        public EmptyViewHolder() {
            super(R.layout.item_empty_page);
        }

        @Override
        public void onBindView(int position) {

        }
    }

    private final class ConstructionViewHolder extends ViewHolder {

        private TextView mTvStorename;
        private TextView mTvOrdertype;
        private TextView mTvStoreaddress;
        private TextView mBtnAdd;
        private RecyclerView mRecyclerView;

        private ShopEquipmentAdapter mAdapter;

        public ConstructionViewHolder() {
            super(R.layout.item_my_construction_order);

            mTvStorename = (TextView) findViewById(R.id.tv_item_home_order_storename);
            mTvOrdertype = (TextView) findViewById(R.id.tv_item_home_order_ordertype);
            mTvStoreaddress = (TextView) findViewById(R.id.tv_item_home_order_storeaddress);
            mBtnAdd = (TextView) findViewById(R.id.tv_btn_add);
            mRecyclerView = findViewById(R.id.rv_shop_equipment);
        }

        @Override
        public void onBindView(int position) {

            ShopInfo shopInfo = mDatas.get(position);
            mTvStorename.setText(shopInfo.getShop_name());
            mTvStoreaddress.setText(shopInfo.getAddress());
            mTvOrdertype.setText(shopInfo.getStatusTip());

            mBtnAdd.setText(shopInfo.getBtnShowTip());

            initRecyclerView(shopInfo);

            mBtnAdd.setOnClickListener(v->{
                if (mCallBack != null){
                    mCallBack.onItemClick(shopInfo);
                }
            });
        }

        private void initRecyclerView(ShopInfo shopInfo) {
            mAdapter = new ShopEquipmentAdapter(getContext());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setItemCallBack(info -> {
                AddEquipmentActivity.start(getContext(),shopInfo.getUser_id(),info.getId());
            });

            if (shopInfo.getDevicelist() != null){
                mAdapter.initData(shopInfo.getDevicelist());
            }
        }
    }

    public interface ItemClickCallBack{
        void onItemClick(ShopInfo shopInfo);
    }
}
