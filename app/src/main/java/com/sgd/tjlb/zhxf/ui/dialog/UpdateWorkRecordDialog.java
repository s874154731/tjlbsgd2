package com.sgd.tjlb.zhxf.ui.dialog;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseActivity;
import com.hjq.base.BaseDialog;
import com.hjq.http.model.FileContentResolver;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordBean;
import com.sgd.tjlb.zhxf.entity.QuestionImgData;
import com.sgd.tjlb.zhxf.ui.activity.ApplyForMaintenanceActivity;
import com.sgd.tjlb.zhxf.ui.activity.ImageCropActivity;
import com.sgd.tjlb.zhxf.ui.activity.ImagePreviewActivity;
import com.sgd.tjlb.zhxf.ui.activity.ImageSelectActivity;
import com.sgd.tjlb.zhxf.ui.adapter.QuestionImgAdapter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
        private BaseActivity activity;

        private ConstructionRecordBean data;
        private int status;

        private Uri mQuestionUrl;
        private QuestionImgAdapter mAdapter;
        private List<QuestionImgData> imgList = new ArrayList<>();
        private List<String> mImages = new ArrayList<>();

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

            mAdapter = new QuestionImgAdapter(getContext());
            mRvImg.setLayoutManager(new GridLayoutManager(getContext(), 4));
            mRvImg.setAdapter(mAdapter);
            mAdapter.setItemClickListener(new QuestionImgAdapter.OnItemClick() {
                @Override
                public void onItemClick(int position) {
                    if (imgList.get(position).getType() == QuestionImgData.Add_Visible) {
                        // 裁剪
                        ImageSelectActivity.start(activity, data -> {
                            cropImageFile(new File(data.get(0)));
                        });
                    } else {
                        // 查看图片
                        ImagePreviewActivity.start(getActivity(), imgList.get(position).getUrl().toString());
                    }
                }

                @Override
                public void onCloseItemClick(int position) {
                    if (imgList.get(position).getType() == QuestionImgData.Add_Gone) {
                        imgList.remove(position);
                        mAdapter.initData(imgList);
                    }
                }


            });

            mLlBtnExamine.setOnClickListener(view -> {
                List<String> data = new ArrayList<>();
                data.add(ConstructionRecordBean.Type_OK_TIP);
                data.add(ConstructionRecordBean.Type_Installation_Warring_TIP);
                data.add(ConstructionRecordBean.Type_Maintenance_Warring_TIP);
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
                                switch (position) {
                                    case 0:
                                        status = ConstructionRecordBean.Type_OK;
                                        break;
                                    case 1:
                                        status = ConstructionRecordBean.Type_Installation_Warring;
                                        break;
                                    case 2:
                                        status = ConstructionRecordBean.Type_Maintenance_Warring;
                                        break;
                                }
                                mTvExamine.setText(string);
                            }

                            @Override
                            public void onCancel(BaseDialog dialog) {
//                                    toast("取消了");
                            }
                        })
                        .show();
            });

            mBtnModification.setOnClickListener(view -> {
                if (mListener == null) {
                    return;
                }
                String describe = mEtDescription.getText().toString().trim();
                if (TextUtils.isEmpty(describe)) {
                    ToastUtils.show("描述不能为空");
                    return;
                }
                ConstructionRecordBean recordBean = new ConstructionRecordBean();
                recordBean.setDevice_work_id(data.getDevice_work_id());
                recordBean.setDevice_id(data.getDevice_id());
                recordBean.setShop_name(data.getShop_name());
                recordBean.setAddress(data.getAddress());
                recordBean.setCreate_time(data.getCreate_time());
                //描述
                recordBean.setStatus_info(describe);
                //检查状态
                recordBean.setStatus(status);
                //图片
                String img = mAdapter.getImgData()+",";
                recordBean.setStatus_img(img);
                mListener.onSubmit(getDialog(), recordBean);
            });

        }

        public UpdateWorkRecordDialog.Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public UpdateWorkRecordDialog.Builder setBaseActivity(BaseActivity basActivity) {
            activity = basActivity;
            return this;
        }

        public UpdateWorkRecordDialog.Builder setData(ConstructionRecordBean recordBean) {
            this.data = recordBean;
            mEtDescription.setText(data.getStatus_info());
            mTvExamine.setText(data.getStatusTip());
            if (!TextUtils.isEmpty(recordBean.getStatus_img())) {
                String img = recordBean.getStatus_img() + ",";
                mImages = Arrays.asList(img.split(","));
            }
            setAdapterData(1);
            return this;
        }

        /**
         * @param type 1 初始数据 2 选择图片后
         */
        private void setAdapterData(int type) {
            if (type == 2) {
                QuestionImgData data = new QuestionImgData();
                data.setUrl(mQuestionUrl);
                data.setType(QuestionImgData.Add_Gone);
                imgList.add(imgList.size() - 1, data);
            } else {
                for (int i = 0; i < mImages.size(); i++) {
                    QuestionImgData imgData = new QuestionImgData();
                    imgData.setUrl(mImages.get(i));
                    imgData.setType(QuestionImgData.Add_Gone);
                    imgList.add(imgData);
                }
                QuestionImgData imgData = new QuestionImgData();
                imgData.setType(QuestionImgData.Add_Visible);
                imgList.add(imgData);
            }
            mAdapter.initData(imgList);
        }

        /**
         * 裁剪图片
         */
        private void cropImageFile(File sourceFile) {
            ImageCropActivity.start(activity, sourceFile, 1, 1, new ImageCropActivity.OnCropListener() {

                @Override
                public void onSucceed(Uri fileUri, String fileName) {
                    File outputFile;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        outputFile = new FileContentResolver(getActivity(), fileUri, fileName);
                    } else {
                        try {
                            outputFile = new File(new URI(fileUri.toString()));
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                            outputFile = new File(fileUri.toString());
                        }
                    }
                    updateCropImage(outputFile, true);
                }

                @Override
                public void onError(String details) {
                    // 没有的话就不裁剪，直接上传原图片
                    // 但是这种情况极其少见，可以忽略不计
                    updateCropImage(sourceFile, false);
                }
            });
        }

        /**
         * 上传裁剪后的图片
         */
        private void updateCropImage(File file, boolean deleteFile) {
            if (true) {
                if (file instanceof FileContentResolver) {
                    mQuestionUrl = ((FileContentResolver) file).getContentUri();
                } else {
                    mQuestionUrl = Uri.fromFile(file);
                }
                setAdapterData(2);
                return;
            }
        /*EasyHttp.post(this)
                .api(new UpdateImageApi()
                        .setImage(file))
                .request(new HttpCallback<HttpData<String>>(this) {

                    @Override
                    public void onSucceed(HttpData<String> data) {
                        mQuestionUrl = Uri.parse(data.getData());
                        GlideApp.with(getActivity())
                                .load(mQuestionUrl)
                                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                                .into(mAvatarView);
                        if (deleteFile) {
                            file.delete();
                        }
                    }
                });*/
        }

    }

    public interface OnListener {

        /**
         * 点击提交
         */
        void onSubmit(BaseDialog dialog, ConstructionRecordBean data);
    }

}
