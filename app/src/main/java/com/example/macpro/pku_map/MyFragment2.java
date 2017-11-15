package com.example.macpro.pku_map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MyFragment2 extends Fragment {

    private FragmentManager fManager = null;
    private Button addbtn = null;
    private Context mContext = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2, container, false);
        mContext = getActivity();
        fManager = getFragmentManager();
        addbtn = (Button) view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceUtil.islogged)
                    startActivity(new Intent(getActivity(), NewEvent.class));
                else
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        ListFragment nlFragment = new ListFragment(0);
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.fl_content, nlFragment);
        ft.commit();
        return view;
    }

}
