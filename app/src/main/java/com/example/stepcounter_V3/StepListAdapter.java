package com.example.stepcounter_V3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jjoe64.graphview.GraphView;

import java.util.List;

public class StepListAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private List<Step> mStps;

    StepListAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    /*public StepViewHolder onCreateViewHolder()
    {

    }

     */
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mStps != null)
            return mStps.size();
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
