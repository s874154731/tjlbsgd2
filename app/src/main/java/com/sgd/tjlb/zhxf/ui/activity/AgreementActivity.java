package com.sgd.tjlb.zhxf.ui.activity;

import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.TipBean;
import com.sgd.tjlb.zhxf.http.api.FindTipByTypeApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;

public class AgreementActivity extends AppActivity {

    private TextView tv_content;

    private int mType;
    private TipBean mTipBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView() {

        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    protected void initData() {
        findTips();
    }


    //获取tip
    private void findTips() {
        EasyHttp.post(this)
                .api(new FindTipByTypeApi()
                        .setType(mType)
                )
                .request(new HttpCallback<HttpData<TipBean>>(this) {

                    @Override
                    public void onSucceed(HttpData<TipBean> data) {
                        if (data.getData() != null) {
                            mTipBean = data.getData();
                            refreshUI();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    private void refreshUI() {
        if (mTipBean != null){
            tv_content.setText(mTipBean.getNews_info());
        }
    }
}
