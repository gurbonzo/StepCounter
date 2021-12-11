package com.example.stepcounter_V3;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class StepViewModel extends AndroidViewModel
{
    private StepRepository mRepository;
    private LiveData<List<Step>> mAllSteps;

    public StepViewModel (Application application)
    {
        super(application);
        mRepository = new StepRepository(application);
        mAllSteps = mRepository.getAllSteps();
    }

    public LiveData<List<Step>> getAllSteps()
    {
        return mAllSteps;
    }



    public void insert(Step step)
    {
        mRepository.insert(step);
        /*if(mRepository.getSteps(step))
        {
            float oldSteps = mRepository.getStep3(step).getStep();
            oldSteps += step.getStep();
            step.setStep(oldSteps);
            mRepository.update(step);
        }
        else {
            mRepository.insert(step);
        }

         */
    }
 /*
    public void update(Step step)
    {
        mRepository.update(step);
    }

    public boolean getDate(int day, int year)  {
        boolean value;
          value = mRepository.getDate(day, year);
          return value;
    }

  */
}
