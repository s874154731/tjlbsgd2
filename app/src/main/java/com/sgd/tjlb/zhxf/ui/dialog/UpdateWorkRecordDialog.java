package com.sgd.tjlb.zhxf.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseDialog;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.entity.WorkRecordDetailsData;

import java.util.ArrayList;
import java.util.List;

public class UpdateWorkRecordDialog {

    public static final class Builder
            extends BaseDialog.Builder<UpdateWorkRecordDialog.Builder> {

        private RecyclerView mRvImg;
        private RegexEditText mEtDescription;
        private LinearLayout mLlBtnExamine;
        private TextView mTvExamine;
        private AppCompatButton mBtnModification;

        private OnListener mListener;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_update_work_record);
            setAnimStyle(BaseDialog.ANIM_TOAST);
//            setBackgroundDimEnabled(false);
//            setCancelable(false);

            mRvImg = (RecyclerView) findViewById(R.id.rv_dialog_work_record_img);
            mEtDescription = (RegexEditText) findViewById(R.id.et_dialog_work_record_description);
            mLlBtnExamine = (LinearLayout) findViewById(R.id.ll_btn_dialog_work_record_examine);
            mTvExamine = (TextView) findViewById(R.id.tv_dialog_work_record_examine);
            mBtnModification = (AppCompatButton) findViewById(R.id.btn_dialog_work_record_modification);

            mLlBtnExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> data = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        data.add("我是数据" + (i + 1));
                    }
                    // 底部选择框
                    new MenuDialog.Builder(getContext())
                            // 设置 null 表示不显示取消按钮
                            //.setCancel(getString(R.string.common_cancel))
                            // 设置点击按钮后不关闭对话框
                            //.setAutoDismiss(false)
                            .setList(data)
                            .setListener(new MenuDialog.OnListener<String>() {

                                @Override
                                public void onSelected(BaseDialog dialog, int position, String string) {
                                    mTvExamine.setText(string);
                                }

                                @Override
                                public void onCancel(BaseDialog dialog) {
//                                    toast("取消了");
                                }
                            })
                            .show();
                }
            });

            mBtnModification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener == null) {
                        return;
                    }
                    String describe = mEtDescription.getText().toString().trim();
                    String examine = mTvExamine.getText().toString().trim();
                    if (TextUtils.isEmpty(describe)) {
                        ToastUtils.show("描述不能为空");
                        return;
                    }
                    WorkRecordDetailsData data = new WorkRecordDetailsData();
                    data.setDescribe(describe);
                    data.setDescribe(examine);
                    mListener.onSubmit(getDialog(), data);
                }
            });

        }

        public UpdateWorkRecordDialog.Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public UpdateWorkRecordDialog.Builder setData(WorkRecordDetailsData data) {
            mEtDescription.setText(data.getDescribe());
            mTvExamine.setText("安装异常");
            return this;
        }
    }

    public interface OnListener {

        /**
         * 点击提交
         */
        void onSubmit(BaseDialog dialog, WorkRecordDetailsData data);
    }

}
