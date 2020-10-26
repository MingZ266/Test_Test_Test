package com.tai.TestTestTest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutListActivity extends AppCompatActivity {
    private LinearLayout view, text;
    private Button restart;
    private Button gridLayout, tableLayout, frameLayout, absoluteLayout;
    private Button radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_list);

        initView();
        myProcess();
        myListener();
    }

    private void initView() {
        view           = findViewById(R.id.view);
        text           = findViewById(R.id.text);
        restart        = findViewById(R.id.restart);
        // 四大布局
        gridLayout     = findViewById(R.id.gridLayout);
        tableLayout    = findViewById(R.id.tableLayout);
        frameLayout    = findViewById(R.id.frameLayout);
        absoluteLayout = findViewById(R.id.absoluteLayout);
        // 控件
        radioGroup     = findViewById(R.id.radioGroup);
    }

    private void myProcess() {
    }

    private void myListener() {
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.removeAllViews();
                view.addView(text);
                text.setVisibility(View.VISIBLE);
            }
        });

        gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.view, new GridLayoutFragment()).commitAllowingStateLoss();
            }
        });

        tableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        absoluteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.view, new RadioGroupFragment()).commitAllowingStateLoss();
            }
        });
    }

    public static class GridLayoutFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.layout_list_grid_layout, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            showToast((TextView) view.findViewById(R.id.one));
            showToast((TextView) view.findViewById(R.id.two));
            showToast((TextView) view.findViewById(R.id.three));
            showToast((TextView) view.findViewById(R.id.four));
            showToast((TextView) view.findViewById(R.id.five));
            showToast((TextView) view.findViewById(R.id.six));
            showToast((TextView) view.findViewById(R.id.seven));
            showToast((TextView) view.findViewById(R.id.eight));
            showToast((TextView) view.findViewById(R.id.nine));
            showToast((TextView) view.findViewById(R.id.zero));
        }

        private void showToast(final TextView textView) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
                    MyLog.i("TAG", new DebugInfo(), "真机调试" + textView.getText().toString());
                }
            });
        }
    }

    public static class RadioGroupFragment extends Fragment {
        private String character, sex;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
             return inflater.inflate(R.layout.layout_list_radio_group, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            RadioGroup radioGroupOne = view.findViewById(R.id.radioGroupOne);
            RadioGroup radioGroupTwo = view.findViewById(R.id.radioGroupTwo);
            Button okBtn = view.findViewById(R.id.okBtn);

            radioGroupOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.cute:
                            character = "可爱";
                            break;
                        case R.id.sexy:
                            character = "性感";
                            break;
                        case R.id.soft:
                            character = "温柔";
                            break;
                        case R.id.hasty:
                            character = "轻率";
                            break;
                        case R.id.natural:
                            character = "天然";
                            break;
                        case R.id.scheming:
                            character = "腹黑";
                            break;
                        default:
                            character = "Error";
                    }
                }
            });

            radioGroupTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.man:
                            sex = "男性";
                            break;
                        case R.id.woman:
                            sex = "女孩子";
                            break;
                        case R.id.unknown:
                            sex = "**(不告诉你)";
                            break;
                        default:
                            sex = "Error";
                    }
                }
            });

            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "这有一个" + character + "的" + sex, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
