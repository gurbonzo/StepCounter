package com.example.stepcounter_V3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
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

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public static final String EXTRA_REPLY =
            "com.example.stepcounter_V3.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        valueCollect = 0;
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mTextStepCounter = (TextView)findViewById(R.id.label_steps);
        mTextStepDetector = (TextView)findViewById(R.id.label_detector);
        mTextCounter = (TextView)findViewById(R.id.label_counter);
        mTestDay = (TextView)findViewById(R.id.get_day);
        mTestYear = (TextView)findViewById(R.id.get_year);
        //mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER); //look into the documentation for this counter
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        String sensor_error = "No sensor";
        // Setup the WordViewModel
        mStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        /*if(mStepCounter == null)
        {
            mTextStepCounter.setText(sensor_error);
        }

         */
        if(mStepDetector == null)
        {
            mTextStepDetector.setText(sensor_error);
        }
         //button = findViewById(R.id.button_save);

        //Toast.makeText(getApplicationContext(), "onCreate initialized", Toast.LENGTH_LONG).show();
    }



    @Override
    protected void onStart() {
        super.onStart();
       /* if(mStepCounter != null)
        {
            mSensorManager.registerListener(this, mStepCounter,SensorManager.SENSOR_DELAY_NORMAL);
        }

        */
        if(mStepDetector != null)
        {
            mSensorManager.registerListener(this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void graph(View view)
    {
        Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
        startActivity(intent);

    }

    public void onButtonPressed(View view) {


                //float value = getValueCollector();
                //Toast toast = Toast.makeText(this,"button pressed", Toast.LENGTH_LONG);
                //toast.show();
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);
               // Step step = new Step(year, day, value);
                //value = 0;
                //setValueCollect(value);

                mTestDay.setText("Day: " + calendar.get(Calendar.DAY_OF_MONTH));
                mTestYear.setText("Year: " + calendar.get(Calendar.YEAR));


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

    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
       // switch (sensorType)
        //{
           // case Sensor.TYPE_STEP_COUNTER:
            //mTextStepCounter.setText(getResources().getString(R.string.label_steps, currentValue));
             //valueCollect += currentValue;
                //Calendar calendar = Calendar.getInstance();
                //int day = calendar.get(Calendar.DAY_OF_MONTH);
                //int year = calendar.get(Calendar.YEAR);
                //Step step = new Step(year, day, currentValue);
                //mStepViewModel.insert(step);
                //Calendar calendar = Calendar.getInstance();

                //int day = calendar.get(Calendar.DAY_OF_MONTH);
                //int year = calendar.get(Calendar.YEAR);
                //Step step = new Step(year, day, currentValue);
                //mStepViewModel.insert(step);
                //onButtonPressed(currentValue);
           // break;
            //case Sensor.TYPE_STEP_DETECTOR:
            mTextStepDetector.setText(getResources().getString(R.string.label_detector, currentValue));
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int year = calendar.get(Calendar.YEAR);
                Step step = new Step(year, day, currentValue);
                mStepViewModel.insert(step);
            stepValue += currentValue;
            mTextCounter.setText(getResources().getString(R.string.label_counter, stepValue));
           // break;
            //default:
                //do nothing
        //}

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
