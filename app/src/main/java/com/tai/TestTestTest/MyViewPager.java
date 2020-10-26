package com.tai.TestTestTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private boolean canScroll = true;
    private MethodList.AboutView av = new MethodList.AboutView();

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public void resetPagerHeight(int pagerIndex) {
        if (getChildCount() <= pagerIndex)
            return;
        View child = getChildAt(pagerIndex);
        av.setView(child);
        int height = av.getViewH();
        ViewGroup.LayoutParams params = getLayoutParams();
        if (params == null)
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        else
            params.height = height;
        setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            av.setView(getChildAt(i));
            int h = av.getViewH();
            if (h > height)
                height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (canScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (canScroll)
            return super.onTouchEvent(ev);
        else
            return false;
    }
}
