package com.example.macpro.pku_map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by macpro on 2017/10/31.
 */

public class CustomExpandableListView extends ExpandableListView{

    public CustomExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CustomExpandableListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CustomExpandableListView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                View.MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
