package com.tai.TestTestTest;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {
    String TAG = "LoadingActivityTAG";
    private String wideText, highText;

    private AVLoadingIndicatorView loading;
    private Button OK, open, close;
    private EditText wide, high;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initView();
        myPurpose();
        myListener();
    }

    private void initView() {
        loading = findViewById(R.id.loading);
        OK      = findViewById(R.id.OK);
        open    = findViewById(R.id.open);
        close   = findViewById(R.id.close);
        wide    = findViewById(R.id.wide);
        high    = findViewById(R.id.high);
    }

    private void myPurpose() {
    }

    private void myListener() {
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int wideData, highData;
                MethodList.UnitChange uc = new MethodList.UnitChange(LoadingActivity.this);
                wideText = String.valueOf(wide.getText());
                highText = String.valueOf(high.getText());
                wide.setText("");
                high.setText("");
                if (wideText.equals(""))
                    wideData = getResources().getDimensionPixelOffset(R.dimen.defaultWide);
                else
                    wideData = uc.fromDpToPx(Float.parseFloat(wideText));
                if (highText.equals(""))
                    highData = getResources().getDimensionPixelOffset(R.dimen.defaultHigh);
                else
                    highData = uc.fromDpToPx(Float.parseFloat(highText));
                ViewGroup.LayoutParams params = loading.getLayoutParams();
                MyLog.i(TAG, new DebugInfo(), "宽：" + uc.fromPxToDp((float) wideData) + "dp");
                MyLog.i(TAG, new DebugInfo(), "高：" + uc.fromPxToDp((float) highData) + "dp");
                params.width  = wideData;
                params.height = highData;
                loading.setLayoutParams(params);
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.INVISIBLE);
                final MyDialog dialog = MyDialog.showDialog(LoadingActivity.this);
                dialog.show();
                dialog.setCancelable(false);//设置触摸弹窗外及按返回键弹窗不消失
                //dialog.setCanceledOnTouchOutside(false);//设置触摸弹窗外弹窗不消失（返回键会消失）
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                }, 2000);
            }
        });
    }
}
