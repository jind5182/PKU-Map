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
    public int publisherID;
    public String title;
    public int locationID;
    public double locationX, locationY;
    public String beginDate, beginTime, endDate, endTime;
    public int type;
    public String description;
    public int outdate;
    private Context mContext = null;

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
    public void setPublisherID(int publisherID){
        this.publisherID = publisherID;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocation(int index) {
        this.locationID = index;
        this.locationX = PreferenceUtil.coordinateX[index];
        this.locationY = PreferenceUtil.coordinateY[index];
    }
    /*public void setBeginTime(String beginDate, String beginTime) {
        this.beginDate = beginDate;
        this.beginTime = beginTime;
    }*/
    public void setBeginTime(String beginTime){
        this.beginTime = beginTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    /*public void setEndTime(String endDate, String endTime) {
        this.endDate = endDate;
        this.endTime = endTime;
    }*/
    public void setType(String type) {
        switch (type) {
            case "实时":
                this.type = 0;
                break;
            case "活动预告":
                this.type = 1;
                break;
            case "求救":
                this.type = 2;
                break;
        }
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setOutdate(int outdate) {
        this.outdate = outdate;
    }
}
