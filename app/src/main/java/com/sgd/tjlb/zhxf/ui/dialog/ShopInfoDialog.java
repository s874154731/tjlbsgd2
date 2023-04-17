package com.sgd.tjlb.zhxf.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.entity.ShopInfo;
import com.sgd.tjlb.zhxf.ui.activity.AddEquipmentActivity;
import com.sgd.tjlb.zhxf.ui.adapter.ShopEquipmentAdapter;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 店铺信息弹窗
 */
public final class ShopInfoDialog {
    public static final int TYPE_AREA = 0;
    public static final int TYPE_MY_ORDER = 1;

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private TextView mTvStorename;
        private TextView mTvOrdertype;
        private TextView mTvStoreaddress;
        private TextView mBtnAdd;
        private RecyclerView mRecyclerView;
        private ShopEquipmentAdapter mAdapter;
        private ShopInfo info;

        private OnListener mListener;

        private int mType;
        private ShopInfo mShopInfo;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.shop_dialog);
            setAnimStyle(BaseDialog.ANIM_BOTTOM);
            setGravity(Gravity.CENTER);

            initView();
        }

        private void initView() {
            mTvStorename = findViewById(R.id.tv_item_my_order_storename);
            mTvOrdertype = findViewById(R.id.tv_item_my_order_ordertype);
            mTvStoreaddress = findViewById(R.id.tv_item_my_order_storeaddress);
            mBtnAdd = findViewById(R.id.tv_btn_add);
            mRecyclerView = findViewById(R.id.rv_shop_equipment);
        }

        private void refreshUI() {
            if (mShopInfo != null) {
                mTvStorename.setText(mShopInfo.getShop_name());
                mTvStoreaddress.setText(mShopInfo.getAddress());
                mTvOrdertype.setText(mShopInfo.getStatusTip());

                if (mType == TYPE_AREA) {
                    mRecyclerView.setVisibility(View.GONE);
                    mBtnAdd.setText(mShopInfo.getBtnReceivingOrderTip());
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mBtnAdd.setText(mShopInfo.getBtnShowTip());

                    boolean isShowBtn = mShopInfo.getStatus() == ShopInfo.Type_Installation_ING;
                    mBtnAdd.setVisibility(isShowBtn ? View.VISIBLE : View.GONE);
                }

                initRecyclerView(mShopInfo);

                mBtnAdd.setOnClickListener(v -> {
                    if (mListener != null) {
                        dismiss();
                        mListener.onShopBtn(mType, mShopInfo);
                    }
                });
            }
        }

        private void initRecyclerView(ShopInfo shopInfo) {
            mAdapter = new ShopEquipmentAdapter(getContext());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setItemCallBack(new ShopEquipmentAdapter.ItemCallBack() {
                @Override
                public void onItemClick(EquipmentInfo info) {
                    if (mListener != null) {
                        dismiss();
                        AddEquipmentActivity.start(getContext(), shopInfo.getUser_id(), info.getId());
                    }
                }

                @Override
                public void onItemAddRecord(EquipmentInfo info) {
                    if (mListener != null) {
                        dismiss();
                        mListener.onAddRecord(info);
                    }
                }
            });

            if (shopInfo.getDevicelist() != null) {
                mAdapter.initData(shopInfo.getDevicelist());
            }
        }

        public Builder setType(int type) {
            this.mType = type;
            return this;
        }

        public Builder setShopInfo(ShopInfo shopInfo) {
            this.mShopInfo = shopInfo;
            refreshUI();
            return this;
        }

        public Builder setListener(OnListener listener) {
            this.mListener = listener;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean boo) {
            super.setCanceledOnTouchOutside(boo);
            return this;
        }

        public Builder setCancelable(boolean boo) {
            super.setCancelable(boo);
            return this;
        }
    }

    public interface OnListener {

        /**
         * 店铺上的那个按钮  （接单'添加设备）
         *
         * @param type     类型
         * @param shopInfo 店铺信息
         */
        void onShopBtn(int type, ShopInfo shopInfo);

        /**
         * 修改设备
         */
        void onUpdateDevice(EquipmentInfo device);

        /**
         * 添加施工记录
         */
        void onAddRecord(EquipmentInfo device);
    }
}