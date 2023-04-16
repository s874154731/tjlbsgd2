package com.sgd.tjlb.zhxf.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.sgd.tjlb.zhxf.R;

/**
 * 隐私协议弹窗
 */
public final class PrivacyGreementDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private TextView tv_btn_greement;
        private TextView tv_btn_cancel;
        private TextView tv_btn_see;

        private OnListener mListener;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.privacy_greement_dialog);
            setAnimStyle(BaseDialog.ANIM_BOTTOM);
            setGravity(Gravity.CENTER);

            tv_btn_greement = findViewById(R.id.tv_btn_greement);
            tv_btn_cancel = findViewById(R.id.tv_btn_cancel);
            tv_btn_see = findViewById(R.id.tv_btn_see);

            tv_btn_greement.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onAgreement(getDialog());
                }
            });
            tv_btn_cancel.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onCancel();
                }
            });
            tv_btn_see.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onSee();
                }
            });
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
         * 点击确定
         */
        void onAgreement(BaseDialog dialog);

        /**
         * 点击取消
         */
        void onCancel();

        /**
         *
         */
        void onSee();
    }
}