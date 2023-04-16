package com.sgd.tjlb.zhxf.ui.activity.func;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppActivity;
import com.sgd.tjlb.zhxf.entity.TipBean;
import com.sgd.tjlb.zhxf.http.api.FindTipByTypeApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;

import androidx.core.text.HtmlCompat;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT;

/**
 * 隐私协议
 */
public class AgreementActivity extends AppActivity {

    private final static String TYPE_CONTENT = "TYPE_CONTENT";

    private TextView tv_content;

    private int mType;
    private TipBean mTipBean;
    private String mContent;

    public static void start(Context context, String content) {
        Intent starter = new Intent(context, AgreementActivity.class);
        starter.putExtra(TYPE_CONTENT, content);
        context.startActivity(starter);
    }

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
        mContent = getString(TYPE_CONTENT);

        mContent = TextUtils.isEmpty(mContent) ? "" : mContent;

        tv_content.setText(HtmlCompat.fromHtml(mContent,FROM_HTML_MODE_COMPACT ));
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
        if (mTipBean != null) {
            tv_content.setText(mTipBean.getNews_info());
        }
    }
}
