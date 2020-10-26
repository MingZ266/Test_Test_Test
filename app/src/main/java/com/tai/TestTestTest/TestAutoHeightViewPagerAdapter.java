package com.tai.TestTestTest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestAutoHeightViewPagerAdapter extends PagerAdapter {
    int[] heights;
    private List<View> viewList = new ArrayList<>();
    String TAG = "ViewPagerAdapterTAG";

    TestAutoHeightViewPagerAdapter(List<View> viewList) {
        this.viewList = viewList;
        setHeights();
    }

    TestAutoHeightViewPagerAdapter(Context context, List<String> textOfPager) {
        for (int i = 0; i < textOfPager.size() - 1; i++) {
            View viewPagerLayout = View.inflate(context, R.layout.view_pager_layout, null);
            TextView pagerText = viewPagerLayout.findViewById(R.id.pagerText);
            pagerText.setText(textOfPager.get(i));
            viewList.add(viewPagerLayout);
        }
        viewList.add(View.inflate(context, R.layout.main_pager_one, null));
        setHeights();
    }

    View getIndexView(int index) {
        return viewList.get(index);
    }

    List<View> getViewList() {
        return new ArrayList<>(viewList);
    }

    private void setHeights() {
        heights = new int[viewList.size()];
        MethodList.AboutView av = new MethodList.AboutView();
        for (int i = 0; i < heights.length; i++) {
            av.setView(viewList.get(i));
            heights[i] = av.getViewH();
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
