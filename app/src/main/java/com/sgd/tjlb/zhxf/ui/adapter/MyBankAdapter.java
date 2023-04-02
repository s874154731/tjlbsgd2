package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.BankInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 申请银行卡 adapter
 */
public class MyBankAdapter extends AppAdapter<BankInfo> {

    /**
     * 空布局 与正常布局
     */
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

    private CallBack mCallBack;

    private List<BankInfo> mDatas = new ArrayList<>();

    public MyBankAdapter(@NonNull Context context) {
        super(context);
    }

    public void setmCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*if (viewType == VIEW_TYPE_EMPTY)
            return new EmptyViewHolder();*/
        return new MyBankViewHolder();
    }

    public void initData(List<BankInfo> dataList) {
        if (dataList != null) {
            mDatas = dataList;
        }
        setData(dataList);
    }

    /*@Override
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
    }*/


    private final class MyBankViewHolder extends ViewHolder {

        private ConstraintLayout clayout_item;
        private TextView tv_bank_name, tv_bank_no;

        public MyBankViewHolder() {
            super(R.layout.item_bank);

            clayout_item = findViewById(R.id.clayout_item);
            tv_bank_name = findViewById(R.id.tv_bank_name);
            tv_bank_no = findViewById(R.id.tv_bank_no);
        }

        @Override
        public void onBindView(int position) {
            BankInfo bankData = mDatas.get(position);
            if (bankData != null) {
                tv_bank_name.setText(bankData.getBankName());
                tv_bank_no.setText(bankData.getBank_no());

                clayout_item.setOnClickListener(v -> {
                    if (mCallBack != null){
                        mCallBack.onItemClick(bankData);
                    }
                });
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


    public interface CallBack{
        void onItemClick(BankInfo info);
    }
}
