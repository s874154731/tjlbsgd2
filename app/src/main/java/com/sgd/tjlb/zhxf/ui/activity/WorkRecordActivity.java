package com.sgd.tjlb.zhxf.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.toast.ToastUtils;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.WorkRecordDetailsData;
import com.sgd.tjlb.zhxf.manager.InputTextManager;
import com.sgd.tjlb.zhxf.other.GridSpaceDecoration;
import com.sgd.tjlb.zhxf.ui.adapter.ImageSelectAdapter;
import com.sgd.tjlb.zhxf.ui.dialog.PayPasswordDialog;
import com.sgd.tjlb.zhxf.ui.dialog.UpdateWorkRecordDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作记录 --- activity
 */
public class WorkRecordActivity extends AppActivity {

    private TextView mTvTime;
    private RecyclerView mRvImg;
    private TextView mTvDescription;
    private TextView mTvExamine;
    private AppCompatButton mBtnModification;

    private ImageSelectAdapter mAdapter;//图片展示

    private List<String> mImages = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, WorkRecordActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_record;
    }

    @Override
    protected void initView() {

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
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                if (mImages.size() != 0) {

                }
            }
        });
        mRvImg.setAdapter(mAdapter);
        // 禁用动画效果
        mRvImg.setItemAnimator(null);
        // 添加分割线
        mRvImg.addItemDecoration(new GridSpaceDecoration((int) getResources().getDimension(R.dimen.dp_3)));
    }

    private void initListener() {
        mBtnModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWorkRecord();
            }
        });
    }

    /**
     * 修改工作记录弹窗
     */
    private void updateWorkRecord() {
        WorkRecordDetailsData data = new WorkRecordDetailsData();
        data.setDescribe("工作描述按个高危岗位");
        data.setExamine("安装异常");
        new UpdateWorkRecordDialog.Builder(this)
                .setData(data)
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setListener(new UpdateWorkRecordDialog.OnListener() {
                    @Override
                    public void onSubmit(BaseDialog dialog, WorkRecordDetailsData data) {
                        ToastUtils.show(data.getDescribe());
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void initData() {
        mImages.add("https://img1.baidu.com/it/u=3012806272,1276873993&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        mImages.add("https://img1.baidu.com/it/u=3012806272,1276873993&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        mImages.add("https://img1.baidu.com/it/u=3012806272,1276873993&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        mImages.add("https://img1.baidu.com/it/u=3012806272,1276873993&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        mAdapter.initData(mImages);
    }

}
