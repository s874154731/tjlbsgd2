package com.sgd.tjlb.zhxf.http.model;

import com.google.gson.annotations.SerializedName;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/07
 *    desc   : 统一接口数据结构
 */
public class HttpData<T> {

    /** 返回码 */
    @SerializedName("code")
    private int result;
    /** 提示语 */
    private String msg;
    /** 数据 */
    private T data;

    public int getCode() {
        return result;
    }

    public String getMessage() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 是否请求成功
     */
    public boolean isRequestSucceed() {
        return result == 200;
    }

    /**
     * 是否 Token 失效
     */
    public boolean isTokenFailure() {
        return result == 1001;
    }
}