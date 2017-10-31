package com.example.macpro.pku_map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private TextView txt_chat;
    private TextView txt_contract;
    private TextView txt_me;
    private ViewPager vpager;
    private Drawable drawable = null;

    private MyFragmentPagerAdapter mAdapter;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        txt_chat.performClick();
    }

    private void bindViews(){
        txt_chat=(TextView)findViewById(R.id.txt_map);
        txt_contract=(TextView)findViewById(R.id.txt_event);
        txt_me=(TextView)findViewById(R.id.txt_me);
        drawable = getResources().getDrawable(R.drawable.tab_menu_map, null);
        drawable.setBounds(0, 0, 100, 100);
        txt_chat.setCompoundDrawables(null, drawable, null, null);
        drawable = getResources().getDrawable(R.drawable.tab_menu_event, null);
        drawable.setBounds(0, 0, 100, 100);
        txt_contract.setCompoundDrawables(null, drawable, null, null);
        drawable = getResources().getDrawable(R.drawable.tab_menu_me, null);
        drawable.setBounds(0, 0, 100, 100);
        txt_me.setCompoundDrawables(null, drawable, null, null);

        txt_chat.setOnClickListener(this);
        txt_contract.setOnClickListener(this);
        txt_me.setOnClickListener(this);

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    private void setSelected(){
        txt_chat.setSelected(false);
        txt_contract.setSelected(false);
        txt_me.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_map:
                setSelected();
                txt_chat.setSelected(true);
                vpager.setCurrentItem((PAGE_ONE));
                break;
            case R.id.txt_event:
                setSelected();
                txt_contract.setSelected(true);
                vpager.setCurrentItem((PAGE_TWO));
                break;
            case R.id.txt_me:
                setSelected();
                txt_me.setSelected(true);
                vpager.setCurrentItem((PAGE_THREE));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    setSelected();
                    txt_chat.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    txt_contract.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    txt_me.setSelected(true);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        ImageView retpic = (ImageView) findViewById(R.id.retpic);
        Button retbtn = (Button) findViewById(R.id.retbtn);
        retpic.setVisibility(View.INVISIBLE);
        retbtn.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }
}
