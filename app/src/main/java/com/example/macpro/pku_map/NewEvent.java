package com.example.macpro.pku_map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewEvent extends Activity implements View.OnClickListener{

    private ArrayList<Group> gData = null;
    private ArrayList<ArrayList<Item>> iData = null;
    private ArrayList<Item> lData = null;
    private Button publishbtn = null;
    private Button neretbtn = null;
    private Context mContext = null;
    private CustomExpandableListView exlist_lol;
    private MyBaseExpandableListAdapter myAdapter = null;
    private int month, day, year, hour, minute;
    private static final int msgKey1 = 1;
    private TimeThread update_thread;
    private boolean time_flag;
    private ImageView startDate, startTime, endDate, endTime;
    private TextView sdate_view, stime_view, edate_view, etime_view;
    private String date_time;
    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    if(time_flag == true) {
                        getTime();
                        setTime(1);
                        setTime(2);
                    }
                    break;

                default:
                    break;
            }
        }
    };
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
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
                DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int choose_year, int choose_month, int choose_day) {
                        year = choose_year;
                        month = choose_month + 1;
                        day = choose_day;
                        setDate(1);
                    }
                };
                DatePickerDialog date_dialog = new DatePickerDialog(mContext, date_listener, year, month - 1, day);
                date_dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDate();
                DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int choose_year, int choose_month, int choose_day) {
                        year = choose_year;
                        month = choose_month + 1;
                        day = choose_day;
                        setDate(2);
                    }
                };
                DatePickerDialog date_dialog = new DatePickerDialog(mContext, date_listener, year, month - 1, day);
                date_dialog.show();
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime();
                TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker arg0, int choose_hour, int choose_minute) {
                        hour = choose_hour;
                        minute = choose_minute;
                        time_flag = false;
                        setTime(1);
                    }
                };
                TimePickerDialog time_dialog = new TimePickerDialog(mContext, time_listener, hour, minute, true);
                time_dialog.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime();
                TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker arg0, int choose_hour, int choose_minute) {
                        hour = choose_hour;
                        minute = choose_minute;
                        time_flag = false;
                        setTime(2);
                    }
                };
                TimePickerDialog time_dialog = new TimePickerDialog(mContext, time_listener, hour, minute, true);
                time_dialog.show();
            }
        });
    }

    private void bindViews() {
        startDate = (ImageView)findViewById(R.id.startDate);
        startTime = (ImageView) findViewById(R.id.startTime);
        endDate = (ImageView) findViewById(R.id.endDate);
        endTime = (ImageView)findViewById(R.id.endTime);
        sdate_view = (TextView)findViewById(R.id.sdate_view);
        stime_view = (TextView)findViewById(R.id.stime_view);
        edate_view = (TextView)findViewById(R.id.edate_view);
        etime_view = (TextView)findViewById(R.id.etime_view);
        exlist_lol = (CustomExpandableListView) findViewById(R.id.exlist_lol);
        publishbtn = (Button) findViewById(R.id.publishbtn);
        neretbtn = (Button) findViewById(R.id.neretbtn);
        publishbtn.setOnClickListener(this);
        neretbtn.setOnClickListener(this);
    }
    private void getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cur_date = new Date(System.currentTimeMillis());
        date_time = formatter.format(cur_date);
        year = Integer.valueOf(date_time.substring(0, 4)).intValue();
        month = Integer.valueOf(date_time.substring(5, 7)).intValue();
        day = Integer.valueOf(date_time.substring(8, 10)).intValue();
    }

    private void getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date cur_date = new Date(System.currentTimeMillis());
        date_time = formatter.format(cur_date);
        hour = Integer.valueOf(date_time.substring(11, 13)).intValue();
        minute = Integer.valueOf(date_time.substring(14, 16)).intValue();
    }

    private void setDate(int number) {
        switch (number){
            case 1:
                sdate_view.setText(year + "/" + month + "/" + day);
                break;
            case 2:
                edate_view.setText(year + "/" + month + "/" + day);
                break;
        }
    }

    private void setTime(int number) {
        switch (number){
            case 1:
                stime_view.setText(hour + ":" + minute);
                break;
            case 2:
                etime_view.setText(hour + ":" + minute);
                break;
        }
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
