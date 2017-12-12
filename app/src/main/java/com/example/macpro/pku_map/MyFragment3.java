package com.example.macpro.pku_map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PreemptiveAuthorizationHttpRequestInterceptor;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MyFragment3 extends Fragment {

    private Button myevent = null;
    private Button pwd = null;
    private Context mContext = null;
    private TextView name = null;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content3, container, false);
        mContext = getActivity();
        name = (TextView) view.findViewById(R.id.name);
        if (PreferenceUtil.islogged)
            name.setText(PreferenceUtil.username);
        myevent = (Button) view.findViewById(R.id.myevent);
        pwd = (Button) view.findViewById(R.id.pwd);
        pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceUtil.islogged)
                    startActivity(new Intent(getActivity(), ChangePwd.class));
                else
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        logout = (Button)view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtil.islogged = false;
                Toast.makeText(getActivity(), "退出登录",  Toast.LENGTH_SHORT);
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
        myevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceUtil.islogged)
                    startActivity(new Intent(getActivity(), Myevent.class));
                else
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
