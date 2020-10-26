package com.tai.TestTestTest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileActivity extends AppCompatActivity {
    private Button createAAA, deleteAAA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        initView();
        myListener();

        Button btn = findViewById(R.id.btn);
        final View addLayout = View.inflate(FileActivity.this, R.layout.add_layout, null);
        final LinearLayout changeView = findViewById(R.id.changeView);
        final TextView greenText = findViewById(R.id.greenText);
        final TextView fileText  = findViewById(R.id.fileText),
                aText = findViewById(R.id.aText),
                bText = findViewById(R.id.bText);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> switchValue = new HashMap<>();
                switchValue.put("a", "false");
                switchValue.put("b", "true");

                Gson gson = new Gson();
                String json = gson.toJson(switchValue);
                //changeView.removeView(greenText);
                //changeView.addView(addLayout);
                //startActivity(new Intent(FileActivity.this, MainActivity.class));
                //MainActivity.mainActivity.finish();
                //FileActivity.this.finish();

                String path = Objects.requireNonNull(FileActivity.this.getFilesDir()).getPath();
                String fileName = "testFile";
                File fileDelete = new File(path, "testFile.txt");
                if (fileDelete.exists()) {
                    Toast.makeText(FileActivity.this, "此文件存在\n" + fileDelete.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    if (fileDelete.delete())
                        Toast.makeText(FileActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                }
                    File file;
                    StringBuilder sb = new StringBuilder();
                try {
                    file = new File(path, fileName);
                    FileOutputStream fos = openFileOutput(file.getName(), Context.MODE_PRIVATE);
                    fos.write(json.getBytes());
                    fos.close();
                    try {
                        FileInputStream  fis = openFileInput(file.getName());
                        byte[] out = new byte[1024];
                        int len = 0;
                        while ((len = fis.read(out)) > 0) {
                            sb.append(new String(out, 0, len));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fileText.setText("绝对路径：\n" + file.getAbsolutePath() + "\n" +
                            "文件长度：" + file.length() + "字节\n" + sb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String str = sb.toString();
                aText.setText(str);
                Type type = new TypeToken<Map<String, String>>() {}.getType();
                Map<String, String> map = gson.fromJson(str, type);

                boolean a, b;
                a = Boolean.valueOf(map.get("a"));
                b = Boolean.valueOf(map.get("b"));
                if (!a) aText.setText("a：" + map.get("a"));
                if (b) bText.setText("b：" + map.get("b"));
            }
        });
    }

    private void initView() {
        createAAA = findViewById(R.id.createAAA);
        deleteAAA = findViewById(R.id.deleteAAA);
    }

    private void myListener() {
        final File AAA = new File(getFilesDir().getPath(), "AAA");

        createAAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AAA.exists())
                    Toast.makeText(FileActivity.this, "文件“AAA”已存在", Toast.LENGTH_SHORT).show();
                else {
                    try {
                        if (AAA.createNewFile())
                            Toast.makeText(FileActivity.this, "文件“AAA”已创建", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(FileActivity.this, "文件“AAA”创建失败", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        deleteAAA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AAA.exists())
                    if (AAA.delete())
                        Toast.makeText(FileActivity.this, "文件“AAA”已删除", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(FileActivity.this, "文件“AAA”删除失败", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(FileActivity.this, "文件“AAA”不存在", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
