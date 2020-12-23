package com.example.stepcounter_V3;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private StepViewModel nStepViewModel;
    private PointsGraphSeries<DataPoint> stepSeries;
    private LineGraphSeries<DataPoint> stepLineSeries;
    private GraphView graph;
    private TextView averageStepCounter;
    private TextView maxSteps;
    private TextView minSteps;
    private TextView averageWeeklySteps;
    ArrayList<Step> stepsTaken;
    ArrayList<Step> copyStepsTaken;
    final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private RecyclerView mRecyclerView;
    private StepAdapter mAdapter;
    private ArrayList <PointsGraphSeries> portraitItemList;
    public static final String TAG = "Wasup?";
    //private final LinkedList<String> mWordList = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //graph = (GraphView) findViewById(R.id.graph);


       // RecyclerView recyclerView = findViewById(R.id.recyclerview); //inflates the recyclerview
       // final StepAdapter adapter = new StepAdapter(this);
       // recyclerView.setAdapter(adapter);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //final StepListAdapter adapter = new StepListAdapter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar calendar = Calendar.getInstance();
        portraitItemList = new ArrayList<PointsGraphSeries>();







        stepSeries = new PointsGraphSeries(); //initializes the point graph
        stepLineSeries = new LineGraphSeries(); //initializes the line graph that connects the point graphs

        nStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        nStepViewModel.getAllSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable final List<Step> steps) {
                // Update the cached copy of the words in the adapter.

                //assert steps != null;
                Log.d(TAG, steps.getClass().getSimpleName());
                //Toast.makeText(this, "starting insert", Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();




                stepsTaken = new ArrayList<Step>();//may not need this anymore
                copyStepsTaken = new ArrayList<Step>(); //may not need this anymore
                DataPoint [] stepData = new DataPoint[steps.size()];

                //this if statement is for the initial time that a user might open the graphactivity before any steps have been added.
                if(steps.size() == 0)
                {
                    //do nothing
                }
                else {


                    //for statement goes through the List called steps that contains Step objects 1 at a time.
                    for (int i = 0; i < steps.size(); i++)
                    {
                        //int xValue = steps.get(i).getDay();
                        stepsTaken.add(steps.get(i)); //each step object is copied from the original list to the stepsTaken List in each turn of the for loop
                        copyStepsTaken.add(steps.get(i)); //1 step oject at a time is copied from the original list to the copyStepsTaken list in each turn of the for loop
                        //String xValue = sdf.format(steps.get(i).getDate());
                        Date xValue = steps.get(i).getDate(); //the date value for each step object is taken and stored as an xValue variable.

                        int yValue = (int) steps.get(i).getStep(); //the number of steps in each step object is taken and stored as the yvalue variable.

                        DataPoint stepPoint = new DataPoint(xValue, yValue); //the xValue and yValue, representing the date and steps taken for each step object as it comes up in the for loop
                        // is put into a DataPoint object that stores these values as coordinates.
                        stepData[i] = stepPoint; //the DataPoint object is then put into an array called stepData that is also ratcheted up by the for loop.
                    }

                    //this for loop is activated if the phone is in portrait orientation
                    // Now I don't think this if statement is needed, because the if state should be about what is displayed
                    //while the phone is held in portrait orientation, not what calculations are done. As such, the calculations
                    //could be calculated regardless of the phone orientation, but what is displayed should be dependent on the phone
                    //orientation. However, to use less power, you could have specific calculations done in certain orientations
                    //so that calculations for things that aren't even displayed in a certain orientation are not done
                    //thus saving power. (Dec. 12, 2020).
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                        //averageStepCounter = (TextView) findViewById(R.id.averageSteps);
                        //maxSteps = (TextView) findViewById(R.id.maxSteps);
                        //minSteps = (TextView) findViewById(R.id.minSteps);
                        //averageWeeklySteps = (TextView) findViewById(R.id.weeklyAverage);
                        float totalSteps = 0;
                        float maxValue = 0;
                        float minValue = 0;
                        //the below for loop goes through the steps list and calculates different metrics.
                       /** for(int j = 0; j < steps.size(); j++)
                        {
                            totalSteps += stepsTaken.get(j).getStep(); //I believe I used the stepsTaken array rather then the original steps array because the IDE wouldn't let me.
                            //Likely because the steps array was created in a different section and so it didn't have a reference here. To be checked though....
                            float newValue = stepsTaken.get(j).getStep();
                            //the below if statement finds the max number of steps walked in a day so far in the steps array
                            if (newValue > maxValue)
                            {
                                maxValue = newValue;
                            }
                            for(int k = j +1; k < steps.size(); k++)
                            {
                                //this if statement is meant to find the smallest number of steps taken in a day from the steps array
                                if(copyStepsTaken.get(j).getStep() > copyStepsTaken.get(k).getStep())
                                {
                                    minValue = copyStepsTaken.get(j).getStep();
                                    //copyStepsTaken.get(j).getStep() = copyStepsTaken.get(k).getStep();
                                }
                            }




                        } **/
                        //calculating the average number of steps taken in a day over all the step objects in the array.
                        //float averageSteps = totalSteps / stepsTaken.size();
                        //String info = getResources().getString(R.string.averageSteps, averageSteps);
                        //portraitItemList.add(averageSteps);
                        //portraitItemList.add(steps);


                        //averageStepCounter.setText(getResources().getString(R.string.averageSteps, averageSteps));
                        //maxSteps.setText(getResources().getString(R.string.maxSteps, maxValue));
                    }

                    //double check what the below code does

                    stepSeries.resetData(stepData);
                    stepLineSeries.resetData(stepData);
                    graph.addSeries(stepLineSeries);
                    graph.addSeries(stepSeries);

                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GraphActivity.this, dateFormat));
                    graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                    graph.getLegendRenderer().setVisible(true);
                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(steps.get(0).getDate().getTime());
                    graph.getViewport().setMaxX(steps.get(0).getDate().getTime() + 2*24*60*60*1000);

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(0);

                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScrollable(true);
                    graph.getViewport().setScrollableY(true);
                    graph.getViewport().setScalableY(true);

                    graph.getGridLabelRenderer().setHumanRounding(false);
                    stepSeries.setShape(PointsGraphSeries.Shape.POINT);
                    stepSeries.setColor(Color.RED);


                    //portraitItemList.add(stepLineSeries);
                   // stepSeries.resetData(stepData); //adding the stepData array to the stepSeries graph
                    //portraitItemList.add(stepSeries); //adding the stepSeries graph to the portraitItemList
                   // adapter.setInfo(portraitItemList); //sending the portraitItemList to the adapter which controls the UI...
                    //adapter.setInfo(steps);
                    // Get a handle to the RecyclerView.





                }   //add code for graph to update itself as it gets new data here
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview); //inflates the recyclerview
        final StepAdapter adapter = new StepAdapter(this, portraitItemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void goHome(View view)
    {
        Intent graphIntent = new Intent();
        setResult(RESULT_OK, graphIntent);
        finish();

    }

    public void resetGraph(View view)
    {
      graph.getViewport().setXAxisBoundsManual(true);
      graph.getViewport().setMinX(stepsTaken.get(0).getDate().getTime());
      graph.getViewport().setMaxX(stepsTaken.get(0).getDate().getTime() + 2*24*60*60*1000);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(GraphActivity.this, dateFormat));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);
        graph.onDataChanged(true, true);
    }

}
