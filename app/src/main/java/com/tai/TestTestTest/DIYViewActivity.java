package com.tai.TestTestTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import DiyView.DownloadProgressBar;
import DiyView.ImageCaptcha;
import DiyView.ProgressBar;
import DiyView.ProgressBar2;
import DiyView.TemplateDiyView;
import DiyView.WaterWaveRefresh;

public class DIYViewActivity extends AppCompatActivity {
    Context context = DIYViewActivity.this;
    private DownloadProgressBar myProgressBar;
    private Button go;
    private double nowNum = 0, maxNum = 100;
    private ImageCaptcha code;
    private EditText input;
    private Button OK;
    private ProgressBar2 testBar2;
    private TextView nowValue, maxValue;
    private EditText inputNow, inputMax;
    private Button getNow, getMax;
    private ProgressBar testBar;
    private EditText inputScale;
    private Button getScale;
    private WaterWaveRefresh waterWave;

    private TemplateDiyView testView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_view);

        initView();
        myProcess();
        myListener();
    }

    private void initView() {
        myProgressBar = findViewById(R.id.myProgressBar);
        go = findViewById(R.id.go);
        code = findViewById(R.id.code);
        input = findViewById(R.id.input);
        OK = findViewById(R.id.OK);
        testBar2 = findViewById(R.id.testBar2);
        nowValue = findViewById(R.id.nowValue);
        maxValue = findViewById(R.id.maxValue);
        inputNow = findViewById(R.id.inputNow);
        inputMax = findViewById(R.id.inputMax);
        getNow = findViewById(R.id.getNow);
        getMax = findViewById(R.id.getMax);
        testBar = findViewById(R.id.testBar);
        inputScale = findViewById(R.id.inputScale);
        getScale = findViewById(R.id.getScale);
        waterWave = findViewById(R.id.waterWave);

        testView = findViewById(R.id.testView);
    }

    @SuppressLint("SetTextI18n")
    private void myProcess() {
        myProgressBar.setMaxValue(maxNum);
        testBar2.setMaxValue(100);
        maxValue.setText("100");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testBar2.setPercentage(26);
                        nowValue.setText("26");
                    }
                });
            }
        }, 500);
    }

    private void myListener() {
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go.setEnabled(false);
                nowNum = 0;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        myProgressBar.post(new Runnable() {
                            @Override
                            public void run() {
                                myProgressBar.setProgressValue(nowNum);
                            }
                        });
                        nowNum += 2;
                        if (nowNum >= maxNum) {
                            this.cancel();
                            Looper.prepare();
                            go.post(new Runnable() {
                                @Override
                                public void run() {
                                    go.setEnabled(true);
                                }
                            });
                            Toast.makeText(context, "进度完成", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }, 0, 200);
            }
        });

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String in = String.valueOf(input.getText());
                if ("".equals(in)) {
                    Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    if (code.correctInput(in)) {
                        Toast.makeText(context, "正确", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                code.refresh();
                input.setText("");
            }
        });

        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code.refresh();
            }
        });

        getMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maxStr = String.valueOf(inputMax.getText());
                if ("".equals(maxStr))
                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
                else {
                    maxValue.setText(maxStr);
                    testBar2.setMaxValue(Double.parseDouble(maxStr));
                }
            }
        });

        getNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nowStr = String.valueOf(inputNow.getText());
                if ("".equals(nowStr))
                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
                else {
                    nowValue.setText(nowStr);
                    try {
                        testBar2.setPercentage(Double.parseDouble(nowStr));
                    } catch (ArithmeticException e) {
                        Toast.makeText(context, "未输入maxValue或输入的maxValue为0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        getScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scaleStr = String.valueOf(inputScale.getText());
                if ("".equals(scaleStr))
                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_SHORT).show();
                else
                    testBar.setScale(Double.parseDouble(scaleStr), 1);
            }
        });

        waterWave.setOnClickListener(new View.OnClickListener() {
            private boolean noClick = true;

            @Override
            public void onClick(View v) {
                if (noClick) {
                    noClick = false;
                    waterWave.startAnimator();
                } else {
                    noClick = true;
                    waterWave.stopAnimator();
                }
            }
        });

        testView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        waterWave.stopAnimator();
        super.onBackPressed();
    }
}