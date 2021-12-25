package com.example.stepcounter_V3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
   // private SensorManager mSensorManager;  Dec. 23, 2021
    private Sensor mStepCounter;
    private Sensor mStepDetector;

    private TextView mTextStepCounter;
    private TextView mTextStepDetector;
    private TextView mTextCounter;

    private TextView mTestDay;
    private TextView mTestYear;

    private float stepValue = 0;
    private float valueCollect;

    private StepViewModel mStepViewModel;
    //private Button button;



   // private PointsGraphSeries<DataPoint> stepSeries;
   // private LineGraphSeries<DataPoint> stepLineSeries;
   // private GraphView graph;
    // private TextView averageStepCounter;
    // private TextView maxSteps;
    // private TextView minSteps;
    // private TextView averageWeeklySteps;
    ArrayList<Step> stepsTaken;
    ArrayList<Step> copyStepsTaken;
    final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private ArrayList<PointsGraphSeries> portraitItemList;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public static final String EXTRA_REPLY =
            "com.example.stepcounter_V3.REPLY";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId())
       {
           case R.id.start_stepcounter:
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   startForegroundService(new Intent(this, StepServiceModule.class));
               }
               else
               {
                   startService(new Intent(this, StepServiceModule.class));
               }
           default:

       }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        valueCollect = 0;
       // mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE); Dec. 23, 2021
        mTextStepCounter = (TextView)findViewById(R.id.label_steps);
        mTextStepDetector = (TextView)findViewById(R.id.label_detector);
        mTextCounter = (TextView)findViewById(R.id.label_counter);
        mTestDay = (TextView)findViewById(R.id.get_day);
        mTestYear = (TextView)findViewById(R.id.get_year);
        //mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); //look into the documentation for this counter
       // mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR); Dec. 23, 2021
       // String sensor_error = "No sensor"; Dec. 23, 2021
        /**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, StepServiceModule.class));
        }
        else
        {
            startService(new Intent(this, StepServiceModule.class));
        }
         **/


        // Setup the WordViewModel
        mStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        /*if(mStepCounter == null)
        {
            mTextStepCounter.setText(sensor_error);
        }

         */
        /** Dec. 23, 2021
        if(mStepDetector == null)
        {
            mTextStepDetector.setText(sensor_error);
        }
         **/
         //button = findViewById(R.id.button_save);

        //Toast.makeText(getApplicationContext(), "onCreate initialized", Toast.LENGTH_LONG).show();
      //  stepSeries = new PointsGraphSeries();
       // stepLineSeries = new LineGraphSeries();




        RecyclerView recyclerView = findViewById(R.id.recyclerview); //inflates the recyclerview
        final StepAdapter adapter = new StepAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        mStepViewModel.getAllSteps().observe(this, new Observer<List<Step>>() {
            @Override
            public void onChanged(@Nullable final List<Step> steps) {
                // Update the cached copy of the words in the adapter.




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
                        stepsTaken.add(steps.get(i));
                        copyStepsTaken.add(steps.get(i));
                        //String xValue = sdf.format(steps.get(i).getDate());
                        Date xValue = steps.get(i).getDate();
                       // int xValue = steps.get(i).getDay();

                        int yValue = (int) steps.get(i).getStep();

                        DataPoint stepPoint = new DataPoint(xValue, yValue);
                        stepData[i] = stepPoint;
                    }


                   // stepSeries.resetData(stepData);
                   // stepLineSeries.resetData(stepData);
                   // graph.addSeries(stepLineSeries);
                   // graph.addSeries(stepSeries);
                    adapter.setInfo(stepData);
                }   //add code for graph to update itself as it gets new data here
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();


       /* if(mStepCounter != null)
        {
            mSensorManager.registerListener(this, mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }

        */
       /** Dec. 23, 2021
        if(mStepDetector != null)
        {
            mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
        **/
    }

    /**
    public void graph(View view)
    {
        Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
        startActivity(intent);

    }
     **/

    public void onButtonPressed(View view) {



                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
               // date = calendar.add(Calendar.DAY_OF_MONTH, 1);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                int year1 = calendar.get(Calendar.YEAR);
                //int day1 = 17;
                //int year1 = 2020;
                float step1 = 20;
                Step step = new Step(year1, day1, date, step1);
                mStepViewModel.insert(step);
                //int day2 = 17;
                //int year2 = 2020;
                float steps2 = 40;
                calendar.add(Calendar.DATE, 1);
                 Date date2 = calendar.getTime();
                 int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                 int year2 = calendar.get(Calendar.YEAR);
                Step step2 = new Step(year2, day2, date2, steps2);
                mStepViewModel.insert(step2);
                //int day3 = 19;
                //int year3 = 2020;
                float steps3 = 50;
                calendar.add(Calendar.DATE, 1);
                Date date3 = calendar.getTime();
        int day3 = calendar.get(Calendar.DAY_OF_MONTH);
        int year3 = calendar.get(Calendar.YEAR);
                Step step3 = new Step(year3, day3, date3, steps3);
                mStepViewModel.insert(step3);
        //int day4 = 20;
        //int year4 = 2020;
        float steps4 = 10;
        calendar.add(Calendar.DATE, 1);
        Date date4 = calendar.getTime();
        int day4 = calendar.get(Calendar.DAY_OF_MONTH);
        int year4 = calendar.get(Calendar.YEAR);
        Step step4 = new Step(year4, day4, date4, steps4);
        mStepViewModel.insert(step4);
        //int day5 = 21;
        //int year5 = 2020;
        float steps5 = 20;
        calendar.add(Calendar.DATE, 1);
        Date date5 = calendar.getTime();
        int day5 = calendar.get(Calendar.DAY_OF_MONTH);
        int year5 = calendar.get(Calendar.YEAR);
        Step step5 = new Step(year5, day5, date5, steps5);
        mStepViewModel.insert(step5);
        //int day6 = 21;
        //int year6 = 2020;
        float steps6 = 20;
        calendar.add(Calendar.DATE, 1);
        Date date6 = calendar.getTime();
        int day6 = calendar.get(Calendar.DAY_OF_MONTH);
        int year6 = calendar.get(Calendar.YEAR);
        Step step6 = new Step(year6, day6, date6, steps6);
        mStepViewModel.insert(step6);
        //int day7 = 22;
        //int year7 = 2020;
        float steps7 = 20;
        calendar.add(Calendar.DATE, 1);
        Date date7 = calendar.getTime();
        int day7 = calendar.get(Calendar.DAY_OF_MONTH);
        int year7 = calendar.get(Calendar.YEAR);
        Step step7 = new Step(year7, day7, date7, steps7);
        mStepViewModel.insert(step7);


                //int day2 = 18;
                //int year2 = 2020;
                //float step2 = 40;
                //Step stepv2 = new Step(year2, day2, step2);
               // mStepViewModel.insert(stepv2);




               // mTestDay.setText("Day: " + calendar.get(Calendar.DAY_OF_MONTH));
                //mTestYear.setText("Year: " + calendar.get(Calendar.YEAR));


                Toast.makeText(this, "starting insert", Toast.LENGTH_LONG).show();
                    //mStepViewModel.insert(step);
                    Toast.makeText(this, "insert worked", Toast.LENGTH_LONG).show();


    }

    public float getValueCollector()
    {
        //valueCollect += currentValue;
        return valueCollect;
    }

    public void setValueCollect(float value)
    {
         valueCollect = value;
    }

    /** Dec. 23, 2021
    @Override
    public void onSensorChanged(SensorEvent event) {

       // int sensorType = event.sensor.getType();
        float currentValue = event.values[0];



           // mTextStepDetector.setText(getResources().getString(R.string.label_detector, currentValue));
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);
                Step step = new Step(year, day, date, currentValue);
                mStepViewModel.insert(step);
           // stepValue += currentValue;
         //   mTextCounter.setText(getResources().getString(R.string.label_counter, stepValue));





    }
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    **/
}
