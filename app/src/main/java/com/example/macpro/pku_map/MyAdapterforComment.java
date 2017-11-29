package com.example.macpro.pku_map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapterforComment extends BaseAdapter{

    private List<Comment> mComment;
    private Context mContext;

    public MyAdapterforComment(List<Comment> mComment, Context mContext) {
        this.mContext = mContext;
        this.mComment = mComment;
    }

    @Override
    public int getCount() {
        return mComment.size();
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
        MyAdapterforComment.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new MyAdapterforComment.ViewHolder();
            viewHolder.txt_item_title = (TextView) convertView.findViewById(R.id.txt_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyAdapterforComment.ViewHolder) convertView.getTag();
        }
        viewHolder.txt_item_title.setText(mComment.get(position).getUsername() + " : " + mComment.get(position).getContent());
        return convertView;
    }

    private class ViewHolder {
        TextView txt_item_title;
    }
}
