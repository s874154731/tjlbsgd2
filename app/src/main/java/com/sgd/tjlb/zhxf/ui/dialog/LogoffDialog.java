package com.sgd.tjlb.zhxf.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.hjq.base.BaseDialog;
import com.sgd.tjlb.zhxf.R;

/**
 * 注销弹窗
 */
public final class LogoffDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder> {

        private TextView tv_btn_cancel;
        private TextView tv_btn_logoff;

        private OnListener mListener;

        public Builder(Context context) {
            super(context);

            setContentView(R.layout.logoff_dialog);
            setAnimStyle(BaseDialog.ANIM_BOTTOM);
            setGravity(Gravity.CENTER);

            tv_btn_cancel = findViewById(R.id.tv_btn_cancel);
            tv_btn_logoff = findViewById(R.id.tv_btn_logoff);

            tv_btn_cancel.setOnClickListener(v -> {
                if (mListener != null) {
                    dismiss();
                    mListener.onCancel();
                }
            });
            tv_btn_logoff.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onLogoff();
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
         * 点击取消
         */
        void onCancel();

        /**
         * 注销
         */
        void onLogoff();
    }
}