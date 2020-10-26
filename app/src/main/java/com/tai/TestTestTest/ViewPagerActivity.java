package com.tai.TestTestTest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPagerActivity extends AppCompatActivity {
    final String TAG = "TAG_ViewPagerActivity";
    private int num = 0, toPagerNum = 0;
    private Context context = ViewPagerActivity.this;

    private MyViewPager testPager;
    private MyScrollView pagerScroll;
    private TestAutoHeightViewPager autoHeight;
    private SwipeRefreshLayout refresh;
    private EditText viewPagerEdit;
    private Button viewPagerButton, addOrRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initView();
        FirstViewPager();
        SecondViewPager();
        myListener();
    }

    private void initView() {
        viewPagerEdit   = findViewById(R.id.viewPagerText);
        viewPagerButton = findViewById(R.id.viewPagerButton);
        refresh         = findViewById(R.id.refresh);
        pagerScroll     = findViewById(R.id.pagerScroll);
        testPager       = findViewById(R.id.testPager);
        autoHeight      = findViewById(R.id.autoHeight);
        addOrRemove     = findViewById(R.id.addOrRemove);
    }

    private void myListener() {
        viewPagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(viewPagerEdit.getText()).equals(""))
                    Toast.makeText(context, "输入为空！", Toast.LENGTH_SHORT).show();
                else {
                    toPagerNum = Integer.parseInt(String.valueOf(viewPagerEdit.getText()));
                    viewPagerEdit.setText("");
                    testPager.setCurrentItem(toPagerNum - 1);
                    autoHeight.setCurrentItem(toPagerNum - 1);
                }
            }
        });

        pagerScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                refresh.setEnabled(pagerScroll.getScrollY() == 0);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                testPager.setCanScroll(false);
                pagerScroll.setCanScroll(false);
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        refresh.post(new Runnable() {
                            @Override
                            public void run() {
                                testPager.setCanScroll(true);
                                pagerScroll.setCanScroll(true);
                                refresh.setRefreshing(false);
                            }
                        });
                    }
                };
                new Timer().schedule(task, 2600);
            }
        });

        final boolean[] noClick = new boolean[] {true};
        addOrRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestAutoHeightViewPagerAdapter adapter = (TestAutoHeightViewPagerAdapter) autoHeight.getAdapter();
                if (adapter == null)
                    throw new NullPointerException("类ViewPagerActivity中的myListener方法中的addOrRemove点击监听获得的Adapter为空");
                if (noClick[0]) {
                    noClick[0] = false;
                    for (View view : adapter.getViewList()) {
                        view.findViewById(R.id.colorBlock).setVisibility(View.GONE);
                    }
                    Toast.makeText(context, "移除了", Toast.LENGTH_SHORT).show();
                }
                else {
                    noClick[0] = true;
                    for (View view : adapter.getViewList()) {
                        view.findViewById(R.id.colorBlock).setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(context, "添加了", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final TestAutoHeightViewPagerAdapter adapter = (TestAutoHeightViewPagerAdapter) autoHeight.getAdapter();
        if (adapter == null)
            throw new NullPointerException("类ViewPagerActivity中myListener方法中的Adapter为空");
        final int[] index = new int[] {0};
        final int[] heights = new int[2];// { “当前的”页面高度, 下一个页面高度 }
        final boolean[] noInitialized = new boolean[] {true};
        final HaveBigChangeListener[] changeListener = new HaveBigChangeListener[1];
        MyLog.i(TAG, new DebugInfo(), "高度数组：" + Arrays.toString(adapter.heights));
        MyLog.i(TAG, new DebugInfo(), "------------------------");
        autoHeight.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (autoHeight.listenerFlag) {
                    if (noInitialized[0]) {
                        noInitialized[0] = false;
                        changeListener[0] = new HaveBigChangeListener(autoHeight.width / 2);
                        MyLog.e(TAG, new DebugInfo(), "宽度：" + autoHeight.width + " 临界值：" + changeListener[0].limNum);
                    }
                    if (positionOffset == 0)
                        index[0] = 0;
                    MyLog.i(TAG, new DebugInfo(), "当前页：" + position);
                    MyLog.i(TAG, new DebugInfo(), "滑动比例：" + positionOffset);
                    MyLog.i(TAG, new DebugInfo(), "滑动距离：" + positionOffsetPixels);

                    changeListener[0].setData(positionOffsetPixels);

                    StringBuilder log1 = new StringBuilder(), log2 = new StringBuilder();
                    if (changeListener[0].haveValue[0])
                        log1.append(changeListener[0].judgeNum[0]);
                    else
                        log1.append("没有写入值");
                    if (changeListener[0].haveValue[1])
                        log2.append(changeListener[0].judgeNum[1]);
                    else
                        log2.append("没有写入值");
                    MyLog.i(TAG, new DebugInfo(), "索引0处：" + log1 + " " + "索引1处：" + log2);

                    switch (changeListener[0].getBigChange()) {
                        case HaveBigChangeListener.FromZeroToMax:
                            MyLog.w(TAG, new DebugInfo(), "发生了从0到MAX的突变");
                            //MyLog.i(TAG, new DebugInfo(), "由" + (position + 1) + "页变化到" + position + "页");
                            break;
                        case HaveBigChangeListener.FromMaxToZero:
                            MyLog.w(TAG, new DebugInfo(), "发生了从MAX到0的突变");
                            //MyLog.i(TAG, new DebugInfo(), "由" + (position - 1) + "页变化到" + position + "页");
                            break;
                        case HaveBigChangeListener.NoBigChange:
                            MyLog.i(TAG, new DebugInfo(), "没有发生突变");
                    }

                    MyLog.i(TAG, new DebugInfo(), "------------------------" + (index[0]++));
                } else {
                    if (positionOffsetPixels == 0) {
                        autoHeight.listenerFlag = true;
                        MyLog.e(TAG, new DebugInfo(), "继续监听");
                    }
                    MyLog.w(TAG, new DebugInfo(), "跳过监听");
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void FirstViewPager() {
        final int pagerNum = 6;
        final LinearLayout pointLinear = findViewById(R.id.pointLinear);
        List<String> textOfPager = new ArrayList<>();
        for (int i = 0; i < pagerNum + 1; i++) {
            textOfPager.add("第" + (i + 1) + "个View");
            View pointView = new View(context);
//            pointView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(ViewPagerActivity.this, "被点击", Toast.LENGTH_SHORT).show();
//                }
//            });
            pointView.setBackgroundResource(R.drawable.bg_point);
            pointView.setEnabled(true);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            if (i > 0)
                layoutParams.leftMargin = 10;
            pointLinear.addView(pointView, layoutParams);
        }
        final TestAutoHeightViewPagerAdapter vpa = new TestAutoHeightViewPagerAdapter(context, textOfPager);
        testPager.setAdapter(vpa);

        pointLinear.getChildAt(0).setEnabled(false);
        testPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i(TAG, "发生滑动是第 " + (position + 1) + " 个页面");
            }

            @Override
            public void onPageSelected(int position) {
                pointLinear.getChildAt(num).setEnabled(true);
                pointLinear.getChildAt(position).setEnabled(false);
                num = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void SecondViewPager() {
        List<View> views = new ArrayList<>();
        String[] strings = new String[] {"A", "A\nBB", "A\nBB\nCCC", "A\nBB\nCCC\nDDDD", "A\nBB\nCCC\nDDDD\nEEEEE", "A\nBB\nCCC\nDDDD\nEEEEE\nFFFFFF"};
        for (String string : strings) {
            View view = View.inflate(context, R.layout.main_pager_one, null);
            TextView text = view.findViewById(R.id.text);
            text.setText(string);
            views.add(view);
        }
        TestAutoHeightViewPagerAdapter vpa = new TestAutoHeightViewPagerAdapter(views);
        autoHeight.setAdapter(vpa);
    }

    static class HaveBigChangeListener {
        private int index = 0, limNum;
        private boolean ViewRightScroll = true;
        private int[] judgeNum = new int[2];
        private boolean[] haveValue = new boolean[] {false, false};
        private final static int NoBigChange = 0, FromZeroToMax = 1, FromMaxToZero = 2;

        HaveBigChangeListener(int limNum) {
            this.limNum = limNum;
        }

        void setData(int data) {
            if (index >= 2) {
                judgeNum[0] = judgeNum[1];
                index = 1;
            }
            haveValue[index] = true;
            judgeNum[index++] = data;
        }

        int getBigChange() {
            if (haveValue[0] && haveValue[1]) {
                if (judgeNum[1] - judgeNum[0] >= limNum) {
                    ViewRightScroll = false;
                    return HaveBigChangeListener.FromZeroToMax;
                }
                else if (judgeNum[0] - judgeNum[1] >= limNum) {
                    ViewRightScroll = true;
                    return HaveBigChangeListener.FromMaxToZero;
                }
            }
            return HaveBigChangeListener.NoBigChange;
        }

        boolean isViewRightScroll() {
            return ViewRightScroll;
        }
    }
}

