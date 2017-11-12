package com.example.macpro.pku_map;

import android.content.Context;
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

public class Event {

    public int eventID;
    public String title;
    public int locationID;
    public double locationX, locationY;
    public String beginDate, beginTime, endDate, endTime;
    public String type;
    public String description;
    public boolean outdate;
    private Context mContext;

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocation(int index) {
        this.locationID = index;
        this.locationX = PreferenceUtil.coordinateX[index];
        this.locationY = PreferenceUtil.coordinateY[index];
    }
    public void setBeginTime(String beginDate, String beginTime) {
        this.beginDate = beginDate;
        this.beginTime = beginTime;
    }
    public void setEndTime(String endDate, String endTime) {
        this.endDate = endDate;
        this.endTime = endTime;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setOutdate(boolean outdate) {
        this.outdate = outdate;
    }

    public Boolean Post(Context mContext) {
        this.mContext = mContext;
        eventByAsyncHttpClientPost();
        return true;
    }

    private void eventByAsyncHttpClientPost() {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/login/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventID", eventID);
            jsonObject.put("title", title);
            jsonObject.put("locationID", locationID);
            jsonObject.put("locationX", locationX);
            jsonObject.put("locationY", locationY);
            jsonObject.put("beginDate", beginDate);
            jsonObject.put("beginTime", beginTime);
            jsonObject.put("endDate", endDate);
            jsonObject.put("endTime", endTime);
            jsonObject.put("type", type);
            jsonObject.put("description", description);
            jsonObject.put("outdate", outdate);
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
                //Toast.makeText(mContext, "connection success!"+response.toString(), Toast.LENGTH_SHORT).show();
                //System.out.println("response: " + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //Toast.makeText(mContext, "connection error!Error number is:" + statusCode,  Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "connection error!Error number is:" + statusCode,  Toast.LENGTH_SHORT).show();
            }
        });
        return;
    }
}
