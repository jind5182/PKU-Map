package com.example.macpro.pku_map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
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

public class MyFragment1 extends Fragment {

    MapView map = null;
    BaiduMap bdmap;
    private Button locbtn = null;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Marker marker;
    private Event[] eventList = new Event[1000];
    private int count = 0;
    private BitmapDescriptor bitmap = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_gcoding);
    private static final int msgKey1 = 1;
    private MyFragment1.TimeThread update_thread;
    private InfoWindow addWindow;
    private InfoWindow eventWindow;
    private FrameLayout buttomfl;
    private Button tobuttom;
    private View nothing;

    public class TimeThread extends Thread {
        private final Object lock = new Object();
        private boolean pause = false;
        void pauseThread(){
            pause = true;
        }
        void resumeThread(){
            pause = false;
            synchronized (lock){
                lock.notifyAll();
            }
        }
        void onPause(){
            synchronized (lock){
                try{
                    lock.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void run() {
            do {
                try {
                    while (pause){
                        onPause();
                    }
                    Thread.sleep(5000);
                    if (pause){
                        onPause();
                    }
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
                    getEventByTypeAsyncHttpClientPost(PreferenceUtil.maptype);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content1, container, false);
        mContext = getActivity();
        buttomfl = (FrameLayout) view.findViewById(R.id.buttomfl);
        tobuttom = (Button) view.findViewById(R.id.tobuttom);
        nothing = (View) view.findViewById(R.id.nothing);
        map = (MapView) view.findViewById(R.id.bdmap);
        locbtn = (Button) view.findViewById(R.id.locbtn);
        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] eventtype = new String[]{"实时", "活动预告", "求救"};
                builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                builder.setCancelable(true);
                alert = builder
                        .setItems(eventtype, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                locbtn.setText(eventtype[which]);
                                PreferenceUtil.maptype = which;
                                getEventByTypeAsyncHttpClientPost(PreferenceUtil.maptype);
                            }
                        }).create();
                alert.show();
                Window dialogWindow = alert.getWindow();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
                WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
                Point size = new Point();
                d.getSize(size);
                p.width = (int) (size.x * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
                dialogWindow.setAttributes(p);
            }
        });
        bdmap = map.getMap();
        bdmap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bdmap.hideInfoWindow();
                update_thread.resumeThread();
                if (buttomfl.getVisibility() == View.VISIBLE)
                    hidebuttom();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                bdmap.hideInfoWindow();
                update_thread.resumeThread();
                if (buttomfl.getVisibility() == View.VISIBLE)
                    hidebuttom();
                Toast.makeText(mContext, "请到详情页选择地点！", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        bdmap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {
                bdmap.hideInfoWindow();
                update_thread.pauseThread();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.addwindow, null);
                Button add = view.findViewById(R.id.addButton);

                add.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //在这个地方转到添加新事件
                        if (PreferenceUtil.islogged) {
                            double coordinateX = latLng.longitude;
                            double coordinateY = latLng.latitude;
                            /*LatLng northwest = new LatLng(40.005716, 116.310486);
                            LatLng southeast = new LatLng(39.992755, 116.328488);*/
                            if (coordinateY < 39.992755 || coordinateY > 40.005716 || coordinateX < 116.310486 || coordinateX > 116.328488)
                            {
                                Toast.makeText(mContext, "超出服务范围", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Bundle bd = new Bundle();
                            bd.putDouble("locationX", coordinateX);
                            bd.putDouble("locationY", coordinateY);
                            Intent it = new Intent(getActivity(), NewEvent.class);
                            it.putExtras(bd);
                            startActivity(it);
                        }
                        else
                            Toast.makeText(mContext, "请先登录", Toast.LENGTH_SHORT).show();
                    }
                });
                addWindow = new InfoWindow(view, latLng, 0);
                bdmap.showInfoWindow(addWindow);
            }
        });
        bdmap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                update_thread.pauseThread();
                final int eventIndex = (int)marker.getExtraInfo().get("index");
                if (eventList[eventIndex].getLocationID() >= 0) {
                    showbuttom(eventList[eventIndex].getLocationID(), PreferenceUtil.maptype);
                    return false;
                }
                bdmap.hideInfoWindow();
                hidebuttom();
                LayoutInflater inflater = LayoutInflater.from(mContext);
                View view = inflater.inflate(R.layout.infowindow, null);
                TextView title = (TextView)view.findViewById(R.id.popTitle);
                Button getContent = (Button)view.findViewById(R.id.getContent);
                title.setText("标题: "+eventList[eventIndex].title);
                getContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bd = new Bundle();
                        bd.putInt("eventID", eventList[eventIndex].getEventId());
                        bd.putInt("which", 2);
                        Intent it = new Intent(getActivity(), EventActivity.class);
                        it.putExtras(bd);
                        startActivity(it);
                    }
                });
                LatLng point = marker.getPosition();
                eventWindow = new InfoWindow(view, point, -47);
                bdmap.showInfoWindow(eventWindow);
                return false;
            }
        });
        return view;
    }

    private void showbuttom(int locationID, int type) {
        ((MainActivity)getActivity()).setvisibility(false);
        buttomfl.setVisibility(View.VISIBLE);
        tobuttom.setVisibility(View.VISIBLE);
        nothing.setVisibility(View.INVISIBLE);
        FragmentManager fManager = getFragmentManager();
        ListFragment nlFragment = new ListFragment(3);
        Bundle bd = new Bundle();
        bd.putInt("locationID", locationID);
        bd.putInt("type", type);
        nlFragment.setArguments(bd);
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.buttomfl, nlFragment);
        ft.commit();
        tobuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tobuttom.setVisibility(View.GONE);
                buttomfl.setVisibility(View.GONE);
                nothing.setVisibility(View.GONE);
                ((MainActivity)getActivity()).setvisibility(true);
            }
        });
    }

    private void hidebuttom() {
        tobuttom.setVisibility(View.GONE);
        buttomfl.setVisibility(View.GONE);
        nothing.setVisibility(View.GONE);
        ((MainActivity)getActivity()).setvisibility(true);
    }

    public void onStart(){
        super.onStart();
        getEventByTypeAsyncHttpClientPost(PreferenceUtil.maptype);
        update_thread = new MyFragment1.TimeThread();
        update_thread.start();
    }

    public void onDestroy(){
        super.onDestroy();
        map.onDestroy();
    }
    public void onResume(){
        super.onResume();
        map.onResume();
        //设定地图显示范围
        bdmap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                /*LatLng northwest = new LatLng(40.005716, 116.310486);
                LatLng southeast = new LatLng(39.992755, 116.328488);
                LatLngBounds bound = new LatLngBounds.Builder().include(northwest).include(southeast).build();
                bdmap.setMapStatusLimits(bound);*/
                LatLng center = new LatLng(39.99907, 116.316289);
                MapStatus mMapStatus = new MapStatus.Builder().target(center).zoom(17).build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                bdmap.setMapStatus(mMapStatusUpdate);
            }
        });
    }
    public void onPause(){
        super.onPause();
        map.onPause();
    }
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
                        bdmap.clear();
                        JSONArray events = response.getJSONArray("events");
                        //Toast.makeText(mContext, events.toString(), Toast.LENGTH_LONG).show();
                        for (int i = 0; i < count; i++)
                        {
                            JSONObject temp = events.getJSONObject(i);
                            eventList[i] = new Event();
                            eventList[i].setEventID(temp.getInt("eventID"));
                            //eventList[i].setBeginTime(temp.getString("beginTime"));
                            eventList[i].setDescription(temp.getString("description"));
                            //eventList[i].setEndTime(temp.getString("endTime"));
                            if (temp.getInt("locationID") == -1)
                                eventList[i].setLocation(temp.getDouble("locationX"), temp.getDouble("locationY"));
                            else
                                eventList[i].setLocation(temp.getInt("locationID"));
                            eventList[i].setOutdate(temp.getInt("outdate"));
                            eventList[i].setType(temp.getInt("type"));
                            if (eventList[i].getType() == 2) {
                                eventList[i].setIshelped(temp.getBoolean("ishelped"));
                                eventList[i].setHelper(temp.getInt("helperID"));
                            }
                            eventList[i].setPublisherID(temp.getInt("publisherID"));
                            eventList[i].setTitle(temp.getString("title"));
                            eventList[i].setUsername(temp.getString("username"));
                            Bundle bundle = new Bundle();
                            bundle.putInt("index", i);
                            LatLng point = new LatLng(eventList[i].locationY, eventList[i].locationX);
                            if (eventList[i].locationID >= 0)
                                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding2);
                            else
                                bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding);
                            OverlayOptions option = new MarkerOptions()
                                    .position(point)
                                    .icon(bitmap)
                                    .extraInfo(bundle);
                            //在地图上添加Marker，并显示
                            marker = (Marker) bdmap.addOverlay(option);
                        }
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