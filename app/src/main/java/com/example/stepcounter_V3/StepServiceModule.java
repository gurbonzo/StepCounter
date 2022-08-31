package com.example.stepcounter_V3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;
import java.util.Date;

public class StepServiceModule extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mStepDetector;
    private TextView mTextStepDetector;
    private StepRepository mRepository;
    String sensor_error;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    //private NotificationManager mNotifyManager;
   // private static final int NOTIFICATION_ID = 1;

    public static boolean onOrOff;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        Notification notification = new Notification.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("StepCounter is active!")
                //.setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .build();

        startForeground(1, notification);
        mSensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        sensor_error = "No sensor";
        mRepository = new StepRepository(getApplication());

        onOrOff = true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float currentValue = event.values[0];

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        Step step = new Step(year, day, date, currentValue);
        mRepository.insert(step);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(mStepDetector == null)
        {
            mTextStepDetector.setText(sensor_error);
        }
        else
        {
            mSensorManager.registerListener((SensorEventListener) this, mStepDetector, SensorManager.SENSOR_DELAY_NORMAL);
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        onOrOff = false;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
