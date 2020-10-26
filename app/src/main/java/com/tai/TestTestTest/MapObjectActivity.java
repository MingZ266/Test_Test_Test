package com.tai.TestTestTest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapObjectActivity extends AppCompatActivity {

    String text = "";
    Gson gson = new Gson();

    private Button mapStrings;
    private TextView mapStringsText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_object);

        initView();
        myListener();

        Button showPic = findViewById(R.id.showPic);
        final ImageView Pic = findViewById(R.id.Pic);

        final Map<String, Object> map = new HashMap<>();
        map.put("pic", R.drawable.he_weather_icon);

        showPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pic.setImageDrawable(getDrawable(Integer.valueOf(String.valueOf(map.get("pic")))));
            }
        });

        final String[] Str     = {"北京", "上海", "扬州", "潢川"};
        final String   jsonStr = "{CityID:[\"北京\",\"上海\",\"扬州\",\"潢川\"]}";

        Button showJsonText = findViewById(R.id.showJsonText);
        Button showStrText  = findViewById(R.id.showStrText);
        final TextView Text = findViewById(R.id.Text);
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONArray CityID = json.getJSONArray("CityID");
            Text.setText(String.valueOf(CityID));
            for (int i = 0; i < CityID.length(); i++) {
                text = text.concat(CityID.getString(i) + "  ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showJsonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text.setText(text);
            }
        });
        showStrText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text.setText(gson.toJson(Str));
            }
        });

        TextView srcText = findViewById(R.id.srcText);
        String text_ = "{";
        for (int i = 0; i < Str.length; i++) {
            text_ = text_.concat("\"" + Str[i] + "\"");
            if (i < Str.length - 1)
                text_ = text_.concat(",");
        }
        text_ = text_.concat("}");
        srcText.setText("StringArray: " + text_ + "\nJsonText: " + jsonStr);
        Button jsonToArray = findViewById(R.id.jsonToArray);
        Button arrayToJson = findViewById(R.id.arrayToJson);
        final TextView Text2 = findViewById(R.id.Text2);
        jsonToArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text2.setText("String jsonStr = " + gson.toJson(Str));
            }
        });
        arrayToJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray CityId = null;
                try {
                    JSONObject json = new JSONObject(jsonStr);
                    CityId = json.getJSONArray("CityID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (CityId != null) {
                    String[] strings = gson.fromJson(String.valueOf(CityId), String[].class);
                    String text3 = "{";
                    for (int i = 0; i < strings.length; i++) {
                        text3 = text3.concat("\"" + strings[i] + "\"");
                        if (i < strings.length - 1)
                            text3 = text3.concat(",");
                    }
                    text3 = text3.concat("}");
                    Text2.setText("String[] CityID = " + text3);
                }
            }
        });
    }

    private void initView() {
        mapStrings = findViewById(R.id.mapStrings);
        mapStringsText = findViewById(R.id.mapStringsText);
    }

    private void myListener() {
        mapStrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String[]> stringsMap = new HashMap<>();
                stringsMap.put("A", new String[] {"A1", "A2"});
                stringsMap.put("B", new String[] {"B1", "B2"});
                String text = "";
                try {
                    text = stringsMap.get("A")[0].concat(" ")
                            .concat(stringsMap.get("A")[1]).concat(" ")
                            .concat(stringsMap.get("B")[0]).concat(" ")
                            .concat(stringsMap.get("B")[1]).concat(" ");
                } catch (NullPointerException e) {
                    text = text.concat("空指针异常");
                } catch (ArrayIndexOutOfBoundsException e) {
                    text = text.concat("数组索引越界");
                }
                mapStringsText.setText(text);
            }
        });
    }
}
