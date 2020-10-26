package com.tai.TestTestTest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class TimeActivity extends AppCompatActivity {
    final int delayTime = 3000;
    final String defaultStr = "";
    private DateFormat formatter;
    private Calendar calendar;

    private TextView tvNetTime;
    private Button btnNetTime;
    private Button btnLocalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        initView();
        myListener();
    }

    private void settingTzIs8(boolean settingIt) {
        if (settingIt)
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        else
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        calendar  = Calendar.getInstance();
    }

    private void initView() {
        tvNetTime = findViewById(R.id.tv_nettime);
        btnNetTime = findViewById(R.id.btn_nettime);
        btnLocalTime = findViewById(R.id.btn_localtime);
        tvNetTime.setText(defaultStr);
    }

    private void myListener() {
        btnNetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取网络时间
                //请求网络资源是耗时操作。放到子线程中进行
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        checkOutTime();
                        getNetTime();
                        Looper.loop();
                    }
                }).start();
            }
        });
        btnLocalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取系统时间
                getLocalTime();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getNetTime() {
        URL url;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            settingTzIs8(true);
            calendar.setTimeInMillis(ld);
            final String format = formatter.format(calendar.getTime());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvNetTime.setText("当前网络时间为: \n" + format);
                }
            });
        } catch (Exception e) {
            MyLog.i("TAG", new DebugInfo(), "网络异常");
        }
    }

    @SuppressLint("SetTextI18n")
    private void getLocalTime() {
        settingTzIs8(false);
        calendar.setTimeInMillis(System.currentTimeMillis());
        final String format = formatter.format(calendar.getTime());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvNetTime.setText("当前系统时间为: \n" + format);
            }
        });
    }

    private void checkOutTime() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Looper.prepare();
                if (String.valueOf(tvNetTime.getText()).equals(defaultStr)) {
                    Toast.makeText(TimeActivity.this, "获取网络时间失败", Toast.LENGTH_SHORT).show();
                    getLocalTime();
                }
                Looper.loop();
            }
        }, delayTime);
    }
}
