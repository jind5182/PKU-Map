package com.example.macpro.pku_map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Signup extends Activity{

    private Button su;
    private ImageButton suret;
    private EditText suusername, supasswd, supasswd2, suid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        bindViews();
    }

    private void bindViews() {
        suret = (ImageButton) findViewById(R.id.suret);
        su = (Button) findViewById(R.id.su);
        suusername = (EditText) findViewById(R.id.suusername);
        supasswd = (EditText) findViewById(R.id.supasswd);
        supasswd2 = (EditText) findViewById(R.id.supasswd2);
        suid = (EditText) findViewById(R.id.suid);
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
                startActivity(new Intent(Signup.this, MainActivity.class));
                finish();
            }
        });
    }
}
