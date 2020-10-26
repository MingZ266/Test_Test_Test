package com.tai.TestTestTest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import DiyView.AutoHeightPagerAdapter;
import DiyView.AutoHeightViewPager;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivityTAG";
    Gson gson = new Gson();
    private Context context = MainActivity.this;
    private Button testBtnOne, testBtnTwo, reset;
    private TextView text_4;
    private AutoHeightViewPager mainBtnPager;
    private AutoHeightPagerAdapter adapter;
    Sever sever = new Sever();
    StringBuffer s;
    List<Map<String, Object>> dataList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingTitle();
        initView();
        myProcess();
        myListener();
        btnPager0();
        btnPager1();
    }

    @SuppressLint("SetTextI18n")
    private void btnPager0() {
        View pager0 = adapter.getIndexView(0);

        Button 城市基础信息类 = pager0.findViewById(R.id.城市基础信息类);
        Button jsonStrBtn1 = pager0.findViewById(R.id.jsonString1);
        Button jsonStrBtn2 = pager0.findViewById(R.id.jsonString2);
        Button jsonStrBtn3 = pager0.findViewById(R.id.jsonString3);
        final TextView text_1 = pager0.findViewById(R.id.Text_1);
        final TextView text_2 = pager0.findViewById(R.id.Text_2);
        final TextView text_3 = pager0.findViewById(R.id.Text_3);
        final String jsonString1 = "{\"北京\":{\"have\":\"true\"},\"上海\":{\"have\":\"true\"},\"潢川\":{\"have\":\"false\"},\"深圳\":{\"have\":\"true\"}}";
        final String jsonString2 = "[{\"CityID\":\"北京\",\"havePermission\":\"true\"},{\"CityID\":\"上海\",\"havePermission\":\"true\"},{\"CityID\":\"潢川\",\"havePermission\":\"false\"},{\"CityID\":\"深圳\",\"havePermission\":\"true\"}]";
        final String[] jsonStrings = new String[]{
                "{\"CityName\":\"北京\",\"Country\":\"中国\",\"AdminArea\":\"北京\",\"ParentCity\":\"北京\",\"Timezone\":\"+8.00\"}",
                "{\"CityName\":\"卓资\",\"Country\":\"中国\",\"AdminArea\":\"内蒙古\",\"ParentCity\":\"乌兰察布\",\"Timezone\":\"+8.00\"}",
                "{\"CityName\":\"潢川\",\"Country\":\"中国\",\"AdminArea\":\"河南\",\"ParentCity\":\"信阳\",\"Timezone\":\"+8.00\"}"
        };

        城市基础信息类.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityBasicInfo 北京 = new CityBasicInfo("潢川", "中国", "河南", "信阳", "+8.00");
                CityBasicInfo 卓资 = new CityBasicInfo("潢川", "中国", "河南", "信阳", "+8.00");
                CityBasicInfo 潢川 = new CityBasicInfo("潢川", "中国", "河南", "信阳", "+8.00");
//                text_1.setText(潢川.getDisplayStr());
//                String HuangChuang = gson.toJson(潢川);
//                text_2.setText(HuangChuang);
//                CityBasicInfo to潢川 = gson.fromJson(HuangChuang, CityBasicInfo.class);
//                text_3.setText(to潢川.getDisplayStr());
                CityBasicInfo[] cities = new CityBasicInfo[]{北京, 卓资, 潢川};
                text_1.setText(Arrays.toString(cities));
                String citiesStr = gson.toJson(cities);
                text_2.setText(citiesStr);
                CityBasicInfo[] getCities = gson.fromJson(citiesStr, CityBasicInfo[].class);
                String TEXT = "";
                for (CityBasicInfo aCity : getCities) {
                    TEXT = TEXT.concat(aCity.getDisplayStr());
                }
                text_3.setText(TEXT);
            }
        });

        jsonStrBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                text_1.setText(jsonString1);
