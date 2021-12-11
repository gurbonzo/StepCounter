package com.example.stepcounter_V3;

import android.content.Context;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    /**
     * Order in which RecyclerView methods are called
     1.The RecyclerView attempts to grab a view holder for a certain position from its pool of recycled (or scrapped) view holders.
     2.When it does this, it calls getItemViewType for the position it wants to fill
     3.It then attempts to grab a previously created view holder associated with this item view type.
     4.If the RecyclerView view doesn't have an available view holder for this item type, it calls onCreateViewHolder in order to create a new view holder for this item type.
     5.Once the view holder is attained (whether by creating a new one, or grabbing a scrapped / recycled one), it then calls onBindViewHolder from the adapter to set the correct data for the view holder.
     6.During all this, getItemCount will be used to ensure that the RecyclerView doesn't attempt to populate a view holder beyond the limit of the data-source.

     */


    /**
     * It seems that you need a linked list rather than an ArrayList so that
     * the recyclerview will have directions on which information should be displayed next
     */
    private ArrayList portraitItemList;
    private List portraitItemList3;
        private LayoutInflater inflater;
    ArrayList <Series> seriesArrayList;
    private PointsGraphSeries<DataPoint> stepSeriesCopy;
    private LineGraphSeries<DataPoint> stepLineSeriesCopy;
    private int[] xValues;


        //private List<Step> steps;
        final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

        public StepAdapter(Context context, LinkedList items)
        {
            inflater = LayoutInflater.from(context);
            this.seriesArrayList = new ArrayList<>();

            //portraitItemList3 = new LinkedList <Object>();
            //portraitItemList3 = portraitItemList2;
            /**
            if(portraitItemList3.get(0).getClass().getSimpleName().toLowerCase().contains("seriesarraylist"))
            {

            }**/
            portraitItemList3 = items;
            /**
            this.seriesArrayList = (ArrayList<Series>) seriesArrayList;
            this.stepSeries = (PointsGraphSeries<DataPoint>) this.seriesArrayList.get(0);
            this.stepLineSeries = (LineGraphSeries<DataPoint>) this.seriesArrayList.get(1);
             **/

            //how to ratchet through a linked list

        }


        @Override
        public int getItemViewType(int position) {

            if(portraitItemList3.get(position).getClass().getSimpleName().toLowerCase().contains("array"))
            {
                return 0;
            }
            else if(portraitItemList3.get(position).getClass().getSimpleName().toLowerCase().contains("float"))
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
            //switch (viewType) {
               // case 0:
                    itemView = inflater.inflate(R.layout.portrait_graph_item, parent, false); // onCreateViewHolder just expands the layout inside the portrait_graph_item.xml, not the actual graph.
                    return new GraphViewHolder(itemView, this);
               // case 1:
               //     itemView = inflater.inflate(R.layout.portrait_item, parent, false);
                //    return new StepViewHolder(itemView, this);
           // }
            //return null;
        }

     /**
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(portraitItemList != null)
        {
            /**
             * It should be that in the first line, the datapoints should be taken from the list
             * and then they should all be collected in a new list after which they should be put into a series,
             * then the series should be placed in the graph in the third line. In the example of the recyclerview
             * each word is taken from the list with its position remembered and then each word is then put into a
             * viewHolder.
             *
             */
            /**
            Iterator itr = portraitItemList2.iterator();
            while(itr.hasNext())
                for(int i = 0; i <= portraitItemList2.size(); i++)
                {
                    if(portraitItemList2.get(i).getClass().getSimpleName().toLowerCase().contains("seriesarrayist"));
                    {
                        for(int j = 0; j < portraitItemList2.size(); j++)
                        {

                            holder.graphItem.addSeries((Series) portraitItemList2.get(j));
                        }

                    }
                } **/
            //GraphView graph = (GraphView) portraitItemList.get(position);
            // holder.graphItem.  //pretty confused. graphItem is from GraphViewHolder extends RecyclerView.ViewHolder
            /**
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
    } **/

    @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if(portraitItemList3 != null)
            {
                /**
                 * It should be that in the first line, the datapoints should be taken from the list
                 * and then they should all be collected in a new list after which they should be put into a series,
                 * then the series should be placed in the graph in the third line. In the example of the recyclerview
                 * each word is taken from the list with its position remembered and then each word is then put into a
                 * viewHolder.
                 *
                 */
              
                
                ((GraphViewHolder)holder).graphItem.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(((GraphViewHolder) holder).graphItem.getContext(), dateFormat));
                ((GraphViewHolder)holder).graphItem.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(((GraphViewHolder)holder).graphItem.getContext(), dateFormat));

                ((GraphViewHolder)holder).graphItem.getGridLabelRenderer().setNumHorizontalLabels(3);
                ((GraphViewHolder)holder).graphItem.getLegendRenderer().setVisible(true);
                ((GraphViewHolder)holder).graphItem.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                ((GraphViewHolder)holder).graphItem.getViewport().setXAxisBoundsManual(true);
                //((GraphViewHolder)holder).graphItem.getViewport().setMinX(stepSeriesCopy.getLowestValueX());
                //((GraphViewHolder)holder).graphItem.getViewport().setMaxX(stepSeriesCopy.getHighestValueX());

                //((GraphViewHolder)holder).graphItem.getViewport().setMinX(steps.get(0).getDate().getTime());
                //((GraphViewHolder)holder).graphItem.getViewport().setMaxX(steps.get(0).getDate().getTime() + 2*24*60*60*1000);


                ((GraphViewHolder)holder).graphItem.getViewport().setYAxisBoundsManual(true);
                ((GraphViewHolder)holder).graphItem.getViewport().setMinY(0);

                ((GraphViewHolder)holder).graphItem.getViewport().setScalable(true);
                ((GraphViewHolder)holder).graphItem.getViewport().setScrollable(true);
                ((GraphViewHolder)holder).graphItem.getViewport().setScrollableY(true);
                ((GraphViewHolder)holder).graphItem.getViewport().setScalableY(true);

                ((GraphViewHolder)holder).graphItem.getGridLabelRenderer().setHumanRounding(false);


                ((GraphViewHolder)holder).graphItem.addSeries(stepSeriesCopy);
                ((GraphViewHolder)holder).graphItem.addSeries(stepLineSeriesCopy);
                //int realPosition = position;
              /** Iterator itr = portraitItemList2.iterator();
                while(itr.hasNext())

                    for(int i = 0; i < portraitItemList3.size(); i++)
                    {

                        if(portraitItemList3.get(i).getClass().getSimpleName().toLowerCase().contains("seriesarraylist"));
                        {
                            holder.getItemViewType();
                            for(int j = 0; j < seriesArrayList.size(); j++)
                            {
                                       ((GraphViewHolder)holder).graphItem.addSeries((Series)portraitItemList.get(j));
                            }

                        }
                    }
                    **/
                //GraphView graph = (GraphView) portraitItemList.get(position);
               // holder.graphItem.  //pretty confused. graphItem is from GraphViewHolder extends RecyclerView.ViewHolder
                /**
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
                 **/
            }
            else
            {

                // holder.stepItemView.setText("No word");
            }

        }


        void setInfo(List portraitItemList2)
        {
            portraitItemList3 = portraitItemList2;
            stepSeriesCopy = (PointsGraphSeries<DataPoint>) portraitItemList3.get(0);
            stepLineSeriesCopy = (LineGraphSeries<DataPoint>) portraitItemList3.get(1);
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
            //the purpose of the ViewHolder classes is to associate the data that was received outside of the recycler
            //view with a view item inside the recyclerview. Essentially just equating the same things, so
            // a graphview from outside the recyclerview gets equated with a graphview inside the recyclerview.


        {
            public final GraphView graphItem;
            //final com.example.stepcounter_V3.StepAdapter stepAdapter;
            final StepAdapter adapter;

            int j;

            public GraphViewHolder(View graphView, StepAdapter adapter) //this is the constructor
            {
                super(graphView);
                graphItem = graphView.findViewById(R.id.graph); //graphItem view is set to the parameters of graph inside portrait_graph_item.xml
                graphItem.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graphItem.getContext(), dateFormat));
                graphItem.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graphItem.getContext(), dateFormat));

                graphItem.getGridLabelRenderer().setNumHorizontalLabels(3);
                graphItem.getLegendRenderer().setVisible(true);
                graphItem.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphItem.getViewport().setXAxisBoundsManual(true);
                graphItem.getViewport().setMinX(0);
                graphItem.getViewport().setMaxX(100);
                //graphItem.getViewport().setMinX(stepSeriesCopy.getLowestValueX());
                //graphItem.getViewport().setMaxX(stepSeriesCopy.getHighestValueX());
                this.adapter = adapter;

                /**
               Iterator itr = portraitItemList2.iterator();
               while(itr.hasNext())
               for(int i = 0; i <= portraitItemList2.size(); i++)
               {
                   if(portraitItemList2.get(i).getClass().getSimpleName().toLowerCase().contains("seriesarrayist"));
                   {
                       for(int j = 0; j < portraitItemList2.size(); j++)
                       {
                           graphItem.addSeries((Series) portraitItemList2.get(j));
                       }

                   }
               }
                 **/


                //graphViewHolder.graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(, dateFormat));
                //graphItem.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Context context, dateFormat));
                /**
                graphItem.getGridLabelRenderer().setNumHorizontalLabels(3);
                graphItem.getLegendRenderer().setVisible(true);
                graphItem.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphItem.getViewport().setXAxisBoundsManual(true);
                graphItem.getViewport().setMinX(steps.get(0).getDate().getTime());
                graphItem.getViewport().setMaxX(steps.get(0).getDate().getTime() + 2*24*60*60*1000);


                graphItem.getViewport().setYAxisBoundsManual(true);
                graphItem.getViewport().setMinY(0);

                graphItem.getViewport().setScalable(true);
                graphItem.getViewport().setScrollable(true);
                graphItem.getViewport().setScrollableY(true);
                graphItem.getViewport().setScalableY(true);

                graphItem.getGridLabelRenderer().setHumanRounding(false);
                 **/


                //stepAdapter = adapter;

            }

        }


    }



