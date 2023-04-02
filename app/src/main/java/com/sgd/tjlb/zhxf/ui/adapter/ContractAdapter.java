package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.ContractData;
import com.sgd.tjlb.zhxf.utils.GlideUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 合同管理 adapter
 */
public class ContractAdapter extends AppAdapter<ContractData> {

    private int Buy_Flow_Charge = 1;
    private int Purchased = 2;

    private List<ContractData> mDatas = new ArrayList<>();

    public ContractAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return Buy_Flow_Charge;
        }
        return Purchased;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Buy_Flow_Charge) {
            return new ContractBuyFlowViewHolder();
        }
        return new ContractPurchasedViewHolder();
    }

    public void initData(List<ContractData> dataList) {
        if (dataList != null) {
            mDatas = dataList;
        }
        setData(dataList);
    }

    /**
     * 已购买
     */
    private final class ContractPurchasedViewHolder extends ViewHolder {

        private TextView mTvTime;
        private TextView mTvTitle;
        private TextView mTvMoney;

        public ContractPurchasedViewHolder() {
            super(R.layout.item_contract_purchased);

            mTvTime = (TextView) findViewById(R.id.tv_item_purchased_time);
            mTvTitle = (TextView) findViewById(R.id.tv_item_purchased_title);
            mTvMoney = (TextView) findViewById(R.id.tv_item_buyflowcharge_money);

        }

        @Override
        public void onBindView(int position) {

            ContractData bean = mDatas.get(position);

//            GlideUtils.loadRoundCircleImage(getContext(), bean.getImageUrl(), mIvImg, 4);
            mTvTitle.setText(bean.getName());
            mTvMoney.setText(bean.getContractMoney().toPlainString());
            mTvMoney.setText("￥" + bean.getContractMoney().toPlainString());
            mTvTime.setText(bean.getTime());

        }

    }

    /**
     * 购买流量费
     */
    private final class ContractBuyFlowViewHolder extends ViewHolder {

        private AppCompatImageView mIvImg;
        private TextView mTvName;
        private TextView mTvMoney;
        private ImageView mIvBtnAdd;
        private RegexEditText mEtBuynum;
        private ImageView mIvBtnMinus;
        private TextView mTvTotalmoney;
        private TextView mTvPay;

        public ContractBuyFlowViewHolder() {
            super(R.layout.item_contract_buyflowcharge);

            mIvImg = (AppCompatImageView) findViewById(R.id.iv_item_buyflowcharge_img);
            mTvName = (TextView) findViewById(R.id.tv_item_buyflowcharge_name);
            mTvMoney = (TextView) findViewById(R.id.tv_item_buyflowcharge_money);
            mIvBtnAdd = (ImageView) findViewById(R.id.iv_btn_item_buyflowcharge_add);
            mEtBuynum = (RegexEditText) findViewById(R.id.et_item_buyflowcharge_buynum);
            mIvBtnMinus = (ImageView) findViewById(R.id.iv_btn_item_buyflowcharge_minus);
            mTvTotalmoney = (TextView) findViewById(R.id.tv_item_buyflowcharge_totalmoney);
            mTvPay = (TextView) findViewById(R.id.tv_item_buyflowcharge_pay);

        }

        @Override
        public void onBindView(int position) {

            ContractData bean = mDatas.get(position);

            GlideUtils.loadRoundCircleImage(getContext(), bean.getImageUrl(), mIvImg, 4);
            mTvName.setText(bean.getName());
            mTvMoney.setText("¥" + bean.getContractMoney().toPlainString());
            mTvTotalmoney.setText("¥" + bean.getTotalMoney().toPlainString());

            mIvBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calculationBuyNum(1, bean.getContractMoney());
                }
            });
            mIvBtnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calculationBuyNum(2, bean.getContractMoney());
                }
            });

            mTvPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.show("支付");
                }
            });
        }

        /**
         * 计算购买数量和总价
         *
         * @param type  1 加法 2 减法
         * @param price 单价
         */
        private void calculationBuyNum(int type, BigDecimal price) {
            int nowBuyNum = Integer.parseInt(TextUtils.isEmpty(mEtBuynum.getText().toString().trim()) ? "1" : mEtBuynum.getText().toString().trim());

            if (nowBuyNum != 0) {
                if (type == 1) {
                    nowBuyNum += 1;
                } else {
                    nowBuyNum -= 1;
                }
            }

            mEtBuynum.setText(String.valueOf(nowBuyNum));
            BigDecimal bigDecimal = new BigDecimal(nowBuyNum);
            bigDecimal.multiply(price);
            mTvTotalmoney.setText(bigDecimal.toPlainString());

        }

    }

}
