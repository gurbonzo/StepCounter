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
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
       // Intent notificationIntent = new Intent(this, MainActivity.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
       // Notification notification = new Notification.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
        //        .setContentTitle()
        //startForeground(1, new Notification());
        mSensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mStepDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        //mTextStepDetector = (TextView) mTextStepDetector.findViewById(R.id.label_detector);
        sensor_error = "No sensor";
        mRepository = new StepRepository(getApplication());
        createNotificationChannel();


    }

    private NotificationCompat.Builder getNotificationBuilder(){
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("StepCounter is active!")
                //.setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android);
        return notifyBuilder;


    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel()
     {
     mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
     //if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.0)
     //{
     NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
     "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
         //notificationChannel.enableLights(true);
         //notificationChannel.setLightColor(Color.RED);
         notificationChannel.enableVibration(true);
         notificationChannel.setDescription("Notification from Mascot");
         mNotifyManager.createNotificationChannel(notificationChannel);

     //}
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
        sendNotification();

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
        mNotifyManager.cancelAll();
        //mNotifyManager.cancel("primary_notification_channel", 0);
        super.onDestroy();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
