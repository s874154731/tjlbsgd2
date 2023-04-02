package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.PurchasedTrafficfessBean;

import java.util.ArrayList;
import java.util.List;

public class MyInsuranceAdapter extends AppAdapter<PurchasedTrafficfessBean> {

    private List<PurchasedTrafficfessBean> mDatas = new ArrayList<>();

    public MyInsuranceAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyInsuranceViewHolder();
    }

    /**
     * 初始化数据
     */
    public void initData(List<PurchasedTrafficfessBean> dataList) {
        if (dataList != null) {
            mDatas = dataList;
        }
        setData(dataList);
    }

    /**
     * 添加数据
     */
    public void addDatas(List<PurchasedTrafficfessBean> dataList) {
        if (dataList != null) {
            mDatas.addAll(dataList);
        }
        addData(dataList);
    }

    private final class MyInsuranceViewHolder extends ViewHolder {

        /*private AppCompatImageView mIvImg;
        private TextView mTvName;
        private TextView mTvKeyword;
        private TextView mTvState;*/

        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvIMoney;

        public MyInsuranceViewHolder() {
//            super(R.layout.item_my_insurance);
            super(R.layout.item_contract_purchased);

            /*mIvImg = (AppCompatImageView) findViewById(R.id.iv_item_my_insurance_img);
            mTvName = (TextView) findViewById(R.id.tv_item_my_insurance_name);
            mTvKeyword = (TextView) findViewById(R.id.tv_item_my_insurance_keyword);
            mTvState = (TextView) findViewById(R.id.tv_item_my_insurance_state);*/

            mTvTime = (TextView) findViewById(R.id.tv_item_purchased_time);
            mTvTitle = (TextView) findViewById(R.id.tv_item_purchased_title);
            mTvIMoney = (TextView) findViewById(R.id.tv_item_buyflowcharge_money);

        }

        @Override
        public void onBindView(int position) {

            PurchasedTrafficfessBean bean = mDatas.get(position);

//            GlideUtils.loadRoundCircleImage(getContext(), bean.getImageUrl(), mIvImg, 4);
            mTvTime.setText(bean.getStart_time());
            mTvTitle.setText(bean.getProduct_title());
            mTvIMoney.setText("￥" + bean.getProduct_total().toPlainString());

        }
    }

}
