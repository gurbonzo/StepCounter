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

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import android.view.View;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private StepViewModel nStepViewModel;
    private PointsGraphSeries<DataPoint> stepSeries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        final StepListAdapter adapter = new StepListAdapter(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        stepSeries = new PointsGraphSeries();
        nStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        nStepViewModel.getAllSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable final List<Step> steps) {
                // Update the cached copy of the words in the adapter.
                DataPoint [] stepData = new DataPoint[steps.size()];

                    for(int i = 0; i < steps.size(); i++ ) {
                        int xValue = steps.get(i).getDay();
                        int yValue = (int)steps.get(i).getStep();

                        DataPoint stepPoint = new DataPoint(xValue, yValue);
                        stepData[i] = stepPoint;
                    }
                    stepSeries.resetData(stepData);
                    graph.addSeries(stepSeries);
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(0);
                    stepSeries.setShape(PointsGraphSeries.Shape.POINT);
                    stepSeries.setColor(Color.RED);

                //add code for graph to update itself as it gets new data here
            }
        });





        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void goHome(View view)
    {
        Intent graphIntent = new Intent();
        setResult(RESULT_OK, graphIntent);
        finish();

    }

}
