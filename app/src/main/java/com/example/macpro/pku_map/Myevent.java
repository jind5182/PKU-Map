package com.example.macpro.pku_map;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Myevent extends AppCompatActivity {

    private FrameLayout myeventfl = null;
    private ArrayList<Data> datas = null;
    private FragmentManager fManager = null;
    private Button myeventret, editbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myevent);
        bindViews();

        datas = new ArrayList<Data>();
        for (int i = 1; i <= 3; i++) {
            Data data = new Data("  事件" + i, i + "~事件内容及评论～～～～～～～～");
            datas.add(data);
        }
        fManager = getSupportFragmentManager();
        ListFragment nlFragment = new ListFragment(fManager, datas, 1);
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.myeventfl, nlFragment);
        ft.commit();
    }

    private void bindViews() {
        myeventfl = (FrameLayout) findViewById(R.id.myeventfl);
        myeventret = (Button) findViewById(R.id.myeventret);
        myeventret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editbtn = (Button) findViewById(R.id.editbtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Myevent.this, "你点击了编辑按钮~", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
