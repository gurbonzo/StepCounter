package com.example.stepcounter_V3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private List<String> portraitItemList;
    private LayoutInflater inflater;

    public StepAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);

    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.portrait_item, parent, false);
        return new StepViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        if(portraitItemList != null)
        {
            String mCurrent = portraitItemList.get(position);
            holder.stepItemView.setText(mCurrent);
        }
        else
        {
            holder.stepItemView.setText("No word");
        }


    }

    void setInfo(List<String> info)
    {
        portraitItemList = info;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if (portraitItemList != null) {
            return portraitItemList.size();
        }
        else
        {
            return 0;
        }

    }

    class StepViewHolder extends RecyclerView.ViewHolder{
        public final TextView stepItemView;
        final StepAdapter stepAdapter;

        public StepViewHolder(View itemView, StepAdapter adapter)
        {
            super(itemView);
            stepItemView = itemView.findViewById(R.id.stepcounter_item);
            stepAdapter = adapter;
        }
    }
}
