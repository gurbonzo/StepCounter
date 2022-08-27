package com.example.stepcounter_V3;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StepViewModel extends AndroidViewModel
{
    private StepRepository mRepository;
    private LiveData<List<Step>> mAllSteps; //LiveData

    public StepViewModel (Application application)
    {
        super(application);
        mRepository = new StepRepository(application);
        mAllSteps = mRepository.getAllSteps(); // maybe here?
    }

    LiveData<List<Step>> getAllSteps()
    {
        return mAllSteps;
    }



    public void insert(Step step)
    {
        mRepository.insert(step); //inserting the steps into the StepRepository

    }

}
