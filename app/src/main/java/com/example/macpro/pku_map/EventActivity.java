package com.example.macpro.pku_map;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity {

    private int which;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private Context mContext;
    private Button eventcontentret, deletebtn;
    private TextView event_content, event_title, eventcontenttitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_content);
        mContext = EventActivity.this;
        event_content = (TextView) findViewById(R.id.event_content);
        event_title = (TextView) findViewById(R.id.event_title);
        eventcontenttitle = (TextView) findViewById(R.id.eventcontenttitle);
        eventcontentret = (Button) findViewById(R.id.eventcontentret);
        eventcontentret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        deletebtn = (Button) findViewById(R.id.deletebtn);
        deletebtn.setVisibility(View.GONE);

        Bundle bd = getIntent().getExtras();
        event_title.setText(bd.getString("title"));
        event_content.setText(bd.getString("content"));
        which = bd.getInt("which");
        if (which == 1) {
            deletebtn.setVisibility(View.VISIBLE);
            eventcontenttitle.setText("我的事件");
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert = null;
                    builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                    alert = builder.setMessage("是否确定删除？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).create();
                    alert.show();
                }
            });
        }
    }
}
