package com.example.macpro.pku_map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class NewEvent extends Activity implements View.OnClickListener{

    private Button publishbtn = null;
    private Button neretbtn = null;
    private Context mContext = null;
    private Button exlist_lol;
    private TextView loc = null;
    private RadioGroup radgroup = null;
    private RadioButton btn1 = null;
    private LinearLayout stime, etime;
    private int month, day, year, hour, minute;
    private static final int msgKey1 = 1;
    private TimeThread update_thread;
    private boolean time_flag;
    private ImageView startDate, startTime, endDate, endTime;
    private TextView sdate_view, stime_view, edate_view, etime_view;
    private String date_time;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private EditText header, content;

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
        exlist_lol.setOnClickListener(this);
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
    public void onStart(){
        super.onStart();
        getDate();
        getTime();
        setDate(1);
        setTime(1);
        time_flag = true;
        update_thread = new TimeThread();
        update_thread.start();
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
        exlist_lol = (Button) findViewById(R.id.exlist_lol);
        publishbtn = (Button) findViewById(R.id.publishbtn);
        neretbtn = (Button) findViewById(R.id.neretbtn);
        loc = (TextView) findViewById(R.id.loc);
        radgroup = (RadioGroup) findViewById(R.id.radiogroup);
        btn1 = (RadioButton) findViewById(R.id.btn1);
        btn1.setChecked(true);
        stime = (LinearLayout) findViewById(R.id.stime);
        etime = (LinearLayout) findViewById(R.id.etime);
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if (checkedId == R.id.btn2) {
                    stime.setVisibility(View.VISIBLE);
                }
                else {
                    stime.setVisibility(View.GONE);
                }
            }
        });
        header = (EditText) findViewById(R.id.header);
        content = (EditText) findViewById(R.id.content);
        publishbtn.setOnClickListener(this);
        neretbtn.setOnClickListener(this);
        exlist_lol.setOnClickListener(this);
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
                alert = null;
                builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                alert = builder.setMessage("是否确定发布？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (header.getText().toString().equals("")) {
                                    Toast.makeText(mContext, "标题不能为空", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else if (header.getText().toString().length() > 20) {
                                    Toast.makeText(mContext, "标题不能超过20个字", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else if (content.getText().toString().equals("")) {
                                    Toast.makeText(mContext, "事件内容不能为空", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else if (content.getText().toString().length() > 100) {
                                    Toast.makeText(mContext, "事件内容不能超过100个字", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else if (edate_view.getText().toString().equals("") || etime_view.getText().toString().equals("")) {
                                    Toast.makeText(mContext, "请选择时间", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else if (loc.getText().toString().equals("")) {
                                    Toast.makeText(mContext, "请选择地点", Toast.LENGTH_SHORT).show();
                                    alert.dismiss();
                                }
                                else {
                                    Event event = new Event();
                                    event.setEventID(1);
                                    event.setTitle(header.getText().toString());
                                    event.setLocation(PreferenceUtil.getPlace(loc.getText().toString()));
                                    //event.setBeginTime(sdate_view.getText().toString(), stime_view.getText().toString());
                                    //event.setEndTime(edate_view.getText().toString(), etime_view.getText().toString());
                                    event.setType(((RadioButton)findViewById(radgroup.getCheckedRadioButtonId())).getText().toString());
                                    event.setDescription(content.getText().toString());
                                    event.setOutdate(false);
                                    eventByAsyncHttpClientPost(event);
                                }
                            }
                        }).create();             //创建AlertDialog对象
                alert.show();                    //显示对话框
                break;
            case R.id.neretbtn:
                finish();
                break;
            case R.id.exlist_lol:
                alert = null;
                builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                builder.setCancelable(true);
                alert = builder.setTitle("地点选择")
                        .setItems(PreferenceUtil.place, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loc.setText(PreferenceUtil.place[which]);
                            }
                        }).create();
                alert.show();
                Window dialogWindow = alert.getWindow();
                WindowManager m = this.getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                Point size = new Point();
                d.getSize(size);
                p.height = (int) (size.y * 0.6);
                p.width = (int) (size.x * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
                dialogWindow.setAttributes(p);
                break;
            default:
                alert.dismiss();
        }
    }
    public void eventByAsyncHttpClientPost(Event event) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/postEvent/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventID", event.eventID);
            jsonObject.put("title", event.title);
            jsonObject.put("locationID", event.locationID);
            jsonObject.put("locationX", event.locationX);
            jsonObject.put("locationY", event.locationY);
            //jsonObject.put("beginDate", event.beginDate);
            jsonObject.put("beginTime", event.beginTime);
            //jsonObject.put("endDate", event.endDate);
            jsonObject.put("endTime", event.endTime);
            jsonObject.put("type", event.type);
            jsonObject.put("publisherID", 1);
            jsonObject.put("description", event.description);
            jsonObject.put("outdate", event.outdate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //将参数加入到参数对象中
        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //进行post请求
        client.post(mContext, url, entity, "application/json", new JsonHttpResponseHandler() {
            //如果成功
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Toast.makeText(mContext, "status code is:"+ statusCode+ "connection success!"+response.toString(), Toast.LENGTH_SHORT).show();
                //Log.e("rs",response.toString());

                Toast.makeText(mContext, "123", Toast.LENGTH_SHORT).show();
                //System.out.println("response: " + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //Toast.makeText(mContext, "connection error!Error number is:" + statusCode,  Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "connection error!Error number is:" + statusCode, Toast.LENGTH_SHORT).show();
            }
        });
        return;
    }
}
