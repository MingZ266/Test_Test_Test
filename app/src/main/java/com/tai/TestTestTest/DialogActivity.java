package com.tai.TestTestTest;

//import android.app.AlertDialog;//均可

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogActivity extends AppCompatActivity {
    private Context context = DialogActivity.this;
    private Button onlyTitle, muchSelect, diyDialogLayout, diyListDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        initView();
        myListener();
    }

    private void initView() {
        onlyTitle       = findViewById(R.id.只有标题);
        muchSelect      = findViewById(R.id.多按钮及多选列表);
        diyDialogLayout = findViewById(R.id.diyDialogLayout);
        diyListDialog   = findViewById(R.id.diyListDialog);
    }

    @SuppressLint("SetTextI18n")
    private void myListener() {
        onlyTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("这是标题")         //标题
                        .setMessage("这是内容")       //内容
                        .setIcon(R.mipmap.ic_launcher)//图标
                        .create();
                alertDialog.show();
            }
        });

        muchSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] fruit = new String[]{"苹果", "橘子", "香蕉", "葡萄", "草莓", "西瓜"};
                int len = fruit.length;
                boolean[] booleans = new boolean[len];
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("选择你喜欢的水果")
                        .setMultiChoiceItems(fruit, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        diyDialogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = View.inflate(context, R.layout.dialog_layout_2, null);
                TextView title = view.findViewById(R.id.title);
                title.setText("标题");
                title.getPaint().setFakeBoldText(true);
                final TextView text = view.findViewById(R.id.text);
                text.setText("这是一段文本");
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                view.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String editText = String.valueOf(((EditText) view.findViewById(R.id.edit)).getText());
                        if (editText.equals(""))
                            Toast.makeText(context, "请输入", Toast.LENGTH_SHORT).show();
                        else
                            text.setText(editText);
                    }
                });
            }
        });

        diyListDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Map<String, String>> mapList = new ArrayList<>();
                Map<String, String> map;

                map = new HashMap<>();
                map.put("name", "香蕉");
                map.put("kind", "水果");
                map.put("judge", "可");
                mapList.add(map);

                map = new HashMap<>();
                map.put("name", "沙发");
                map.put("kind", "家具");
                map.put("judge", "不可");
                mapList.add(map);

                map = new HashMap<>();
                map.put("name", "耗牛");
                map.put("kind", "动物");
                map.put("judge", "可");
                mapList.add(map);

                final View view = View.inflate(context, R.layout.dialog_layout_3, null);
                ((TextView) view.findViewById(R.id.title)).setText("可食用表");
                ListView list = view.findViewById(R.id.list);
                list.setAdapter(new DialogListAdapter(mapList));
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .setPositiveButton("确定", null)
                        .create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context,
                                mapList.get(position).get("name") + "是" +
                                      mapList.get(position).get("kind") + "，" +
                                      mapList.get(position).get("judge") + "食用。",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private class DialogListAdapter extends BaseAdapter {
        private List<Map<String, String>> mapList;

        DialogListAdapter(List<Map<String, String>> mapList) {
            this.mapList = mapList;
        }

        @Override
        public int getCount() {
            return mapList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.dialog_list_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.name  = convertView.findViewById(R.id.name);
                viewHolder.kind  = convertView.findViewById(R.id.kind);
                viewHolder.judge = convertView.findViewById(R.id.judge);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.name.setText(mapList.get(position).get("name"));
            viewHolder.kind.setText(mapList.get(position).get("kind"));
            viewHolder.judge.setText(mapList.get(position).get("judge"));
            return convertView;
        }

        private class ViewHolder {
            TextView name, kind, judge;
        }
    }
}
