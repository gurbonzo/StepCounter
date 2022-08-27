package com.example.stepcounter_V3;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


public class MainActivity extends AppCompatActivity implements SensorEventListener {
   // private SensorManager mSensorManager;  Dec. 23, 2021
    private Sensor mStepCounter;
    private Sensor mStepDetector;

    private TextView mTextStepCounter;
    private TextView mTextStepDetector;
    private TextView mTextCounter;

    private TextView mTestDay;
    private TextView mTestYear;
    FloatingActionButton graphFab;

    private float stepValue = 0;
    private float valueCollect;

    private StepServiceModule stepService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    private StepViewModel mStepViewModel;
    private NotificationManager mNotifyManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    Intent serviceIntent;




    ArrayList<Step> stepsTaken;
    ArrayList<Step> copyStepsTaken;



    boolean isItOn;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    public static final String EXTRA_REPLY =
            "com.example.stepcounter_V3.REPLY";

    //the onOptionsItemSelected method was created in an attempt to determine whether the play or pause button should be
    // shown on the tool bar if the activity is closed and then reopened.

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())  //comment this out
       {
           case R.id.start_stepcounter:

               if(StepServiceModule.onOrOff != true)
               {

                   serviceIntent = new Intent(MainActivity.this, StepServiceModule.class);
                   startService(serviceIntent); //the method startService calls the OnStartCommand, as per android documentation.
                   //This causes the service to be started.

                   item.setIcon(R.drawable.ic_pause_counting);
               }
               else if (StepServiceModule.onOrOff == true)
               {

                   item.setIcon(R.drawable.ic_start_counting);
                   serviceIntent = new Intent(MainActivity.this, StepServiceModule.class);
                   stopService(serviceIntent);
               }

               break;


          case R.id.pause_stepcounter:
              stopService(serviceIntent);
              item.setIcon(R.drawable.ic_start_counting);
              break;
       }
        return super.onOptionsItemSelected(item);

    }


    //onCreate is the basic method where the contents of the screen are defined and inflated
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //goes under res-->layout-->activity_main.xml

        Toolbar toolbar = findViewById(R.id.toolbar); //goes under res--> layout-->activity_main.xml->reads the id named toolbar
        setSupportActionBar(toolbar);


        isItOn = false;
        valueCollect = 0;

        mTextStepCounter = (TextView)findViewById(R.id.label_steps); //defining mTextstepCounter as the xml code
        mTextStepDetector = (TextView)findViewById(R.id.label_detector);
        mTextCounter = (TextView)findViewById(R.id.label_counter);
        mTestDay = (TextView)findViewById(R.id.get_day);
        mTestYear = (TextView)findViewById(R.id.get_year);
        graphFab = findViewById(R.id.fab); //fab stands for floating action button and is programmed to add in the
        //dummy values for testing. Should be deleted in the final product.


        //setting the listener so that when graph fab is pressed, it inserts the below data into the memory which is then
        //displayed in the graph
        graphFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                // date = calendar.add(Calendar.DAY_OF_MONTH, 1);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                int year1 = calendar.get(Calendar.YEAR);
                //int day1 = 17;
                //int year1 = 2020;
                float step1 = 20;
                Step step = new Step(year1, day1, date, step1); //creating a step object
                mStepViewModel.insert(step); //inserting a step object
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

            }
        });

        mStepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);

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

                        stepsTaken.add(steps.get(i));
                        copyStepsTaken.add(steps.get(i));

                        Date xValue = steps.get(i).getDate();


                        int yValue = (int) steps.get(i).getStep();

                        DataPoint stepPoint = new DataPoint(xValue, yValue);
                        stepData[i] = stepPoint;
                    }

                    adapter.setInfo(stepData);
                }
            }
        });

    }

    private void bindService() {
        if (serviceConnection == null) //if there is no serviceConnection
        {
            //create a new serviceConnection object
            serviceConnection = new ServiceConnection() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) { //called when the connection to a service has been established
                    //stepService.
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;

                }
            };
        }
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

    }


    @Override
    protected void onStart() {
        super.onStart();

    }


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


                Toast.makeText(this, "starting insert", Toast.LENGTH_LONG).show();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(StepServiceModule.onOrOff != true)
        {
           MenuItem item = menu.findItem(R.id.start_stepcounter);
           item.setIcon(R.drawable.ic_start_counting);


        }
        else
        {
            MenuItem item = menu.findItem(R.id.start_stepcounter);
            item.setIcon(R.drawable.ic_pause_counting);


        }

        return super.onPrepareOptionsMenu(menu);


    }



    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
