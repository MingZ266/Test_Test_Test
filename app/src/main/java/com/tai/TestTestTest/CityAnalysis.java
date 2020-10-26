package com.tai.TestTestTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CityAnalysis {
    //basic{cid,         location,      parent_city,              admin_area,                 cnty,                     tz}
    // 地区／城市ID  地区／城市名称  该地区／城市的上级城市  该地区／城市所属行政区域  该地区／城市所属国家名称  该地区／城市所在时区
    //eg："GMT " + tz + "  " + cnty + " - " + admin_area + " - " + parent_city
    //     GMT +8.00  中国 - 浙江 - 宁波
    int len = 0;
    private List<Map<String, String>> CitySX = new ArrayList<>();

    List<Map<String, String>> Analysis(String jsonStringSearch) {
        Map<String, String> map = new HashMap<>();
        try {
            JSONObject json       = new JSONObject(jsonStringSearch);
            JSONArray  HeWeather6 = json.getJSONArray("HeWeather6");
            JSONObject SXJ        = HeWeather6.getJSONObject(0);
            JSONArray  SX         = SXJ.getJSONArray("basic");
            len                   = SX.length();

            JSONObject city;

            for (int i = 0; i < SX.length(); i++) {
                city = SX.getJSONObject(i);
                if (i > 0)
                    map = new HashMap<>();
                map.put("cid",         city.getString("cid"));
                map.put("location",    city.getString("location"));
                map.put("parent_city", city.getString("parent_city"));
                map.put("admin_area",  city.getString("admin_area"));
                map.put("cnty",        city.getString("cnty"));
                map.put("tz",          city.getString("tz"));
                CitySX.add(map);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return CitySX;
    }
}

class WeatherAnalysis {
    private Map<String, String> now;
    private Map<String, String> basic;
    private Map<String, String> update;
    //now{cond_code, cond_text, fl,      tmp,  wind_dir, wind_sc,   hum，       vis}
    //    图片代号   天气状况  体感温度  温度    风向     风力    相对湿度  能见度（公里）
    //
    //basic{location,         parent_city,              admin_area,                 cnty,                     tz}
    //  地区／城市名称  该地区／城市的上级城市  该地区／城市所属行政区域  该地区／城市所属国家名称  该地区／城市所在时区
    //
    //update{               loc,                                     utc}
    //  当地时间，24小时制，格式yyyy-MM-dd HH:mm  UTC时间，24小时制，格式yyyy-MM-dd HH:mm

    void Analysis(String jsonStringWeather) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        try {
            JSONObject json = new JSONObject(jsonStringWeather);
            String jsonNow    = json.getString("now");
            String jsonBasic  = json.getString("basic");
            String jsonUpdate = json.getString("update");

            now    = gson.fromJson(jsonNow, type);
            basic  = gson.fromJson(jsonBasic, type);
            update = gson.fromJson(jsonUpdate, type);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Map<String, String> getNow() {
        return now;
    }

    Map<String, String> getBasic() {
        return basic;
    }

    Map<String, String> getUpdate() {
        return update;
    }
}
