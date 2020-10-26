package com.tai.TestTestTest;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Field;

public class ImmersionTitleActivity extends AppCompatActivity {
    String TAG = "ImmersionTitleActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //状态栏字体黑色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //去除状态栏阴影
        try {
            Class<? extends View> decorViewClazz = getWindow().getDecorView().getClass();
            Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
            field.setAccessible(true);
            field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            Log.d(TAG, "getDeclaredField异常");
            Log.d(TAG, "去除状态栏阴影失败");
        } catch (IllegalAccessException e) {
            Log.d(TAG, "setInt异常");
            Log.d(TAG, "去除状态栏阴影失败");
        }

        setContentView(R.layout.activity_immersion_title);

        Toolbar testTitle = findViewById(R.id.testTitle);
        testTitle.setTitle(R.string.title);
        setSupportActionBar(testTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_title_menu, menu);
//        for (int i = 0; i < menu.size(); i++) {
//            String itemTitle = (String) menu.getItem(i).getTitle();
//            SpannableString itemChange = new SpannableString(itemTitle);
//            //Log.i("Main", );
//            ForegroundColorSpan itemColor = new ForegroundColorSpan(Color.parseColor("#4ABAC4"));
//            itemChange.setSpan(itemColor, 0, itemTitle.length(), 0);
//        }
        return super.onCreateOptionsMenu(menu);
    }
}
