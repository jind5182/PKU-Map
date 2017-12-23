package com.example.macpro.pku_map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class Message extends Activity {

    private TimeCount time;
    private Button getcode, next;
    private ImageButton messageret;
    private TextView phone, code;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private String codestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        mContext = Message.this;
        time = new TimeCount(60000, 1000);
        phone = (TextView) findViewById(R.id.phone);
        code = (TextView) findViewById(R.id.code);
        messageret = (ImageButton) findViewById(R.id.messageret);
        messageret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert = null;
                builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                alert = builder.setMessage("是否确定退出注册？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Message.this, Login.class));
                                finish();
                            }
                        }).create();             //创建AlertDialog对象
                alert.show();                    //显示对话框
            }
        });
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (code.getText().toString().equals(codestr)) {
                    Intent it = new Intent(Message.this, Signup.class);
                    it.putExtra("phonenumber", phone.getText().toString());
                    startActivity(it);
                    finish();
                }
                else
                    Toast.makeText(Message.this, "未进行验证或验证码不正确", Toast.LENGTH_SHORT).show();
            }
        });
        getcode = (Button) findViewById(R.id.getcode);
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageAsyncHttpClientPost(phone.getText().toString());
                time.start();
            }
        });
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getcode.setClickable(false);
            getcode.setText("("+millisUntilFinished / 1000 +") 秒后\n重新发送");
        }

        @Override
        public void onFinish() {
            getcode.setText("重新获取验证码");
            getcode.setClickable(true);

        }
    }

    public void messageAsyncHttpClientPost(String phonenumber) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/getPhoneVariCode/";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", phonenumber);
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
                        Toast.makeText(mContext, "该手机号已经注册", Toast.LENGTH_SHORT).show();
                    }
                    else if(status == 0) {
                        codestr = response.getString("obj");
                        Toast.makeText(mContext, "短信已发送，请注意查收", Toast.LENGTH_SHORT).show();
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
