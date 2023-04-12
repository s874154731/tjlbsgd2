package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.ConstructionRecordBean;
import com.sgd.tjlb.zhxf.entity.WorkRecordDetailsData;
import com.sgd.tjlb.zhxf.http.api.FindConstructionRecordByIDApi;
import com.sgd.tjlb.zhxf.http.api.MyConstructionRecordListApi;
import com.sgd.tjlb.zhxf.http.api.UpdateConstructionRecordByIDApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.manager.InputTextManager;
import com.sgd.tjlb.zhxf.other.GridSpaceDecoration;
import com.sgd.tjlb.zhxf.ui.adapter.ImageSelectAdapter;
import com.sgd.tjlb.zhxf.ui.dialog.PayPasswordDialog;
import com.sgd.tjlb.zhxf.ui.dialog.UpdateWorkRecordDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 工作记录 --- activity
 */
public class WorkRecordActivity extends AppActivity {

    private static final String INTENT_KEY_PARAME = "INTENT_KEY_PARAME";

    private TextView mTvTime;
    private RecyclerView mRvImg;
    private TextView mTvDescription;
    private TextView mTvExamine;
    private AppCompatButton mBtnModification;

    private ImageSelectAdapter mAdapter;//图片展示

    private List<String> mImages = new ArrayList<>();

    private String mRecordId;//工作记录id

    private ConstructionRecordBean recordBean;

    public static void start(Context context, String id) {
        Intent starter = new Intent(context, WorkRecordActivity.class);
        starter.putExtra(INTENT_KEY_PARAME, id);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_record;
    }

    @Override
    protected void initView() {
        mRecordId = getIntent().getStringExtra(INTENT_KEY_PARAME);

        mTvTime = (TextView) findViewById(R.id.tv_work_record_time);
        mRvImg = (RecyclerView) findViewById(R.id.rv_work_record_img);
        mTvDescription = (TextView) findViewById(R.id.tv_work_record_description);
        mTvExamine = (TextView) findViewById(R.id.tv_work_record_examine);
        mBtnModification = (AppCompatButton) findViewById(R.id.btn_work_record_modification);

        initRecyclerView();
        initListener();

    }

    private void initRecyclerView() {
        mAdapter = new ImageSelectAdapter(this, ImageSelectAdapter.No_Check);
        mAdapter.setOnItemClickListener((recyclerView, itemView, position) -> {
            if (mImages.size() != 0) {
                ImagePreviewActivity.start(getActivity(), mImages.get(position));
            }
        });
        mRvImg.setAdapter(mAdapter);
        // 禁用动画效果
        mRvImg.setItemAnimator(null);
        // 添加分割线
        mRvImg.addItemDecoration(new GridSpaceDecoration((int) getResources().getDimension(R.dimen.dp_3)));
    }

    private void initListener() {
        mBtnModification.setOnClickListener(view -> updateWorkRecord());
    }

    /**
     * 修改工作记录弹窗
     */
    private void updateWorkRecord() {
        if (recordBean == null) {
            return;
        }
        new UpdateWorkRecordDialog.Builder(this)
                .setData(recordBean)
                .setBaseActivity(WorkRecordActivity.this)
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new UpdateWorkRecordDialog.OnListener() {
                    @Override
                    public void onSubmit(BaseDialog dialog, ConstructionRecordBean data) {
                        updateConstructionRecord(dialog, data);
                    }
                })
                .show();
    }

    /**
     * 修改工作记录详情
     */
    private void updateConstructionRecord(BaseDialog dialog, ConstructionRecordBean data) {
        EasyHttp.post(this)
                .api(new UpdateConstructionRecordByIDApi()
                        .setDeviceWorkID(data.getDevice_work_id())
                        .setStatusImg(data.getStatus_img())
                        .setStatusInfo(data.getStatus_info())
                        .setStatus(data.getStatus())
                        .setDeviceId(data.getDevice_id())
                )
                .request(new HttpCallback<HttpData<Void>>(this) {

                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        ToastUtils.show("修改成功");
                        dialog.dismiss();
                        initData();
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    protected void initData() {
        findConstructionRecord();
    }

    private void findConstructionRecord() {
        EasyHttp.post(this)
                .api(new FindConstructionRecordByIDApi()
                        .setDeviceID(mRecordId)
                )
                .request(new HttpCallback<HttpData<ConstructionRecordBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<ConstructionRecordBean> data) {
                        if (data.getData() != null) {
                            recordBean = data.getData();
                            mTvTime.setText(recordBean.getCreate_time());
                            if (!TextUtils.isEmpty(recordBean.getStatus_img())) {
                                String img = recordBean.getStatus_img() + ",";
                                mImages = Arrays.asList(img.split(","));
                                mAdapter.initData(mImages);
                            }
                            mTvDescription.setText(recordBean.getStatus_info());
                            mTvExamine.setText("检查：" + recordBean.getStatusTip());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

}
