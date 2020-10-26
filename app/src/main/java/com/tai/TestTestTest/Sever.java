package com.tai.TestTestTest;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 *********************************Notice********************************
 *
 *     You need to create a new Sever class and call the method add() to get
 * the data from the Our sever When the "MainActivity" First start.
 *   add():
 *           1?：method will send a request to get a json type data from our sever
 *           and analyze the json data and push to the static list;
 *           2?  You can also call this method to refresh local data;
 *          Notice: When return to the UI thread, the method did't get data yet.
 *                  So,you can't use data right after call add() finish
 *                  Suggest to judge the data after you call the add() method
 *                  each time. If get data fail the list.size()'s value is 0;
 *
 *   public void change(String num,String modify,String modifydata):
 *          Function: To send changed data to the sever and refresh local data
 *          1?：You need to income 3 parameters:num、modify and modifydata
 *           num:        StudentID of student who you want to change.
 *           modify:     Item that you want to change.
 *           modifydata: Item's data you want to change
 *
 *   getlist():
 *           This method will return list;
 * Other:
 *   public void any(String jsonData):
 *           This method will analyze jsonData and push it to the list;
 *   public void getstr():
 *           This method will return the raw json string;
 *
 *************************************************************************
 * NOtice:2020`2`28 add two new value flag and password;
 */
public class Sever {
    public static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    Map<String, Object> map = new HashMap<String, Object>();
    static String resd;
    public static int change_flag;
    public static int add_flag;
    public static int check_flag;
    public static String Username;
    public static String Passwork;
    public static String Part;
    public static String web="https://4-0-4.cn";
    public static String token="as?df?!kj!?129!??!0340aflasf";

