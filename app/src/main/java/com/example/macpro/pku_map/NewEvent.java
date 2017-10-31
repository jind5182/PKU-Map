package com.example.macpro.pku_map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class NewEvent extends Activity implements View.OnClickListener{

    private Button publishbtn = null;
    private Button neretbtn = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newevent);
        bindViews();
    }

    private void bindViews() {
        publishbtn = (Button) findViewById(R.id.publishbtn);
        neretbtn = (Button) findViewById(R.id.neretbtn);
        publishbtn.setOnClickListener(this);
        neretbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publishbtn:
                break;
            case R.id.neretbtn:
                finish();
                break;
        }
    }
}
