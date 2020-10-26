package com.tai.TestTestTest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button spBtn;
    private Spinner sp1, sp2, sp3;
    private String sp1Str, sp2Str, sp3Str;
    private String[] strings;
    private List<String> stringList;
    private final String defaultStr = "请选择";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        initView();
        initListener();
        myProcess();
        myListener();
    }

    private void initView() {
        spBtn = findViewById(R.id.spBtn);
        sp1   = findViewById(R.id.sp1);
        sp2   = findViewById(R.id.sp2);
        sp3   = findViewById(R.id.sp3);
    }

    private void initListener() {
        sp1.setOnItemSelectedListener(SpinnerActivity.this);
        sp2.setOnItemSelectedListener(SpinnerActivity.this);
        sp3.setOnItemSelectedListener(SpinnerActivity.this);
    }

    private void myProcess() {
        sp1Str = "";
        sp2Str = "";
        sp3Str = "";
        strings = new String[] {defaultStr, "是", "像", "成为了", "真的"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SpinnerActivity.this, android.R.layout.simple_spinner_dropdown_item/*android.R.layout.simple_spinner_item*/, strings);
        sp2.setAdapter(arrayAdapter);

        stringList = new ArrayList<>();
        stringList.add(defaultStr);
        stringList.add("狗");
        stringList.add("笨蛋");
        stringList.add("召唤兽");
        stringList.add("美少女");
        stringList.add("？");
        sp3.setAdapter(new StringListAdapter());
    }

    private void myListener() {
        spBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                if (sp1Str.equals(defaultStr) || sp2Str.equals(defaultStr) || sp3Str.equals(defaultStr))
                    text = "没有选择完全部";
                else
                    text = sp1Str + sp2Str + sp3Str;
                Toast.makeText(SpinnerActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp1:
                sp1Str = getResources().getStringArray(R.array.strings)[position];
                break;
            case R.id.sp2:
                sp2Str = strings[position];
                break;
            case R.id.sp3:
                sp3Str = stringList.get(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private class StringListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(SpinnerActivity.this, android.R.layout.simple_spinner_dropdown_item, null);
            CheckedTextView text = view.findViewById(android.R.id.text1);
            text.setText(stringList.get(position));
            return view;
        }
    }
}
