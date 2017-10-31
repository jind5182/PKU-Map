package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.baidu.mapapi.SDKInitializer;

public class Start extends Activity {

    ImageButton imgbtn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        imgbtn = (ImageButton) findViewById(R.id.startbtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Start.this, Login.class));
                finish();
            }
        });
    }
}
