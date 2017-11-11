package com.example.macpro.pku_map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private FragmentManager fManager;
    private ArrayList<Data> datas;
    private ListView list_event;

    public ListFragment() {}

    public ListFragment(FragmentManager fManager, ArrayList<Data> datas) {
        this.fManager = fManager;
        this.datas = datas;
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
        ncFragment.setArguments(bd);
        fTransaction.replace(R.id.fl_content, ncFragment);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }
}
