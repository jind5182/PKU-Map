package com.example.macpro.pku_map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private ListView list_event;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;
    private int which;

    public ListFragment() {}

    public ListFragment(FragmentManager fManager, ArrayList<Data> datas, int which) {
        this.fManager = fManager;
        this.datas = datas;
        this.which = which;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.eventlist, container, false);
        list_event = (ListView) view.findViewById(R.id.list_event);
        MyAdapter myAdapter = new MyAdapter(datas, getActivity());
        list_event.setAdapter(myAdapter);
        list_event.setOnItemClickListener(this);
        list_event.setOnItemLongClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        EventActivity ncFragment = new EventActivity();
        Bundle bd = new Bundle();
        bd.putString("title", datas.get(position).getNew_title());
        bd.putString("content", datas.get(position).getNew_content());
        bd.putInt("which", which);
        Intent it = new Intent(getActivity(), EventActivity.class);
        it.putExtras(bd);
        startActivity(it);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (which == 1) {
            builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            builder.setCancelable(true);
            alert = builder.setMessage("是否确定删除？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
            alert.show();
        }
        return true;
    }
}