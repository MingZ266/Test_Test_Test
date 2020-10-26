package com.tai.TestTestTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NdkTestActivity extends AppCompatActivity {
    private TextView text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_test);

        initView();
        myListener();
    }

    private void initView() {
        text = findViewById(R.id.text);
        button = findViewById(R.id.button);
    }

    private void myListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("暂未研究出来，待续...");
            }
        });
    }
}