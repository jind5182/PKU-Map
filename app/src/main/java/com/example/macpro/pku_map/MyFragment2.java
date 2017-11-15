package com.example.macpro.pku_map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MyFragment2 extends Fragment {

    private FrameLayout fl_content;
    private ArrayList<Event> datas = null;
    private FragmentManager fManager = null;
    private Button addbtn = null;
    private Context mContext = null;
    //private static final int msgKey1 = 1;
    //private final static int MIN_MOVE = 200;   //最小距离
    //private GestureDetector gesture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2, container, false);
        mContext = getActivity();
        fManager = getFragmentManager();
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        addbtn = (Button) view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewEvent.class));
            }
        });
        /*gesture = new GestureDetector(this.getActivity(), new MyOnGestureListener());
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);//返回手势识别触发的事件
            }
        });*/
        datas = new ArrayList<Event>();
        ListFragment nlFragment = new ListFragment(fManager, 0);
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.fl_content, nlFragment);
        ft.commit();
        return view;
    }

    /*private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override//此方法必须重写且返回真，否则onFling不起效
        public boolean onDown(MotionEvent e) {
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getY() - e2.getY()  < MIN_MOVE){
                getEventByTypeAsyncHttpClientPost(0);
                Toast.makeText(getActivity(), "下拉",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }*/

    /*public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(5000);
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
                    getEventByTypeAsyncHttpClientPost(0);
                    break;

                default:
                    break;
            }
        }
    };*/

    private void getEventByTypeAsyncHttpClientPost(int type) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/getEventByType/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", type);
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
                try {
                    int status = response.getInt("getStatus");
                    if (status == 1) {
                        Toast.makeText(mContext, "status code is:"+ statusCode+ "\n更新失败!\n", Toast.LENGTH_LONG).show();
                    }
                    else if(status == 0) {
                        //Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG).show();
                        int count = response.getInt("eventNum");
                        JSONArray events = response.getJSONArray("events");
                        //Toast.makeText(mContext, events.toString(), Toast.LENGTH_LONG).show();
                        for (int i = 0; i < count; i++)
                        {
                            JSONObject temp = events.getJSONObject(i);
                            Event event = new Event();
                            event.setEventID(temp.getInt("eventID"));
                            //eventList[i].setBeginTime(temp.getString("beginTime"));
                            event.setDescription(temp.getString("description"));
                            //eventList[i].setEndTime(temp.getString("endTime"));
                            if (temp.getInt("locationID") == -1)
                                event.setLocation(temp.getDouble("locationX"), temp.getDouble("locationY"));
                            else
                                event.setLocation(temp.getInt("locationID"));
                            event.setOutdate(temp.getInt("outdate"));
                            event.type = (temp.getInt("type"));
                            event.setPublisherID(temp.getInt("publisherID"));
                            event.setTitle(temp.getString("title"));
                            datas.add(event);
                        }
                        ListFragment nlFragment = new ListFragment(fManager, 0);
                        FragmentTransaction ft = fManager.beginTransaction();
                        ft.replace(R.id.fl_content, nlFragment);
                        ft.commit();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(mContext, "connection error!Error number is:" + statusCode,  Toast.LENGTH_LONG).show();
            }
        });
        return;

    }
}
