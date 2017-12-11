package com.example.macpro.pku_map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ChangePwd extends Activity {
    private Button change_pwd;
    private ImageButton change_pwd_ret;
    private EditText ori_pwd, new_pwd, confirm_pwd;
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

    private void loginByAsyncHttpClientPost(int userid, String oripwd) {
        //创建异步请求对象
        AsyncHttpClient client = new AsyncHttpClient();
        //输入要请求的url
        String url = "http://120.25.232.47:8002/login/";
        //String url = "http://www.baidu.com";
        //请求的参数对象
        JSONObject jsonObject = new JSONObject();
        userPass = getMD5(userPass);
        try {
            jsonObject.put("userName",userName);
            jsonObject.put("pwd",userPass);
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
                    int status = response.getInt("loginStatus");
                    if (status == 1) {
                        Toast.makeText(mContext, "用户名或密码输入错误",  Toast.LENGTH_LONG).show();
                    }
                    else if(status == 0) {
                        PreferenceUtil.islogged = true;
                        PreferenceUtil.userID = response.getInt("userID");
                        PreferenceUtil.username = username;
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pwd);
        bindViews();
    }

    private void bindViews() {
        change_pwd_ret = (ImageButton) findViewById(R.id.change_pwd_ret);
        change_pwd = (Button) findViewById(R.id.change_pwd);
        ori_pwd = (EditText) findViewById(R.id.ori_pwd);
        new_pwd = (EditText) findViewById(R.id.new_pwd);
        confirm_pwd = (EditText) findViewById(R.id.confirm_pwd);

        change_pwd_ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Signup.this, Login.class));
                finish();
            }
        });
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OriPwd = ori_pwd.getText().toString();
                String NewPwd = new_pwd.getText().toString();
                String ConfirmPwd = confirm_pwd.getText().toString();
                int UserID = PreferenceUtil.userID;
                if(OriPwd.length() == 0) {
                    Toast.makeText(mContext, "原密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(NewPwd.length() == 0){
                    Toast.makeText(mContext, "请输入新密码", Toast.LENGTH_SHORT).show();
                }
                else if(NewPwd.length() > 32){
                    Toast.makeText(mContext, "密码不能超过32个字符", Toast.LENGTH_SHORT).show();
                }
                else if (!ConfirmPwd.equals(NewPwd)) {
                    Toast.makeText(mContext, "前后输入的密码不一致，请再次输入", Toast.LENGTH_SHORT).show();
                }
                else {
                    String[] param = {OriPwd, NewPwd, ConfirmPwd};
                    TextView displaytxt = (TextView) findViewById(R.id.display_txt);
                    loginByAsyncHttpClientPost(UserID, OriPwd);
                }
                //startActivity(new Intent(Signup.this, MainActivity.class));
                //finish();
            }
        });
    }

}