//                try {
//                    JSONObject jsonObject = new JSONObject(jsonString1);
//                    int i = jsonObject.length();
//                    text_2.setText(String.valueOf(i));//直接传入数字将作为string资源id去匹配导致出错
//                    text_3.setText(jsonObject.getString("潢川"));
////                    JSONObject HuangChuang = jsonObject.getJSONObject("潢川");
////                    text_3.setText(HuangChuang.getString("have"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                String s = "[\"A\",\"B,\"C\"]";
                text_1.setText(s);
                try {
//                    String[] ss = gson.fromJson(s, String[].class);
//                    text_2.setText("ss: " + Arrays.toString(ss));
//                    text_3.setText(ss[0]);
                    double i = Double.parseDouble("");
                    text_1.setText(String.valueOf(i));
                } catch (NumberFormatException e) {
                    text_2.setText("");
                    text_3.setText("异常");
                }
            }
        });

        jsonStrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_1.setText(jsonString2);
                try {
                    JSONArray jsonArray = new JSONArray(jsonString2);
                    int i = jsonArray.length();
                    text_2.setText(String.valueOf(i));
                    JSONObject HuangChuang = jsonArray.getJSONObject(2);
                    text_3.setText(HuangChuang.length() + " " + HuangChuang.getString("CityID") + " " + HuangChuang.getString("havePermission"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        jsonStrBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = gson.toJson(jsonStrings);
                text_1.setText(str);
                String[] strings = gson.fromJson(str, String[].class);
                text_2.setText(Arrays.toString(strings));
                String TEXT = "";
                for (String string : strings) {
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        TEXT = TEXT.concat(jsonObject.getString("CityName") + "  在  " +
                                jsonObject.getString("Country") + " - " +
                                jsonObject.getString("AdminArea") + " - " +
                                jsonObject.getString("ParentCity") +
                                "  所在时区：GMT" + jsonObject.getString("Timezone") + "\n");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                text_3.setText(TEXT);
            }
        });

        skip((Button) pager0.findViewById(R.id.toFile), FileActivity.class);
        skip((Button) pager0.findViewById(R.id.toMap), MapObjectActivity.class);
        skip((Button) pager0.findViewById(R.id.toTitle), ImmersionTitleActivity.class);
        skip((Button) pager0.findViewById(R.id.toTimerTest), TimerTestActivity.class);
        skip((Button) pager0.findViewById(R.id.toDownFresh), DownFreshActivity.class);
        skip((Button) pager0.findViewById(R.id.toViewPager), ViewPagerActivity.class);
        skip((Button) pager0.findViewById(R.id.toFinish), FinishMiddleActivity.class);
        skip((Button) pager0.findViewById(R.id.toListInScroll), ListInScrollActivity.class);
        skip((Button) pager0.findViewById(R.id.toDIYAnim), DIYAnimActivity.class);
        skip((Button) pager0.findViewById(R.id.toPops_up), PopsUpWindowsActivity.class);
        skip((Button) pager0.findViewById(R.id.toLoading), LoadingActivity.class);
        skip((Button) pager0.findViewById(R.id.toTime), TimeActivity.class);
        skip((Button) pager0.findViewById(R.id.toSpinner), SpinnerActivity.class);
    }

    private void btnPager1() {
        View pager1 = adapter.getIndexView(1);
        skip((Button) pager1.findViewById(R.id.toWebView), WebViewActivity.class);
        skip((Button) pager1.findViewById(R.id.toOneLineText), OneLineTextActivity.class);
        skip((Button) pager1.findViewById(R.id.toLayoutList), LayoutListActivity.class);
        skip((Button) pager1.findViewById(R.id.toDIYView), DIYViewActivity.class);
        skip((Button) pager1.findViewById(R.id.toNdkTest), NdkTestActivity.class);
        skip((Button) pager1.findViewById(R.id.toTest), TestActivity.class);
    }

    private void initView() {
        List<View> viewList = new ArrayList<>();
        viewList.add(View.inflate(context, R.layout.main_btn_pager_0, null));
        viewList.add(View.inflate(context, R.layout.main_btn_pager_1, null));
        adapter = new AutoHeightPagerAdapter(viewList);

        testBtnOne = findViewById(R.id.testBtnOne);
        testBtnTwo = findViewById(R.id.testBtnTwo);
        reset      = findViewById(R.id.reset);
        text_4 = findViewById(R.id.Text_4);
        mainBtnPager = findViewById(R.id.mainBtnPager);
    }

    private void myProcess() {
        mainBtnPager.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void myListener() {
        testBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBtnOne.setEnabled(false);
                testBtnTwo.setEnabled(false);
                /*
                text_4.setText("测试按钮 One");
                final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        text_4.setText("Handle消息");
                        testBtnOne.setEnabled(true);
                        testBtnTwo.setEnabled(true);
                        return false;
                    }
                });
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // new Message()    ==> 创建了一个新的Message对象
                        // Message.obtain() ==> 从Message池中拿出一个Message，减少内存的开销
                        // handle.obtainMessage()直接调用obtain()，故同上

                        //handler.sendMessage(new Message());
                        //handler.sendMessage(Message.obtain());
                        handler.sendMessage(handler.obtainMessage());
                    }
                }, 3000);
                */
                /*text_4.setText(String.format("%.1f", 12.36));*/
                /*
                sever.check("software", "000000", "XQ");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        s = new StringBuffer("内容：");
                        dataList = new ArrayList<>(sever.getList());
                        for (Map<String, Object> map : dataList) {
                            s.append(map.get("name"));
                            Log.d(TAG, String.valueOf(map.get("name")));
                        }
                        text_4.post(new Runnable() {
                            @Override
                            public void run() {
                                text_4.setText(s);
                            }
                        });
                    }
                }, 1500);
                */

                /*final int[] time = new int[] {0};
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Log.d(TAG, time[0] + "s：执行了");
                        if (time[0] == 6) {
                            // 进行比较验证
                            this.cancel();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    testBtnOne.setEnabled(true);
                                    testBtnTwo.setEnabled(true);
                                }
                            });
                        }
                        time[0]++;
                    }
                }, 0, 1000);*/

                final String[] strings = new String[] {"邰启超", "许杰", "root", "张宇", "爱丽丝", "ber"};
                text_4.setText(Arrays.toString(strings));
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Arrays.sort(strings);
                                text_4.setText(Arrays.toString(strings));
                                testBtnOne.setEnabled(true);
                                testBtnTwo.setEnabled(true);
                            }
                        });
                    }
                }, 3000);
            }
        });

        testBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testBtnOne.setEnabled(false);
                testBtnTwo.setEnabled(false);

                Toast.makeText(context, "10s 倒计时开始", Toast.LENGTH_SHORT).show();
                new CountDownTimer(10 * 1000 + 200/*抵消官方代码运行减去的时间*/, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (text_4 == null)
                            this.cancel();
                        else
                            text_4.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        text_4.setText("倒计时结束");
                        testBtnOne.setEnabled(true);
                        testBtnTwo.setEnabled(true);
                    }
                }.start();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });

        mainBtnPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mainBtnPager.reDraw();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void skip(Button skipBtn, final Class<? extends AppCompatActivity> skipActivityClass) {
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, skipActivityClass));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void settingTitle() {
        Calendar TimeShow = Calendar.getInstance();
        TextResource textResource = new TextResource();
        TextView writeTitle = findViewById(R.id.writeTitle);
        TextView writeTime = findViewById(R.id.writeTime);

        writeTitle.setText(textResource.RandomTitle());
        writeTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FZMWFont.ttf"));
        writeTime.setText(
                TimeShow.get(Calendar.YEAR) + "年"
                        + (TimeShow.get(Calendar.MONTH) + 1) + "月"
                        + TimeShow.get(Calendar.DAY_OF_MONTH) + "日 星期"
                        + textResource.getNumChinese(TimeShow.get(Calendar.DAY_OF_WEEK) - 1)
        );
    }

    private static class TextResource {
        String getNumChinese(int num) {
            switch (num) {
                case 1:
                    return "一";
                case 2:
                    return "二";
                case 3:
                    return "三";
                case 4:
                    return "四";
                case 5:
                    return "五";
                case 6:
                    return "六";
                case 0:
                    return "日";
                default:
                    return "（未知错误！）";
            }
        }

        String RandomTitle() {
            String[] titleArray = new String[]{
                    "相信是成功的起点，坚持是成功的终点。所以说，你今天敲代码了没？",
                    "我们不是没有好的机会，我们是没有好的代码。。。",
                    "努力一定会有结果，但不一定有好结果。方向不对，努力白费，记得多多请教哦！",
                    "我们不了解的事情不等于不存在，多看博客，学习更多有趣的东西，让你的代码也有趣起来吧！"
            };
            try {
                return titleArray[(int) (Math.random() * titleArray.length)];
            } catch (ArrayIndexOutOfBoundsException e) {
                return titleArray[0];
            }
        }
    }
}
