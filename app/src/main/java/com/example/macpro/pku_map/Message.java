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

public class Message extends Activity {

    private TimeCount time;
    private Button getcode, next;
    private ImageButton messageret;
    private TextView phone, code;
    private Context mContext;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        mContext = Message.this;
        PreferenceUtil.ischecked = false;
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
                if (true/*PreferenceUtil.ischecked*/) {
                    Intent it = new Intent(Message.this, Signup.class);
                    it.putExtra("phonenumber", phone.getText().toString());
                    startActivity(it);
                    finish();
                }
                else
                    Toast.makeText(Message.this, "请先进行手机短信验证", Toast.LENGTH_SHORT).show();
            }
        });
        getcode = (Button) findViewById(R.id.getcode);
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "短信已发送，请注意查收", Toast.LENGTH_SHORT).show();
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
            getcode.setBackgroundColor(Color.parseColor("#B6B6D8"));
            getcode.setClickable(false);
            getcode.setText("("+millisUntilFinished / 1000 +") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            getcode.setText("重新获取验证码");
            getcode.setClickable(true);
            getcode.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }
}
