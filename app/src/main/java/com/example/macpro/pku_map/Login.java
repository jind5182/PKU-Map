package com.example.macpro.pku_map;

import android.util.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;
import java.io.*;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.entity.ByteArrayEntity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import org.apache.http.Consts;
import org.apache.http.message.BasicHeader;

public class Login extends Activity implements View.OnClickListener {

    private Button signinbtn = null;
    private Button signupbtn = null;
    private Button signinlater = null;
    private EditText username = null;
    private EditText passwd = null;
    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bindViews();
    }

    private void bindViews() {
        signinbtn = (Button) findViewById(R.id.signinbtn);
        signupbtn = (Button) findViewById(R.id.signupbtn);
        signinlater = (Button) findViewById(R.id.signinlater);
        username = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.passwd);
        signinbtn.setOnClickListener(this);
        signupbtn.setOnClickListener(this);
        signinlater.setOnClickListener(this);
    }

    private void loginByAsyncHttpClientPost(String userName, String userPass) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8001/login/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("password",userPass);
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
                Toast.makeText(Login.this, "status code is:"+ statusCode+ "connection success!"+response.toString(), Toast.LENGTH_SHORT).show();
                //Log.e("rs",response.toString());
                //Toast.makeText(mContext, "connection success!"+response.toString(), Toast.LENGTH_SHORT).show();
                //System.out.println("response: " + response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(mContext, "connection error!Error number is:" + statusCode,  Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "connection error!Error number is:" + statusCode,  Toast.LENGTH_SHORT).show();
            }
        });
        return;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signinbtn:
                String Username = username.getText().toString();
                String Passwd = passwd.getText().toString();
                if (Username.length() <= 0 || Passwd.length() <= 0) {
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    String[] param = {Username, Passwd};
                    TextView displaytxt = (TextView) findViewById(R.id.display_txt);
                    loginByAsyncHttpClientPost(param[0], param[1]);
                    Toast.makeText(this, "用户名：" + Username + " 密码为：" + Passwd, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }
                break;
            case R.id.signupbtn:
                startActivity(new Intent(Login.this, Signup.class));
                finish();
                break;
            case R.id.signinlater:
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
                break;
        }
    }
}
