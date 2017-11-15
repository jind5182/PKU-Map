package com.example.macpro.pku_map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Event> mEvent;
    private Context mContext;

    public MyAdapter(List<Event> mEvent, Context mContext) {
        this.mContext = mContext;
        this.mEvent = mEvent;
    }

    @Override
    public int getCount() {
        return mEvent.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txt_item_title = (TextView) convertView.findViewById(R.id.txt_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_item_title.setText(mEvent.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder {
        TextView txt_item_title;
    }
}
