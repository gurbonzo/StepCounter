package com.example.stepcounter_V3;

import android.content.Context;
import android.graphics.ColorSpace;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter {

    private ArrayList portraitItemList;
    private LayoutInflater inflater;
    private List<Step> steps;
    final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    public StepAdapter(Context context, ArrayList<PointsGraphSeries> portraitItemList)
    {
        inflater = LayoutInflater.from(context);
        this.portraitItemList = portraitItemList;

    }

    @Override
    public int getItemViewType(int position) {

        if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("graphseries"))
        {
            return 0;
        }
        else if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("float"))
        {
            return 1;
        }
        else
        {
            return 2;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 0:
                itemView = inflater.inflate(R.layout.portrait_graph_item, parent, false);
                return new GraphViewHolder(itemView, this);
            case 1:
                itemView = inflater.inflate(R.layout.portrait_item, parent, false);
                return new StepViewHolder(itemView, this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(portraitItemList != null)
        {
            if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("graphseries")) {
                GraphViewHolder graphViewHolder = (GraphViewHolder)holder;
                graphViewHolder.graph.addSeries(new PointsGraphSeries<DataPoint>(new DataPoint[]
                        {new DataPoint(0,0), new DataPoint(1,1), new DataPoint(2,1), new DataPoint(5,3), new DataPoint(7,7)}));
                Log.d("StepAdapter", String.valueOf(portraitItemList.get(position)));

                //graphViewHolder.graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(, dateFormat));
                //graphViewHolder.graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                //graphViewHolder.graph.getLegendRenderer().setVisible(true);
                //graphViewHolder.graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                //graphViewHolder.graph.getViewport().setXAxisBoundsManual(true);
                //graphViewHolder.graph.getViewport().setMinX(steps.get(0).getDate().getTime());
                //graphViewHolder.graph.getViewport().setMaxX(steps.get(0).getDate().getTime() + 2*24*60*60*1000);

                //graphViewHolder.graph.getViewport().setYAxisBoundsManual(true);
                //graphViewHolder.graph.getViewport().setMinY(0);

                //graphViewHolder.graph.getViewport().setScalable(true);
                //graphViewHolder.graph.getViewport().setScrollable(true);
                //graphViewHolder.graph.getViewport().setScrollableY(true);
                //graphViewHolder.graph.getViewport().setScalableY(true);

                //graphViewHolder.graph.getGridLabelRenderer().setHumanRounding(false);
                //String mCurrent = portraitItemList.get(position);
                //holder.stepItemView.setText(mCurrent);
            }
            else if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("float"))
            {
                //StepViewHolder stepViewHolder = (StepViewHolder)holder;
                //stepViewHolder.stepItemView.setText(portraitItemList.get(position));
            }
        }
        else
        {
           // holder.stepItemView.setText("No word");
        }

    }


    /**
    void setInfo(ArrayList info)
    {
        portraitItemList = info;
        //this.steps = (List<Step>) portraitItemList.get(1);
        notifyDataSetChanged();
    }
     **/

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

    class GraphViewHolder extends RecyclerView.ViewHolder
    {
        public final GraphView graph;
        final StepAdapter stepAdapter;

        public GraphViewHolder(View graphView, StepAdapter adapter)
        {
            super(graphView);
            graph = graphView.findViewById(R.id.graph);

            //graphViewHolder.graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(, dateFormat));
            //graph.getGridLabelRenderer().setNumHorizontalLabels(3);
            //graph.getLegendRenderer().setVisible(true);
            //graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            //graph.getViewport().setXAxisBoundsManual(true);
            //graph.getViewport().setMinX(steps.get(0).getDate().getTime());
            //graph.getViewport().setMaxX(steps.get(0).getDate().getTime() + 2*24*60*60*1000);

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);

            graph.getViewport().setScalable(true);
            graph.getViewport().setScrollable(true);
            graph.getViewport().setScrollableY(true);
            graph.getViewport().setScalableY(true);

            graph.getGridLabelRenderer().setHumanRounding(false);
            stepAdapter = adapter;
        }
    }
}
