package com.example.stepcounter_V3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter {





        private ArrayList portraitItemList;
        private LayoutInflater inflater;
        private List<Step> steps;
        final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        public StepAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);

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


        void setInfo(ArrayList info)
        {
            portraitItemList = info;
            //this.steps = (List<Step>) portraitItemList.get(1);
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
            final com.example.stepcounter_V3.StepAdapter stepAdapter;

            public StepViewHolder(View itemView, com.example.stepcounter_V3.StepAdapter adapter)
            {
                super(itemView);
                stepItemView = itemView.findViewById(R.id.stepcounter_item);
                stepAdapter = adapter;
            }


        }

        class GraphViewHolder extends RecyclerView.ViewHolder
        {
            public final GraphView graph;
            final com.example.stepcounter_V3.StepAdapter stepAdapter;

            public GraphViewHolder(View graphView, com.example.stepcounter_V3.StepAdapter adapter)
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



