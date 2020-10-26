package com.tai.TestTestTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class TestAutoHeightViewPager extends ViewPager {
    int width;
    boolean listenerFlag = true;
    //private DebugInfo info = new DebugInfo(29);

    public TestAutoHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        TestAutoHeightViewPagerAdapter adapter = (TestAutoHeightViewPagerAdapter) getAdapter();
        if (adapter == null)
            throw new NullPointerException("类AutoHeightViewPager中onMeasure方法获得的Adapter为空");
        View view = adapter.getIndexView(getCurrentItem());
        if (view == null)
            throw new NullPointerException("类AutoHeightViewPager中onMeasure方法获得的view为空");
        view.measure(0, 0);
        width = MeasureSpec.getSize(widthMeasureSpec);
        /*int mode = MeasureSpec.getMode(widthMeasureSpec);
        MyLog.e("AutoHeightViewPagerTAG", info, "width：" + width + "  mode：" + mode
                + "  widthMeasureSpec：" + widthMeasureSpec + "  make：" + MeasureSpec.makeMeasureSpec(width, mode));*/
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(measureHeight(heightMeasureSpec, view), MeasureSpec.EXACTLY));
    }

    private int measureHeight(int measureSpec, View view) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);// 模式
        int specSize = MeasureSpec.getSize(measureSpec);// 尺寸

        if (specMode == MeasureSpec.EXACTLY) {
            //Log.i("TAG", "精确模式");
            result = specSize;// 精确模式直接返回尺寸
        }
        else {
            result = view.getMeasuredHeight();
            if (specMode == MeasureSpec.AT_MOST) {// else无限制模式返回view的高度
                //Log.i("TAG", "父控件最大空间限制模式");
                result = Math.min(result, specSize);// 父控件最大空间模式返回view高度和最大尺寸之间的最小值
            }
                //Log.i("TAG", "无限制模式");
        }
        return result;
    }

    @Override
    public void setCurrentItem(int item) {
        listenerFlag = false;
        super.setCurrentItem(item);
    }
}

