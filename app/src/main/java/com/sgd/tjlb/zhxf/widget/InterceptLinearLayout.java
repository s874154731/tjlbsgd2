package com.sgd.tjlb.zhxf.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/*
 * 时间：2022/3/28 17:02
 * 说明：
 * */
public class InterceptLinearLayout extends LinearLayout {

    private boolean mIntercept;

    public void setmIntercept(boolean mIntercept) {
        this.mIntercept = mIntercept;
    }

    public InterceptLinearLayout(@NonNull @NotNull Context context) {
        super(context);
    }

    public InterceptLinearLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptLinearLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public InterceptLinearLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mIntercept){
            return mIntercept;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
