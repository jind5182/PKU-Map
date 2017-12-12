package com.example.macpro.pku_map;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class Myevent extends AppCompatActivity {

    private FrameLayout myeventfl = null;
    private FragmentManager fManager = null;
    private Button myeventret, editbtn;
    private Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myevent);
        mContext = getApplicationContext();
        bindViews();
        PreferenceUtil.mydatas.clear();
        getEventByIDAsyncHttpClientPost(PreferenceUtil.userID);
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
    }

    private void getEventByIDAsyncHttpClientPost(int userID) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/getEventByID/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userID);
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
                            event.setType(temp.getInt("type"));
                            if (event.getType() == 2) {
                                event.setIshelped(temp.getBoolean("isHelped"));
                                event.setHelper(temp.getInt("helperID"));
                            }
                            event.setPublisherID(temp.getInt("publisherID"));
                            event.setTitle(temp.getString("title"));

                            PreferenceUtil.mydatas.add(event);
                        }
                        fManager = getSupportFragmentManager();
                        ListFragment nlFragment = new ListFragment(1);
                        FragmentTransaction ft = fManager.beginTransaction();
                        ft.replace(R.id.myeventfl, nlFragment);
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
