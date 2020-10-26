package com.tai.TestTestTest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class MyImageView extends AppCompatImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);//ImageView控件原宽度
            int height = (int)Math.ceil(((double)drawable.getIntrinsicHeight() / (double)drawable.getIntrinsicWidth()) * (double)width);
            //                              (图片高 / 图片宽) * 控件宽 = 宽度填满控件，高度等比例放大
            setMeasuredDimension(width, height);
        }
        else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
