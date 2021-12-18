package com.example.stepcounter_V3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.GraphViewHolder> {

    /**
     * Order in which RecyclerView methods are called
     1.The RecyclerView attempts to grab a view holder for a certain position from its pool of recycled (or scrapped) view holders.
     2.When it does this, it calls getItemViewType for the position it wants to fill
     3.It then attempts to grab a previously created view holder associated with this item view type.
     4.If the RecyclerView view doesn't have an available view holder for this item type, it calls onCreateViewHolder in order to create a new view holder for this item type.
     5.Once the view holder is attained (whether by creating a new one, or grabbing a scrapped / recycled one), it then calls onBindViewHolder from the adapter to set the correct data for the view holder.
     6.During all this, getItemCount will be used to ensure that the RecyclerView doesn't attempt to populate a view holder beyond the limit of the data-source.
     */




        private LayoutInflater inflater;
        private List<Step> steps;
        final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        DataPoint [] recyclerData;
    private PointsGraphSeries<DataPoint> stepSeries;
    private LineGraphSeries<DataPoint> stepLineSeries;
    int maxValue;
    int minValue;

        public StepAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);
            stepSeries = new PointsGraphSeries<>();
            stepLineSeries = new LineGraphSeries<>();



        }

        /**
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
         **/

        @Override
        public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            //switch (viewType) {
              //  case 0:
                    itemView = inflater.inflate(R.layout.portrait_graph_item, parent, false);
                    return new GraphViewHolder(itemView);
               // case 1:
                   // itemView = inflater.inflate(R.layout.portrait_item, parent, false);
                   // return new StepViewHolder(itemView, this);
            //}
            //return null;
        }

        @Override
        public void onBindViewHolder(@NonNull GraphViewHolder holder, int position) {

            if(recyclerData != null)
            {
                ((GraphViewHolder)holder).graph.addSeries(stepSeries);
                ((GraphViewHolder)holder).graph.addSeries(stepLineSeries);
            }

            /**
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
             **/

        }


        void setInfo(DataPoint[] info)
        {
           // recyclerData = new DataPoint[info.length];
            recyclerData = info;
            //recyclerDatasize = recyclerData.length;

                stepSeries.resetData(recyclerData);
                stepLineSeries.resetData(recyclerData);

            //this.steps = (List<Step>) portraitItemList.get(1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {

            if (recyclerData != null) {
                //return recyclerData.length;
                return 1;
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
            //final com.example.stepcounter_V3.StepAdapter stepAdapter;
           // final StepAdapter stepAdapter;

            public GraphViewHolder(View graphView)
            {
                super(graphView);
                graph = graphView.findViewById(R.id.graph);

                //graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext(), dateFormat));
                graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graph.getViewport().setXAxisBoundsManual(true);
               // graph.getViewport().setMinX(0);
               // graph.getViewport().setMaxX(100);
                maxValue = (int)recyclerData[0].getX();
                for(int i = 0; i < recyclerData.length; i++)
                {
                    if(recyclerData[i].getX() > maxValue)
                    {
                        maxValue = (int)recyclerData[i].getX();
                    }

                }
                graph.getViewport().setMaxX(maxValue);
                minValue = (int)recyclerData[0].getX();
                for(int i = 0; i < recyclerData.length; i++)
                {
                    if(recyclerData[i].getX() < minValue)
                    {
                        minValue = (int)recyclerData[i].getX();
                    }

                }
                graph.getViewport().setMinX(minValue);

                //graph.getViewport().setMinX(steps.get(0).getDate().getTime());
                //graph.getViewport().setMaxX(steps.get(0).getDate().getTime() + 2*24*60*60*1000);

                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(100);

                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
                graph.getViewport().setScrollableY(true);
                graph.getViewport().setScalableY(true);

                graph.getGridLabelRenderer().setHumanRounding(false);
                //graph.addSeries(stepSeries);
                //graph.addSeries(stepLineSeries);
               // stepAdapter = adapter;
            }
        }
    }



