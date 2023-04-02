package com.sgd.tjlb.zhxf.repository;

import androidx.lifecycle.LifecycleOwner;

public class HomeRepository {

    private HomeRepository() {
    }

    private static class SingletonHolder {
        private static final HomeRepository instance = new HomeRepository();
    }

    public static HomeRepository getInstance() {
        return SingletonHolder.instance;
    }


    public <T> T findShopInfo(LifecycleOwner lifecycleOwner, CallBack<T> callBack) {
        /*EasyHttp.post(lifecycleOwner)
                .api(new GetShopApi())
                .request(new HttpCallback<HttpData<T>>(this) {

                    @Override
                    public void onSucceed(HttpData<T> data) {
                        if (data.getData() != null) {
                            if (callBack != null) {
                                callBack.onSuccess(data.getData());
                            }
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        if (callBack != null)
                            callBack.onFail(e.toString());
                    }
                });*/
        return null;
    }

    public interface CallBack<T> {
        void onSuccess(T t);

        void onFail(String error);
    }
}
