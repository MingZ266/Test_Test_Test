package com.tai.TestTestTest;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

class MyDialog extends Dialog {
    private Context context;
    private static MyDialog[] dialog = new MyDialog[1];

    private MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    static MyDialog showDialog(Context context) {
        dialog[0] = new MyDialog(context, R.style.myDialog);
        dialog[0].setContentView(R.layout.dialog_layout);
        dialog[0].setCanceledOnTouchOutside(false);
        return dialog[0];
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && dialog[0] != null) {
            ImageView ivProgress = dialog[0].findViewById(R.id.ivProgress);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading);
            ivProgress.startAnimation(animation);
        }
    }
}
