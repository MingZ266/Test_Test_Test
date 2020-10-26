package com.tai.TestTestTest;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

class MethodList {
    static class AboutView {
        // 在view没有指定的父控件时，math_parent效果同warp_content
        private View view;

        void setView(View view) {
            if (view == null)
                throw new NullPointerException("传给MethodList.AboutView的view为空");
            this.view = view;
            this.view.measure(0, 0);
        }

        int getViewW() {
            if (view == null)
                throw new NullPointerException("MethodList.AboutView中的view为空");
            return view.getMeasuredWidth();
        }

        int getViewH() {
            if (view == null)
                throw new NullPointerException("MethodList.AboutView中的view为空");
            return view.getMeasuredHeight();
        }
    }

    static class UnitChange {
        private DisplayMetrics dm;

        UnitChange(Context context) {
            dm = context.getResources().getDisplayMetrics();
        }

        int fromPxToDp(float pxValue) {
            return (int) (pxValue / dm.density + 0.5f);
        }

        int fromPxToSp(float pxValue) {
            return (int) (pxValue / dm.scaledDensity + 0.5f);
        }

        int fromDpToPx(float dpValue) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, dm);
        }

        int fromSpToPx(float spValue) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, dm);
        }
    }
}
