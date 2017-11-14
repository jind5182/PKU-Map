package com.example.macpro.pku_map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private ListView list_event;
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
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        EventFragment ncFragment = new EventFragment();
        Bundle bd = new Bundle();
        bd.putString("title", datas.get(position).getNew_title());
        bd.putString("content", datas.get(position).getNew_content());
        bd.putInt("which", which);
        ncFragment.setArguments(bd);
        if (which == 0)
            fTransaction.replace(R.id.fl_content, ncFragment);
        if (which == 1)
            fTransaction.replace(R.id.myeventfl, ncFragment);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }
}
