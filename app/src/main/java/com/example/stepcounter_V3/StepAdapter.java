package com.example.stepcounter_V3;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    double testMin;
    Calendar calendar;

        public StepAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);
            stepSeries = new PointsGraphSeries<>();
            stepLineSeries = new LineGraphSeries<>();

        }



        @Override
        public GraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;

                    itemView = inflater.inflate(R.layout.portrait_graph_item, parent, false);
                    return new GraphViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull GraphViewHolder holder, int position) {

            if(recyclerData != null)
            {
                ((GraphViewHolder)holder).graph.addSeries(stepSeries);
                ((GraphViewHolder)holder).graph.addSeries(stepLineSeries);

            }


        }


        void setInfo(DataPoint[] info)
        {

            recyclerData = info;


                stepSeries.resetData(recyclerData);
                stepLineSeries.resetData(recyclerData);


            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {

            if (recyclerData != null) {

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
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));


                //wow how cool is this to type of a big monitor (Typed on JD's wide screen monitor on March 16, 2022)
                graph.getViewport().setXAxisBoundsManual(true);



                if(recyclerData.length == 1)
                {
                    graph.getGridLabelRenderer().setNumHorizontalLabels(1);
                    graph.getViewport().setMaxX(recyclerData[recyclerData.length-1].getX());
                    graph.getViewport().setMinX(recyclerData[0].getX());

                }
                else if (recyclerData.length == 2)
                {
                    graph.getGridLabelRenderer().setNumHorizontalLabels(2);
                    graph.getViewport().setMaxX(recyclerData[recyclerData.length-1].getX());
                    graph.getViewport().setMinX(recyclerData[0].getX());
                }
                else if (recyclerData.length > 2)
                {
                    graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                    graph.getViewport().setMaxX(recyclerData[recyclerData.length-1].getX());
                    graph.getViewport().setMinX(recyclerData[recyclerData.length-3].getX());
                }


                //code below is for finding the max Y and setting the viewport to have the maxY.

                 maxValue = (int)recyclerData[0].getY();
                for(int i = 0; i < recyclerData.length; i++)
                {
                    if(recyclerData[i].getY() > maxValue)
                    {
                        maxValue = (int)recyclerData[i].getY();
                    }
                }
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMaxY(maxValue);
                graph.getViewport().setMinY(0);
                graph.getViewport().setScalableY(true);
                graph.getGridLabelRenderer().setHumanRounding(false);

                //below code is reset graph viewport after zooming, has not been tested yet Feb. 14, 2022
                graph.setOnTouchListener(new View.OnTouchListener(){
                    GestureDetector gestureDetector = new GestureDetector(graph.getContext(), new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {

                            graph.getViewport().setXAxisBoundsManual(true);
                            if(recyclerData.length <= 3 )
                            {
                                graph.getViewport().setMinX(recyclerData[0].getX());
                            }
                            else
                            {
                                graph.getViewport().setMinX(recyclerData[recyclerData.length-3].getX());
                            }

                            graph.getViewport().setMaxX(recyclerData[recyclerData.length-1].getX());
                            graph.getViewport().setYAxisBoundsManual(true);
                            graph.getViewport().setMinY(0);
                            maxValue = (int)recyclerData[0].getY();
                            for(int i = 0; i < recyclerData.length; i++)
                            {
                                if(recyclerData[i].getY() > maxValue)
                                {
                                    maxValue = (int)recyclerData[i].getY();
                                }

                            }
                            graph.getViewport().setMaxY(maxValue);

                            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
                            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                            graph.onDataChanged(true, true);
                            return super.onDoubleTap(e);
                        }
                    });

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });


            }
        }
    }



