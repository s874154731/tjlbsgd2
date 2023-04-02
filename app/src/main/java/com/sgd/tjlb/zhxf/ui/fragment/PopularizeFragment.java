package com.sgd.tjlb.zhxf.ui.fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SwitchButton;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.TitleBarFragment;
import com.sgd.tjlb.zhxf.entity.PopularizeTypeBean;
import com.sgd.tjlb.zhxf.helper.MMKVHelper;
import com.sgd.tjlb.zhxf.http.api.PopularizeTypeApi;
import com.sgd.tjlb.zhxf.http.model.HttpData;
import com.sgd.tjlb.zhxf.ui.activity.init.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 消防宣传页
 */
public final class PopularizeFragment extends TitleBarFragment<HomeActivity>
        implements SwitchButton.OnCheckedChangeListener {

    private final String TAB_TITLE_NEWS = "消防新闻";
    private final String TAB_TITLE_FIND = "消防法现";
    private final String TAB_TITLE_KNOLWLEDGE = "消防知识";

    private final String[] mTabs = {
            TAB_TITLE_NEWS,
            TAB_TITLE_FIND,
            TAB_TITLE_KNOLWLEDGE
    };

    private List<PopularizeTypeBean> mPoTypeList = new ArrayList<>();

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;

    public static PopularizeFragment newInstance() {
        return new PopularizeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_popularize;
    }

    @Override
    protected void initView() {
        mTabLayout = findViewById(R.id.tab_popularize);
        mViewPager2 = findViewById(R.id.vp2_popularize);

    }

    private void initViewPager() {
        mViewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return PopularizeDataFragment.getInstance(mPoTypeList.get(position));
            }

            @Override
            public int getItemCount() {
                return mPoTypeList.size();
            }
        });

        mViewPager2.setOffscreenPageLimit(mPoTypeList.size());

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                mTabLayout,
                mViewPager2, (tab, position) -> tab.setText(mPoTypeList.get(position).getNews_type_name())

        );
        tabLayoutMediator.attach();

        mViewPager2.setCurrentItem(0);
    }

    @Override
    protected void initData() {
        findPopularizeType();
    }

    //获取宣传分类
    private void findPopularizeType() {
        mPoTypeList.clear();
        List<PopularizeTypeBean> tempList = MMKVHelper.getInstance().findPopularizeTypes();

        if (tempList != null && tempList.size() > 0) {
            mPoTypeList.addAll(tempList);
            initViewPager();
            return;
        }

        EasyHttp.post(this)
                .api(new PopularizeTypeApi())
                .request(new HttpCallback<HttpData<List<PopularizeTypeBean>>>(this) {

                    @Override
                    public void onSucceed(HttpData<List<PopularizeTypeBean>> data) {
                        if (data.getData() != null) {
                            if (data.getData().size() > 0) {
                                mPoTypeList.addAll(data.getData());
                                initViewPager();
                            }
                            MMKVHelper.getInstance().savePopularizeTypes(data.getData());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                    }
                });
    }

    @Override
    public void onCheckedChanged(SwitchButton button, boolean checked) {

    }

}
