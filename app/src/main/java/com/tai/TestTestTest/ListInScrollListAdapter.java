package com.tai.TestTestTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListInScrollListAdapter extends BaseAdapter {
    private List<Map<String, String>> mapList = new ArrayList<>();
    private LayoutInflater inflater;

    ListInScrollListAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        Map<String, String> map = new HashMap<>();
        map.put("name", "桌子");
        map.put("type", "家具");
        mapList.add(map);

        map = new HashMap<>();
        map.put("name", "电脑");
        map.put("type", "电器");
        mapList.add(map);

        map = new HashMap<>();
        map.put("name", "苹果");
        map.put("type", "水果");
        mapList.add(map);

        map = new HashMap<>();
        map.put("name", "布偶熊");
        map.put("type", "玩偶");
        mapList.add(map);

        map = new HashMap<>();
        map.put("name", "小狗");
        map.put("type", "动物");
        mapList.add(map);

        map = new HashMap<>();
        map.put("name", "霍森");
        map.put("type", "知名科学家");
        mapList.add(map);
    }

    private static class ViewHolder {
        TextView name;
        TextView type;
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_list_in_scroll, null);
            viewHolder = new ViewHolder();

            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.type = convertView.findViewById(R.id.type);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(mapList.get(position).get("name"));
        viewHolder.type.setText(mapList.get(position).get("type"));

        return convertView;
    }
}
