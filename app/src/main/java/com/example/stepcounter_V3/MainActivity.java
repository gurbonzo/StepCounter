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
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.jjoe64.graphview.series.DataPoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private StepViewModel mStepViewModel;
    Intent serviceIntent;
    ArrayList<Step> stepsTaken;
    ArrayList<Step> copyStepsTaken;

    //the onOptionsItemSelected method was created in an attempt to determine whether the play or pause button should be
    // shown on the tool bar if the activity is closed and then reopened.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
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

    @Override
    protected void onStart() {
        super.onStart();

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
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