    //Get number list from Sever  and flash data
    public void add() {
        list.clear();
        map = new HashMap<String, Object>();
        map.put("id", 0);
        map.put("name", "Data is null");
        map.put("num", "Amazing");
        map.put("work", "Amazing");
        map.put("phone", "Amazing");
        map.put("teacher", "Amazing");
        map.put("qq", "Amazing");
        map.put("sex", "Amazing");
        map.put("cont", "Amazing");
        map.put("password", "Amazing");
        map.put("flag", "Amazing");
        list.add(map);
        add_flag = 0;//flag to waiting
        Log.d("Sever.add()", "Enter the add() ");
        fun(new okhttp3.Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                resd = response.body().string();
                if(!resd.equals("wrong-password")){
                    any(resd);
                    add_flag = 1;//flag to success
                    Log.d("Sever.add()", "onResponse: " + resd);
                }
                else {
                    add_flag=2;//flag to passwork wrong
                    Log.d("Sever.add()", "onNOResp:Passworkwrong" + resd);
                }

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Sever.add()", "onNOResp: Internet problem With Respsond: " + resd);
                add_flag = 3;//flag to internet problem
            }
        });
    }
    public static void fun(okhttp3.Callback callback) {
        Log.d("Sever.add()", "Start to get msg");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("token", token)
                .add("part", Part)
                .add("number", Username)
                .add("password", Passwork)
                .build();
        Request request = new Request.Builder()
                .url(web)
                .post(requestBody)
                .build();
        //Post a request to get all the list
        Log.d("Sever.add()", "Recall back");
        client.newCall(request).enqueue(callback);
    }
    //Analyze the json data and push data to List with map type
    public void any(String jsonData) {
        //Log.d("Sever.any()", "Start to Analyze:" + jsonData);
        list.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String num = jsonObject.getString("num");
                String work = jsonObject.getString("work");
                String phone = jsonObject.getString("phone");
                String teacher = jsonObject.getString("teacher");
                String qq = jsonObject.getString("qq");
                String sex = jsonObject.getString("sex");
                String cont = jsonObject.getString("cont");
                String flag = jsonObject.getString("flag");
                map = new HashMap<String, Object>();
                map.put("id", id);
                map.put("name", name);
                map.put("num", num);
                map.put("work", work);
                map.put("phone", phone);
                map.put("teacher", teacher);
                map.put("qq", qq);
                map.put("sex", sex);
                map.put("cont", cont);
                map.put("flag", flag);
                list.add(map);
                Log.d("Sever.any()", "Present List Size: " + list.size());
            }
        } catch (JSONException e) {
            Log.d("Sever.any()", "Fail to analyze date");
            e.printStackTrace();
        }
        Log.d("Sever.any()", "Success to analyze date include list size: " + list.size());
    }


    //return list
    public int getsize() {
        return list.size();
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    //return src json data
    public String getstr() {
        return resd;
    }

    //change data and send it to sever
    public void change(String num, String modify, String modifydata) {
        Log.d("Sever.change()", "Start to post change date");
        change_flag = 0; // flag to waiting
        chang(num, modify, modifydata,"other" ,new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String temp = response.body().string();
                if (temp.equals("update-success")) {
                    add();
                    while (true) {
                        if (add_flag == 1) break;
                        else if (add_flag == 0) continue;
                        else add();
                    }
                    change_flag = 1;//flag success
                    Log.d("Sever.change()", "Success to post change date!");

                } else {
                    change_flag = 2;//flag Fail date is illegal
                    Log.d("Sever.change()", "Fail to post change date because the post date is illegal:" + temp);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                change_flag = 3;//flag to internet;
                Log.d("Sever.change()", "Fail to post change date with the internet Problem");
            }
        });
    }//change other info
    public void changecnt(String num, String modifydata,String remark) {
        Log.d("Sever.changecnt()", "Start to post change date");
        change_flag = 0; // flag to waiting
        chang(num, "count", modifydata, remark ,new okhttp3.Callback(){
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String temp = response.body().string();
                if (temp.equals("insert-success-count-update-success")) {
                    add();
                    while (true) {
                        if (add_flag == 1) break;
                        else if (add_flag == 0) continue;
                        else add();
                    }
                    change_flag = 1;//flag success
                    Log.d("Sever.changecnt()", "Success to post change date!");
                } else {
                    change_flag = 2;//flag Fail date is illegal
                    Log.d("Sever.changecnt()", "Fail to post change date because the post date is illegal:" + temp);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                change_flag = 3;//flag to internet;
                Log.d("Sever.change()", "Fail to post change date with the internet Problem");
            }
        });
    }//change cont info
    public static void chang(String num, String modify, String modifydata,String remark ,okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("token", token)
                .add("part",Part)
                .add("number", Username)
                .add("password",Passwork)
                .add("modify", modify)
                .add("modifydata", modifydata)
                .add("note",remark)
                .add("cnumber",num)
                .build();
        Request request = new Request.Builder()
                .url(web)
                .post(requestBody)
                .build();
        //Post a request to change the info
        client.newCall(request).enqueue(callback);
    }
    public void changepswd(String num, String before, final String after) {
        Log.d("Sever.changepsw()", "Start to post change date");
        change_flag = 0; // flag to waiting
        changpsw(num, before, after,"userchangepasw" ,new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String temp = response.body().string();
                if (temp.equals("update-success")) {
                    Passwork=after;
                    change_flag = 1;//flag success
                    Log.d("Sever.changepsw()", "Success to post change date!");

                } else {
                    change_flag = 2;//flag Fail date is illegal
                    Log.d("Sever.changepsw()", "Fail to post change date because the post date is illegal:" + temp);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                change_flag = 3;//flag to internet;
                Log.d("Sever.changepsw()", "Fail to post change date with the internet Problem");
            }
        });
    }//change psw info
    public static void changpsw(String num, String before, String after,String remark ,okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        Log.d("FFFF", "Post"+Username+before);
        RequestBody requestBody = new FormBody.Builder()
                .add("token", token)
                .add("part",Part)
                .add("number", Username)
                .add("password",before)
                .add("modify", "password")
                .add("modifydata", after)
                .add("note",remark)
                .add("cnumber",num)
                .build();
        Request request = new Request.Builder()
                .url(web)
                .post(requestBody)
                .build();
        //Post a request to change the info
        client.newCall(request).enqueue(callback);
    }

    public int getaddflag() {
        return add_flag;
    }
    public int getchangeflag() {
        return change_flag;
    }
    public int getCheck_flag(){
        return check_flag;
    }


    //check password and username
    public void check(String part, String username, String pwd) {
        Log.d("Sever.check()", "Start to post user date");
        check_flag = 0; // flag to waiting
        Passwork=pwd;
        Username=username;
        Part = part;
        check(part, username, pwd, new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                resd = response.body().string();
                if (!resd.equals("wrong-password") && !resd.equals("user-does-not-exist")) {
                    any(resd);
                    check_flag = 1;//flag success
                    Log.d("Sever.check()", "Success");
                } else {
                    check_flag = 2;//Passwork or username wrong
                    Log.d("Sever.check()", "Fail because username or password is eorr" + resd);
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                check_flag = 3;//flag to internet;
                Log.d("Sever.check()", "Fail to post change date with the internet Problem");
            }
        });
    }
    public static void check(String num, String user, String pwd, okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("token", "as?df?!kj!?129!??!0340aflasf")
                .add("part", num)
                .add("number", user)
                .add("password", pwd)
                .build();
        Request request = new Request.Builder()
                .url(web)
                .post(requestBody)
                .build();
        //Post a request to check the info
        client.newCall(request).enqueue(callback);
    }

}
