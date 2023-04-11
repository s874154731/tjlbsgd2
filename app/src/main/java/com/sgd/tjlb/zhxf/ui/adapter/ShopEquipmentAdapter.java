package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.EquipmentInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺下的设备列表adapter
 */
public final class ShopEquipmentAdapter extends AppAdapter<EquipmentInfo> {

    private List<EquipmentInfo> mDatas = new ArrayList<>();

    public ShopEquipmentAdapter(Context context) {
        super(context);
    }

    private ItemCallBack itemCallBack;

    public void setItemCallBack(ItemCallBack itemCallBack) {
        this.itemCallBack = itemCallBack;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        private TextView tv_equipment_name;
        private TextView tv_btn_state;

        private ViewHolder() {
            super(R.layout.item_shop_equipment_in_item);

            tv_equipment_name = findViewById(R.id.tv_equipment_name);
            tv_btn_state = findViewById(R.id.tv_btn_state);
        }

        @Override
        public void onBindView(int position) {

            EquipmentInfo equipmentInfo = mDatas.get(position);
            if (equipmentInfo != null) {
                tv_equipment_name.setText(equipmentInfo.getName());

                tv_btn_state.setOnClickListener(v -> {
                    if (itemCallBack != null) {
                        itemCallBack.onItemClick(equipmentInfo);
                    }
                });
            }
        }
    }

    public interface ItemCallBack {
        void onItemClick(EquipmentInfo info);
    }
}