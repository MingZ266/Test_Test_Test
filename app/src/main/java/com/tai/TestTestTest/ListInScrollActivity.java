package com.tai.TestTestTest;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class ListInScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_in_scroll);

        final MyListView list = findViewById(R.id.list);
        ListInScrollListAdapter listInScrollListAdapter = new ListInScrollListAdapter(this);
        list.setAdapter(listInScrollListAdapter);
        //setListViewHeight(list);
        final boolean[] noClick = new boolean[list.getCount()];
        Arrays.fill(noClick, true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListInScrollActivity.this, String.valueOf(list.getCount()), Toast.LENGTH_SHORT).show();
                TextView name = view.findViewById(R.id.name);
//                Toast.makeText(ListInScrollActivity.this, "点击了 " + name.getText(), Toast.LENGTH_SHORT).show();

                View itemBg = view.findViewById(R.id.testBg);
                if (noClick[position]) {
//                    CharSequence text;
                    SpannableString text = new SpannableString(name.getText());
                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getColor(R.color.colorAccent));
                    text.setSpan(foregroundColorSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    name.setText(text);

//                    //单选
//                    list.setSelector(R.drawable.bg_item_list);

                    //多选
                    itemBg.setBackgroundResource(R.drawable.bg_item_list);

                    noClick[position] = false;
                }
                else {
                    itemBg.setBackgroundResource(0);
                    name.setText(String.valueOf(name.getText()));
                    noClick[position] = true;
                }
            }
        });
    }

    private void setListViewHeight(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int height = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = height + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
