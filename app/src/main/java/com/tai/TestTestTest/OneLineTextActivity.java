package com.tai.TestTestTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

public class OneLineTextActivity extends AppCompatActivity {
    private TextView text1;
    private EditText edit1, edit2, textE;
    private Button   btn1, btn2;
    private HorizontalScrollView HScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_line_text);

        initView();
        myProcess();
        myListener();
    }

    private void initView() {
        text1   = findViewById(R.id.text1);
        textE   = findViewById(R.id.textE);
        edit1   = findViewById(R.id.edit1);
        edit2   = findViewById(R.id.edit2);
        btn1    = findViewById(R.id.btn1);
        btn2    = findViewById(R.id.btn2);
        HScroll = findViewById(R.id.HScroll);
    }

    private void myProcess() {
        textE.setSelection(textE.length());
    }

    private void myListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(edit1.getText().toString());
                HScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);//加一个Timer延时器效果更好
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textE.setText(edit2.getText().toString());
                textE.setSelection(textE.length());
            }
        });
    }
}
