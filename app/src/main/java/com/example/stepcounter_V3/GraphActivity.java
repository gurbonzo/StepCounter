package com.example.stepcounter_V3;

import android.content.Intent;
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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private StepViewModel nStepViewModel;
    private PointsGraphSeries<DataPoint> stepSeries;
    private LineGraphSeries<DataPoint> stepLineSeries;
    private GraphView graph;
   // private TextView averageStepCounter;
   // private TextView maxSteps;
   // private TextView minSteps;
   // private TextView averageWeeklySteps;
    ArrayList<Step> stepsTaken;
    ArrayList<Step> copyStepsTaken;
    final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private ArrayList<GraphView> portraitItemList;
    private LinkedList portraitItemList2 = new LinkedList();
    ArrayList <Series> seriesArrayList = new ArrayList<>();
    private static final String TAG = "GRAPHACTIVITY";
    //Series[] seriesArray = new Series[2];
    //DataPoint [] stepData ;
    int[] yValues;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = (GraphView) findViewById(R.id.graph);

        //final StepAdapter adapter = new StepAdapter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar calendar = Calendar.getInstance();
        portraitItemList = new ArrayList<GraphView>();
        stepSeries = new PointsGraphSeries();
        stepLineSeries = new LineGraphSeries();

        //RecyclerView recyclerView = findViewById(R.id.recyclerview); //inflates the recyclerview
        //final StepAdapter adapter = new StepAdapter(this, portraitItemList2);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        nStepViewModel.getAllSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable final List<Step> steps) {
                // Update the cached copy of the words in the adapter.
                //adapter.setInfo(steps);


                stepsTaken = new ArrayList<Step>();
                copyStepsTaken = new ArrayList<Step>();
                 DataPoint [] stepData = new DataPoint[steps.size()];

                if(steps.size() == 0)
                {
                    //Toast.makeText(this, "No steps taken yet", Toast.LENGTH_LONG).show();
                    //do nothing
                }
                else {


                    for (int i = 0; i < steps.size(); i++) {
                        //int xValue = steps.get(i).getDay();
                        //DataPoint[] stepData = new DataPoint[steps.size()];
                        stepsTaken.add(steps.get(i));
                        copyStepsTaken.add(steps.get(i));
                        //String xValue = sdf.format(steps.get(i).getDate());
                        Date xValue = steps.get(i).getDate();

                        int yValue = (int) steps.get(i).getStep();
                        yValues[i] = yValue;

                        DataPoint stepPoint = new DataPoint(xValue, yValue);
                        stepData[i] = stepPoint;
                    }


                    stepSeries.resetData(stepData);
                    stepLineSeries.resetData(stepData);




                    //seriesArrayList.add(stepSeries);
                    //seriesArrayList.add(stepLineSeries);
                    //portraitItemList2.add(seriesArrayList);
                    portraitItemList2.add(stepSeries);
                    portraitItemList2.add(stepLineSeries);

                   // adapter.setInfo(portraitItemList2);
                    //could I just send the stepData as the array DataPoint? Nov. 22, 2021
                    //adapter.setInfo(portraitItemList2);




                }   //add code for graph to update itself as it gets new data here
            }
        });





        //Log.d(TAG, String.valueOf((seriesArrayList.get(0))));







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
