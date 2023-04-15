package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.model.FileContentResolver;
import com.hjq.widget.view.RegexEditText;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.QuestionImgData;
import com.sgd.tjlb.zhxf.http.api.AddDeviceMaintenanceRecordApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.InputTextManager;
import com.sgd.tjlb.zhxf.ui.adapter.QuestionImgAdapter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备维修
 */
public class ApplyForMaintenanceActivity extends AppActivity {

    private final static String KEY_DEVICEID = "KEY_DEVICEID";

    private RegexEditText mEtCall;//联系方式
    private RegexEditText mEtQuestion;//问题描述
    private RecyclerView mRvQuestionImg;//问题图片
    private AppCompatButton mBtnSubmit;

    private Uri mQuestionUrl;
    private QuestionImgAdapter mAdapter;
    private List<QuestionImgData> imgList = new ArrayList<>();

    private String mDeviceID;//设备id
    private List<File> mUploadImgs = new ArrayList<>();

    public static void start(Context context, String deviceID) {
        Intent starter = new Intent(context, ApplyForMaintenanceActivity.class);
        starter.putExtra(KEY_DEVICEID, deviceID);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_applyfor_maintenance;
    }

    @Override
    protected void initView() {

        mEtCall = (RegexEditText) findViewById(R.id.et_applyfor_maintenance_call);
        mEtQuestion = (RegexEditText) findViewById(R.id.et_applyfor_maintenance_question);
        mRvQuestionImg = (RecyclerView) findViewById(R.id.rv_applyfor_maintenance_questionimg);
        mBtnSubmit = (AppCompatButton) findViewById(R.id.btn_applyfor_maintenance_submit);

        mDeviceID = getIntent().getStringExtra(KEY_DEVICEID);

        InputTextManager.with(this)
                .addView(mEtCall)
                .addView(mEtQuestion)
                .setMain(mBtnSubmit)
                .build();

        initRecyclerView();
        initListener();
    }

    private void initRecyclerView() {
        mAdapter = new QuestionImgAdapter(this);
        mRvQuestionImg.setLayoutManager(new GridLayoutManager(this, 5));
        mRvQuestionImg.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new QuestionImgAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                if (imgList.get(position).getType() == QuestionImgData.Add_Visible) {
                    // 裁剪
                    ImageSelectActivity.start(ApplyForMaintenanceActivity.this, data -> {
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

        /*mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                if (imgList.size() != 0) {
                    if (imgList.get(position).getType() == QuestionImgData.Add_Visible) {
                        // 裁剪头像
                        ImageSelectActivity.start(ApplyForMaintenanceActivity.this, data -> {
                            // 裁剪头像
                            cropImageFile(new File(data.get(0)));
                        });
                    }

                }

            }
        });*/

    }

    private void initListener() {
        mBtnSubmit.setOnClickListener(view -> {
            submitApply();
        });
    }

    /**
     * 裁剪图片
     */
    private void cropImageFile(File sourceFile) {
        ImageCropActivity.start(this, sourceFile, 1, 1, new ImageCropActivity.OnCropListener() {

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

    @Override
    protected void initData() {
        setAdapterData(1);

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
            QuestionImgData imgData = new QuestionImgData();
            imgData.setType(QuestionImgData.Add_Visible);
            imgList.add(imgData);
        }
        mAdapter.initData(imgList);
    }

    //提交维修单
    private void submitApply() {

        if (TextUtils.isEmpty(mEtCall.getText().toString())) {
            mEtCall.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast("请输入联系方式");
            return;
        }

        if (TextUtils.isEmpty(mEtQuestion.getText().toString())) {
            mEtQuestion.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
            toast("请描述问题");
            return;
        }

        EasyHttp.post(this)
                .api(new AddDeviceMaintenanceRecordApi()
                        .setDevice_id(mDeviceID)
                        .setStatus_img("图片地址")
                        .setStatus_info(mEtQuestion.getText().toString().trim())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        toast("已提交");
                        finish();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
