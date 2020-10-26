package com.tai.TestTestTest;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Timer;
import java.util.TimerTask;

public class DownFreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_fresh);

        final SwipeRefreshLayout fresh = findViewById(R.id.fresh);
        fresh.setColorSchemeColors(
                Color.parseColor("#DD191D"),//红
                Color.parseColor("#FB8C00"),//橙
                Color.parseColor("#FFEB3B"),//黄
                Color.parseColor("#0A8F08"),//绿
                Color.parseColor("#76FF03"),//青
                Color.parseColor("#29B6F6"),//蓝
                Color.parseColor("#8E24AA") //紫
        );
        fresh.setSize(SwipeRefreshLayout.DEFAULT);//默认大小，LARGE偏大
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        fresh.post(new Runnable() {
                            @Override
                            public void run() {
                                fresh.setRefreshing(false);
                            }
                        });
                    }
                };
                new Timer().schedule(task, 8000);
            }
        });
    }
}
