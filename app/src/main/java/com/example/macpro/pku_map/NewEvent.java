package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewEvent extends Activity implements View.OnClickListener{

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Button publishbtn = null;
    private Button neretbtn = null;
    private Context mContext = null;
    private CustomExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newevent);
        bindViews();

        mContext = NewEvent.this;
        gData = new ArrayList<Group>();
        iData = new ArrayList<ArrayList<Item>>();
        gData.add(new Group("选择事件地点"));

        lData = new ArrayList<Item>();

        lData.add(new Item("一教"));
        lData.add(new Item("二教"));
        lData.add(new Item("三教"));
        lData.add(new Item("四教"));
        iData.add(lData);

        myAdapter = new MyBaseExpandableListAdapter(gData, iData, mContext);
        exlist_lol.setAdapter(myAdapter);

        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext, "你点击了：" + iData.get(groupPosition).get(childPosition).getiName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void bindViews() {
        exlist_lol = (CustomExpandableListView) findViewById(R.id.exlist_lol);
        publishbtn = (Button) findViewById(R.id.publishbtn);
        neretbtn = (Button) findViewById(R.id.neretbtn);
        publishbtn.setOnClickListener(this);
        neretbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publishbtn:
                break;
            case R.id.neretbtn:
                finish();
                break;
        }
    }
}
