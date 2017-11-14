package com.example.macpro.pku_map;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EventFragment extends Fragment {

    private int which;
    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_content, container, false);
        TextView event_content = (TextView) view.findViewById(R.id.event_content);
        TextView event_title = (TextView)view.findViewById(R.id.event_title);
        event_title.setText(getArguments().getString("title"));
        event_content.setText(getArguments().getString("content"));
        which = getArguments().getInt("which");
        if (which == 0) {
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
        }
        if (which == 1) {
            final Button deletebtn = (Button) getActivity().findViewById(R.id.deletebtn);
            final Button editbtn = (Button) getActivity().findViewById(R.id.editbtn);
            final Button myeventret = (Button) getActivity().findViewById(R.id.myeventret);
            final Button myeventret2 = (Button) getActivity().findViewById(R.id.myeventret2);
            editbtn.setVisibility(View.GONE);
            deletebtn.setVisibility(View.VISIBLE);
            myeventret.setVisibility(View.GONE);
            myeventret2.setVisibility(View.VISIBLE);
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert = null;
                    builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialog);
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
                                    getFragmentManager().popBackStack();
                                    editbtn.setVisibility(View.VISIBLE);
                                    deletebtn.setVisibility(View.GONE);
                                    myeventret.setVisibility(View.VISIBLE);
                                    myeventret2.setVisibility(View.GONE);
                                }
                            }).create();
                    alert.show();
                }
            });
            myeventret2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                    editbtn.setVisibility(View.VISIBLE);
                    deletebtn.setVisibility(View.GONE);
                    myeventret.setVisibility(View.VISIBLE);
                    myeventret2.setVisibility(View.GONE);
                }
            });
        }
        if (which == 2) {
            final Button retbtn = (Button) getActivity().findViewById(R.id.retbtn);
            final RelativeLayout topbar = (RelativeLayout) getActivity().findViewById(R.id.topbar);
            retbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                    topbar.setVisibility(View.GONE);
                }
            });
        }
        return view;
    }
}
