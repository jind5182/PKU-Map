package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.AsyncHttpClient;

public class Signup extends Activity{

    private Button su;
    private ImageButton suret;
    private EditText suusername, supasswd, supasswd2, suid, suphonenumber;
    Context mContext = this;

    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }

    public static String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        bindViews();
    }


    private void signupByAsyncHttpClientPost(String... param) {
        String studentID = param[0];
        String userName = param[1];
        String userPass = param[2];
        String userPhoneNumber = param[3];
        userPass = getMD5(userPass);
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/signup/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("studentID",studentID);
            jsonObject.put("userName",userName);
            jsonObject.put("pwd",userPass);
            jsonObject.put("contact", userPhoneNumber);
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
                    int status = response.getInt("signupStatus");
                    if(status == 0) {
                        //PreferenceUtil.islogged = true;
                        //PreferenceUtil.userID = response.getInt("userID");
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                    if (status == 1) {
                        Toast.makeText(mContext, "学号已存在",  Toast.LENGTH_LONG).show();
                    }
                    else if (status == 2){
                        Toast.makeText(mContext, "用户名已存在",  Toast.LENGTH_LONG).show();
                    }
                    else if (status == 3){
                        Toast.makeText(mContext, "手机号已存在",  Toast.LENGTH_LONG).show();
                    }
                    else if (status == 4){
                        Toast.makeText(mContext, "error! signupStatus is:" + status ,Toast.LENGTH_LONG).show();
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

    private void bindViews() {
        suret = (ImageButton) findViewById(R.id.suret);
        su = (Button) findViewById(R.id.su);
        suusername = (EditText) findViewById(R.id.suusername);
        supasswd = (EditText) findViewById(R.id.supasswd);
        supasswd2 = (EditText) findViewById(R.id.supasswd2);
        suid = (EditText) findViewById(R.id.suid);
        suphonenumber = (EditText) findViewById(R.id.suphonenumber);
        suret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
                finish();
            }
        });
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentID = suid.getText().toString();
                String Username = suusername.getText().toString();
                String UserPass = supasswd.getText().toString();
                String UserPassConf = supasswd2.getText().toString();
                String UserPhoneNumber = suphonenumber.getText().toString();
                if (studentID.length() != 10) {
                    Toast.makeText(mContext, "请填入10位学号", Toast.LENGTH_SHORT).show();
                }
                else if(Username.length() == 0) {
                    Toast.makeText(mContext, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(Username.length() > 10) {
                    Toast.makeText(mContext, "用户名不能超过10个字符", Toast.LENGTH_SHORT).show();
                }
                else if(UserPhoneNumber.length() == 0){
                    Toast.makeText(mContext, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(UserPass.length() == 0){
                    Toast.makeText(mContext, "  请输入密码", Toast.LENGTH_SHORT).show();
                }
                else if(UserPass.length() > 32){
                    Toast.makeText(mContext, "  密码不能超过32个字符", Toast.LENGTH_SHORT).show();
                }
                else if(UserPassConf.length() == 0){
                    Toast.makeText(mContext, "  请输入确认密码", Toast.LENGTH_SHORT).show();
                }
                else if (!UserPass.equals(UserPassConf)) {
                    Toast.makeText(mContext, "前后输入的密码不一致，请再次尝试", Toast.LENGTH_SHORT).show();
                }
                else {
                    String[] param = {studentID, Username, UserPass, UserPhoneNumber};
                    TextView displaytxt = (TextView) findViewById(R.id.display_txt);
                    signupByAsyncHttpClientPost(param);
                }
                //startActivity(new Intent(Signup.this, MainActivity.class));
                //finish();
            }
        });
    }

}
