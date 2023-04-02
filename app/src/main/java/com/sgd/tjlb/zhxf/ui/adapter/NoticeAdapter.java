package com.sgd.tjlb.zhxf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.HomeMsgBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息adapter
 */
public final class NoticeAdapter extends AppAdapter<HomeMsgBean> {

    private List<HomeMsgBean> mDatas = new ArrayList<>();

    private CallBack mCallBack;

    public NoticeAdapter(Context context) {
        super(context);
    }

    public void setmCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<HomeMsgBean> datas) {
        if (datas != null) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    private final class NoticeViewHolder extends AppAdapter<?>.ViewHolder {

        private TextView tv_content;
        private TextView tv_time;

        private NoticeViewHolder() {
            super(R.layout.item_notice);
            tv_content = findViewById(R.id.tv_content);
            tv_time = findViewById(R.id.tv_time);
        }

        @Override
        public void onBindView(int position) {
            HomeMsgBean bean = mDatas.get(position);
            if (bean != null) {
                tv_content.setText(bean.getMsg_info());
                tv_time.setText(bean.getCreate_time());

                itemView.setOnClickListener(v -> {

                    if (mCallBack != null) {
                        mCallBack.onItemClick(bean);
                    }

                });

            }
        }
    }

    public interface CallBack {
        void onItemClick(HomeMsgBean bean);
    }
}