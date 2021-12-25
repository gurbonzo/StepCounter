package com.example.stepcounter_V3;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.Date;

public class StepServiceModule extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepDetector;
    private TextView mTextStepDetector;
    private StepRepository mRepository;
    String sensor_error;


    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, new Notification());
        mSensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        //mTextStepDetector = (TextView) mTextStepDetector.findViewById(R.id.label_detector);
        sensor_error = "No sensor";
        mRepository = new StepRepository(getApplication());


    }


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
        mRepository.insert(step);
        // stepValue += currentValue;
        //   mTextCounter.setText(getResources().getString(R.string.label_counter, stepValue));





    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        if(mStepDetector == null)
        {
            mTextStepDetector.setText(sensor_error);
        }
        else
        {
            mSensorManager.registerListener((SensorEventListener) getApplication(), mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
