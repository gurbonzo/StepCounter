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
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*

    1.You bind data to your AdapterView using an adapter.
    2.The AdapterView tries to display items that are visible to the user.
    3.The framework calls getItemViewType for row n, the row it is about to display.
    4.The framework checks its recycled views pool for views of row n's type. It doesn't find any because no views have been recycled yet.
    5. The framework then calls onCreateViewHolder to create new view holder for the item type
    5.getView is called for row n. //where is the get view method?
    6.You call getItemViewType for row n to determine what type of view you should use.
    7.You use an if/switch statement to inflate a different xml file depending on which view type is required.
    8.You fill the view with information.
    9.You return the view, exiting getView, and your row's view is displayed to the user.


 */

public class StepAdapter extends RecyclerView.Adapter {

    private ArrayList<PointsGraphSeries> portraitItemList;
    private LayoutInflater inflater;
   // private List<Step> steps;
   private PointsGraphSeries<DataPoint> copyOfSeries =  new PointsGraphSeries();
   private LineGraphSeries<DataPoint> copyOfLineSeries =  new LineGraphSeries();
   public static final String TAG = "Who's there?";
    final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    public StepAdapter(Context context, ArrayList<PointsGraphSeries> portraitItemList)
    {
        inflater = LayoutInflater.from(context);
        this.portraitItemList = portraitItemList;

    }

    //getItemViewType is then called after the notifyDataChanged method. This method first figures out what type of view is required for each object.
    //Note the "int position" refers to the position of the item within the adapter's data set whose view type we want (I think)...
    @Override
    public int getItemViewType(int position) {

        //looks to see if the class name of the object in the portraitItemList is graphseries. If so, it returns a 0 (arbitrary).
        //Then it looks to see if a view has already been created with the label of 0. If not then onCreateViewHolder is called...
        //Log.d(TAG, portraitItemList.get(position).getClass().getSimpleName());
        if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("step"))
        {
            return 0;
        }
        //if not graphseries, it then looks to see if the class name is float. If so, returns 1 (again arbitrary; could be any number).
        else if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("float"))
        {
            return 1;
        }
        else
        {
            return 2;
        }

    }

    //method creates views if there are none that have been made previously. Utilizes the holder classes found below that extend the RecyclerView.ViewHolder class
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            
            //For case 0, creates the graphviewholder
            itemView = inflater.inflate(R.layout.portrait_graph_item, parent, false);
            return new GraphViewHolder(itemView, this);
        }
        else if(viewType == 1)
        {

            //For case 1, creates the stepViewholder
                itemView = inflater.inflate(R.layout.portrait_item, parent, false);
                return new StepViewHolder(itemView, this);
        }
        else {
            return null;
        }
    }

    //onBindViewHolder binds the view that has been created with the information in the object in the portraitItemList...
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //checks to make sure that the portraitItemList is not empty which could occur if there was the option of deleting things from the database...
        if(portraitItemList != null)
        {
            //rechecking to make sure that the item in the list has the class name graphseries and then adds the data to
            if(portraitItemList.get(position).getClass().getSimpleName().toLowerCase().contains("list")) {
                GraphViewHolder graphViewHolder = (GraphViewHolder)holder;
                graphViewHolder.graph.addSeries(copyOfSeries);
                graphViewHolder.graph.addSeries(copyOfLineSeries);
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


   /** void setInfo(List info)
    {
        portraitItemList = info; //portraitItemList has been updated by GraphActivity
        DataPoint [] stepData = new DataPoint[portraitItemList.size()];
        for (int i = 0; i < portraitItemList.size(); i++)
        {
            Date xValue = portraitItemList.get(i).getDate(); //the date value for each step object is taken and stored as an xValue variable.

            int yValue = (int) portraitItemList.get(i).getStep(); //the number of steps in each step object is taken and stored as the yvalue variable.

            DataPoint stepPoint = new DataPoint(xValue, yValue); //the xValue and yValue, representing the date and steps taken for each step object as it comes up in the for loop
            // is put into a DataPoint object that stores these values as coordinates.
            stepData[i] = stepPoint; //the DataPoint object is then put into an array called stepData that is also ratcheted up by the for loop.
        }
        //this.steps = (List<Step>) portraitItemList.get(1);
        //copyOfSeries =  new PointsGraphSeries();
        //copyOfLineSeries = new LineGraphSeries();
        copyOfSeries.resetData(stepData);
        copyOfLineSeries.resetData(stepData);
        notifyDataSetChanged(); //inbuilt method that notifies the adapter that the data has changed
    } **/

    //supposedly, the entire time that RecyclerView is activated, the getItemCount method is keeping track of the number of objects and making sure that
    //the RecyclerView doesn't go over the number of items in the portraitItemList.
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

    //ViewHolders are classes in the RecyclerView framework that are used by the overarching recyclerview. The one below is a viewholder meant to hold onto the max number of steps...
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

    //This viewholder contains a graph view....
    class GraphViewHolder extends RecyclerView.ViewHolder
    {
        public final GraphView graph;
        final StepAdapter stepAdapter;

        public GraphViewHolder(View graphView, StepAdapter adapter)
        {
            super(graphView);
            graph = graphView.findViewById(R.id.graph);
           // graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);


            /**

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            float maxValue = 0;
            //copyOfSeries = (PointsGraphSeries<DataPoint>) portraitItemList.get(0);
            for(int j = 0; j < portraitItemList.size(); j++)
            {
                float newValue = portraitItemList.get(j).getStep();
                //the below if statement finds the max number of steps walked in a day so far in the steps array
                if (newValue > maxValue)
                {
                    maxValue = newValue;
                }
            }

            graph.getViewport().setMaxY(maxValue);



            graph.getGridLabelRenderer().setHumanRounding(false);
             **/
            stepAdapter = adapter;
        }
    }
}
