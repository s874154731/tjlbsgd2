package com.sgd.tjlb.zhxf.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;
import com.sgd.tjlb.zhxf.ui.activity.ApplyForMaintenanceActivity;
import com.sgd.tjlb.zhxf.ui.activity.EquipmentConstructionRecordActivity;
import com.sgd.tjlb.zhxf.ui.activity.EquipmentWaringActivity;
import com.sgd.tjlb.zhxf.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

public class EquipmentAdapter extends AppAdapter<EquipmentInfo> {

    /**
     * 空布局 与正常布局
     */
    public static final int VIEW_TYPE_ITEM = 1;
    public static final int VIEW_TYPE_EMPTY = 0;

    /**
     * 是否默认可显示空布局
     */
    private boolean isShowEmptyPage = false;

    private List<EquipmentInfo> mDatas = new ArrayList<>();
    private Context mContext;

    public EquipmentAdapter(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public EquipmentAdapter(@NonNull Context context, boolean isShowEmptyPage) {
        super(context);
        this.isShowEmptyPage = isShowEmptyPage;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY)
            return new EquipmentEmptyViewHolder();
        return new EquipmentViewHolder();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<EquipmentInfo> data) {
        if (data != null) {
            mDatas = data;
        }
        setData(data);
    }

    @Override
    public int getItemCount() {
        if (isShowEmptyPage) {
            //如果集合为空，则加载一个布局，空布局
            if (mDatas == null || mDatas.size() == 0) {
                return 1;
            }
        }
        //如果不为空，正常加载
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowEmptyPage) {
            //如果集合为空，使用空布局
            if (mDatas.size() == 0) {
                return VIEW_TYPE_EMPTY;
            }
        }
        //如果有数据则，加载item布局
        return VIEW_TYPE_ITEM;
    }


    private final class EquipmentViewHolder extends ViewHolder {

        private ConstraintLayout mLayoutWaring;
        private AppCompatImageView mIVEquipment, mIVWaringState;
        private TextView mTvOnlineState, mTvWaringState, mTvUnReadNum;
        private TextView mTvName, mTvBtnRequestRepair,mTVBtnConstructionRecord;
        private Group mGroupHide;

        private EquipmentViewHolder() {
            super(R.layout.item_shop_equipment);

            mIVEquipment = findViewById(R.id.iv_equipment);
            mTvOnlineState = findViewById(R.id.tv_online_state);
            mTvWaringState = findViewById(R.id.tv_waring_state);
            mIVWaringState = findViewById(R.id.iv_waring_state);
            mTvUnReadNum = findViewById(R.id.tv_unread_num);
            mTvBtnRequestRepair = findViewById(R.id.tv_btn_request_repair);
            mTvName = findViewById(R.id.tv_name);
            mGroupHide = findViewById(R.id.group_hide);
            mLayoutWaring = findViewById(R.id.layout_waring);
            mTVBtnConstructionRecord = findViewById(R.id.tv_btn_construction_record);//施工记录
        }

        @Override
        public void onBindView(int position) {

            //是否展示申请维修按钮和line
            mGroupHide.setVisibility(isShowEmptyPage ? View.VISIBLE : View.GONE);

            EquipmentInfo bean = mDatas.get(position);
            if (bean != null) {
                //设备SN
                mTvName.setText(bean.getDevice_sn());
                //设备图片
                GlideUtils.loadImage(getContext(), bean.getImgUrl(), mIVEquipment, R.mipmap.img_icon_eup);
                //在线状态
                mTvOnlineState.setSelected(bean.isOnline());
                mTvOnlineState.setText(bean.isOnline() ?
                        getContext().getResources().getString(R.string.tip_online) :
                        getContext().getResources().getString(R.string.tip_offline));

                //警报
                mIVWaringState.setSelected(bean.getStatus() == 6);
                mTvWaringState.setSelected(bean.getStatus() == 6);

                //警报数量
                mTvUnReadNum.setText(String.valueOf(bean.getAlarm_count()));
                mTvUnReadNum.setVisibility(bean.getAlarm_count() > 0 ? View.VISIBLE : View.GONE);

                //申请维修
                mTvBtnRequestRepair.setOnClickListener(v -> {
                    ApplyForMaintenanceActivity.start(mContext, bean.getDevice_sn());
                });

                //进入警报列表
                mLayoutWaring.setOnClickListener(v -> {
                    EquipmentWaringActivity.start(mContext, bean.getDevice_sn());
                });
                //进入施工记录列表
                mTVBtnConstructionRecord.setOnClickListener(v -> {
                    EquipmentConstructionRecordActivity.start(mContext, bean.getDevice_sn());
                });
            }
        }
    }

    private final class EquipmentEmptyViewHolder extends ViewHolder {

//        private TextView mTvAdd;

        private EquipmentEmptyViewHolder() {
            super(R.layout.item_shop_equipment_empty);
//            mTvAdd = findViewById(R.id.tv_btn_add);
        }

        @Override
        public void onBindView(int position) {
            /*mTvAdd.setOnClickListener(v -> {
                MineEquipmentActivity.start(getContext());
            });*/
        }
    }
}
