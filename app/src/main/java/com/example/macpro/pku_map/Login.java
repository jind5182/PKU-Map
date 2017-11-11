package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

public class Login extends Activity implements View.OnClickListener {

    private Button signinbtn = null;
    private Button signupbtn = null;
    private Button signinlater = null;
    private EditText username = null;
    private EditText passwd = null;
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
                    GetInfo task = new GetInfo(displaytxt);
                    task.loginByAsyncHttpClientPost(param[0], param[1]);
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
