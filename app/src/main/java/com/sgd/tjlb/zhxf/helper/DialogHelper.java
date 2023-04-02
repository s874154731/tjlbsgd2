package com.sgd.tjlb.zhxf.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;

import com.hjq.base.BaseDialog;
import com.hjq.toast.ToastUtils;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.ui.dialog.AddressDialog;
import com.sgd.tjlb.zhxf.ui.dialog.MenuDialog;
import com.sgd.tjlb.zhxf.ui.dialog.MessageDialog;

import java.util.List;

public class DialogHelper {

    private static class SingletonHolder {
        private static final DialogHelper instance = new DialogHelper();
    }

    public static DialogHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 拨打电话弹窗
     *
     * @param context   上下
     * @param telephone 客服电话
     */
    public void showCallDialog(Context context, String telephone) {
        // 消息对话框
        new MessageDialog.Builder(context)
                // 标题可以不用填写
                .setTitle("提示")
                // 内容必须要填写
                .setMessage("是否拨打客服电话")
                // 确定按钮文本
                .setConfirm(context.getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(context.getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        if (TextUtils.isEmpty(telephone)){
                            ToastUtils.show("您拨打的电话是空号");
                            return;
                        }
                        //手动点击拨打，无需申请权限
                        Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone));
                        try {
                            context.startActivity(intent2);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.show("拨打电话失败");
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {

                    }
                })
                .show();
    }

    /**
     * 省市区弹窗
     *
     * @param context   上下
     * @param mProvince 默认省份
     * @param mCity     默认城市
     */
    public void showAddressDialog(Context context, String mProvince, String mCity, OnAddressListener listener) {
        new AddressDialog.Builder(context)
                //.setTitle("选择地区")
                // 设置默认省份
                .setProvince(mProvince)
                // 设置默认城市（必须要先设置默认省份）
                .setCity(mCity)
                // 不选择县级区域
//                .setIgnoreArea()
                .setListener((dialog, province, city, area) -> {
                    listener.onSelected(province, city, area);
                })
                .show();
    }

    /**
     * 菜单选择框（无确定，取消按钮）
     *
     * @param context  上下
     * @param data     数据
     * @param listener 选中监听
     */
    public void showMenuDialog(Context context, List<String> data, OnMenuListener listener) {
        // 居中选择框
        new MenuDialog.Builder(context)
                .setGravity(Gravity.CENTER)
                // 设置 null 表示不显示取消按钮
                //.setCancel(null)
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setList(data)
                .setListener(new MenuDialog.OnListener<String>() {

                    @Override
                    public void onSelected(BaseDialog dialog, int position, String string) {
                        listener.onSelected(position, string);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {

                    }
                })
                .show();

    }

    public interface OnMenuListener {

        void onSelected(int position, String string);

    }

    public interface OnAddressListener {

        /**
         * 选择完成后回调
         *
         * @param province 省
         * @param city     市
         * @param area     区
         */
        void onSelected(String province, String city, String area);

    }

}
