package com.example.macpro.pku_map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by macpro on 2017/9/28.
 */

public class EventFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_content, container, false);
        TextView event_content = (TextView) view.findViewById(R.id.event_content);
        TextView event_title = (TextView)view.findViewById(R.id.event_title);
        event_title.setText(getArguments().getString("title"));
        event_content.setText(getArguments().getString("content"));
        final ImageView retpic = (ImageView) getActivity().findViewById(R.id.retpic);
        final Button retbtn = (Button) getActivity().findViewById(R.id.retbtn);
        retpic.setVisibility(View.VISIBLE);
        retbtn.setVisibility(View.VISIBLE);
        retbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
                retpic.setVisibility(View.INVISIBLE);
                retbtn.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

}
