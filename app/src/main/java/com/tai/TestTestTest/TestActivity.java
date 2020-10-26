package com.tai.TestTestTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class TestActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button text, edit;
    private View view;
    private Button changeColor;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
        myProcess();
        myListener();
    }

    private void initView() {
        textView    = findViewById(R.id.textView);
        editText    = findViewById(R.id.editText);
        text        = findViewById(R.id.text);
        edit        = findViewById(R.id.edit);
        view        = findViewById(R.id.view);
        changeColor = findViewById(R.id.changeColor);
    }

    private void myProcess() {
    }

    private void myListener() {
        text.setOnClickListener(new View.OnClickListener() {
            private boolean noClick = true;

            @Override
            public void onClick(View v) {
                if (noClick) {
                    noClick = false;
                    textView.setEnabled(false);
                }
                else {
                    noClick = true;
                    textView.setEnabled(true);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            private boolean noClick = true;

            @Override
            public void onClick(View v) {
                if (noClick) {
                    noClick = false;
                    editText.setEnabled(false);
                }
                else {
                    noClick = true;
                    editText.setEnabled(true);
                }
            }
        });

        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setBackgroundColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }
        });
    }
}