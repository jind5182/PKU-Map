package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

public class Start extends Activity {

    ImageButton imgbtn = null;
    boolean isShow = false;
    private ViewPager mViewPager;
    private LinearLayout indicatorLayout;
    private List<View> views;
    private ImageView[] mImageViews;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        isShow = PreferenceUtil.getBoolean(this, PreferenceUtil.SHOW_GUIDE);
        if (isShow) {
            initLog();
        } else {
            initView();
        }
    }

    private void initLog() {
        startActivity(new Intent(this, Login.class));
        finish();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        indicatorLayout = (LinearLayout) findViewById(R.id.dots);
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.welcome1, null));
        views.add(inflater.inflate(R.layout.welcome2, null));
        views.add(inflater.inflate(R.layout.welcome3, null));
        myPagerAdapter = new MyPagerAdapter(this, views);
        mImageViews = new ImageView[views.size()];
        drawCircl();
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.addOnPageChangeListener(new GuidePageChangeListener());
    }

    private void drawCircl() {
        int num = views.size();
        for (int i = 0; i < num; i++) {
            //实例化每一个mImageViews[i]
            mImageViews[i] = new ImageView(this);
            if (i == 0) {
                // 默认选中第一张照片，所以将第一个小圆圈变为icon_carousel_02
                mImageViews[i].setImageResource(R.mipmap.icon_carousel_01);
            } else {
                mImageViews[i].setImageResource(R.mipmap.icon_carousel_02);
            }
            // 给每个小圆圈都设置间隔
            mImageViews[i].setPadding(7, 7, 7, 7);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            // 让每一个小圆圈都在LinearLayout的CENTER_VERTICAL（中间垂直）
            indicatorLayout.addView(mImageViews[i], params);
        }

    }

    private class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int arg0) {
            for (int i = 0; i < mImageViews.length; i++) {
                if (arg0 != i) {
                    mImageViews[i]
                            .setImageResource(R.mipmap.icon_carousel_02);
                } else {
                    mImageViews[arg0]
                            .setImageResource(R.mipmap.icon_carousel_01);
                }
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViews;
        private Activity mContext;

        public MyPagerAdapter(Activity context, List<View> views) {
            this.mViews = views;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mViews.get(arg1));
        }

        /**
         * 实例化页卡，如果变为最后一页，则获取它的button并且添加点击事件
         */
        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            ((ViewPager) arg0).addView(mViews.get(arg1), 0);
            if (arg1 == mViews.size() - 1) {
                imgbtn = (ImageButton) findViewById(R.id.startbtn);
                imgbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initLog();
                    }
                });
            }
            return mViews.get(arg1);
        }
    }
}
