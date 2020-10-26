package com.tai.TestTestTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_test);

        final LinearLayout parentLinear = findViewById(R.id.parentLinear);
        Button showButton   = findViewById(R.id.showButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View childLinear = View.inflate(TimerTestActivity.this, R.layout.timer_test_layout, null);
                final TextView textTimer = childLinear.findViewById(R.id.textTimer);
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        textTimer.post(new Runnable() {
                            @Override
                            public void run() {
                                textTimer.setText("Mr.Tai");
                            }
                        });
                        parentLinear.post(new Runnable() {
                            @Override
                            public void run() {
                                parentLinear.addView(childLinear);
                            }
                        });
                    }
                };
                new Timer().schedule(task, 2000);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View childLinear = findViewById(R.id.childLinear);
                parentLinear.removeView(childLinear);
            }
        });
    }
}
