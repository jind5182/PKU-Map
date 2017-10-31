package com.example.macpro.pku_map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class MyFragment2 extends Fragment {

    private FrameLayout fl_content;
    private ArrayList<Data> datas = null;
    private FragmentManager fManager = null;
    private Button addbtn = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2, container, false);
        fManager = getFragmentManager();
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        addbtn = (Button) view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewEvent.class));
            }
        });

        datas = new ArrayList<Data>();
        for (int i = 1; i <= 30; i++) {
            Data data = new Data("  事件" + i, i + "~事件内容及评论～～～～～～～～");
            datas.add(data);
        }
        ListFragment nlFragment = new ListFragment(fManager, datas);
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.fl_content, nlFragment);
        ft.commit();
        return view;
    }

}
