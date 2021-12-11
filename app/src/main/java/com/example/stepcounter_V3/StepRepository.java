package com.example.stepcounter_V3;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StepRepository
{
    private StepDao mStepDao;
    private LiveData<List<Step>> mAllSteps;

    StepRepository(Application application)
    {
        StepRoomDatabase stepdatabase = StepRoomDatabase.getDatabase(application);
        mStepDao = stepdatabase.stepDao();
        mAllSteps = mStepDao.getAllSteps(); //maybe dont need this
    }

    LiveData<List<Step>> getAllSteps()
    {
        return mAllSteps;
    }

    public void insert (Step step)
    {
        new insertAsyncTask(mStepDao).execute(step);
    }

    /*public void update (Step step)
    {
        new updateAsyncTask(mStepDao).execute(step);
    }

     */



    /*public void getStep3(Step step)  {

        //return new getStep2AsyncTask(mStepDao).execute(step);
    }

     */

    /*public boolean getSteps(Step step)
    {
        boolean result = Boolean.parseBoolean(String.valueOf(new getStepsAsyncTask(mStepDao).execute(step)));
        return result;

        //return mStepDao.getSteps(day, year);

    }

     */





    private static class insertAsyncTask extends AsyncTask<Step, Void, Void>
    {
        private StepDao mAsyncTaskDao;
         insertAsyncTask(StepDao dao)
        {
            mAsyncTaskDao = dao;

        }

        @Override
        protected Void doInBackground(final Step... steps)
        {
            int day = steps[0].getDay(); //takes the day stamp from the steps object
            int year = steps[0].getYear(); //takes the year stamp from the steps object
            boolean result = mAsyncTaskDao.getStep2(day, year); //looks for an object that already exists with the same day and year variables
            if (result == true) //if such an object exists, the result is true and the if statement is executed, if not, the else statement is executed
            {
                float value = mAsyncTaskDao.getSteps(day, year); //value variable represents the number of steps taken from the object in the array with the appropriate day and year
                value += steps[0].getStep(); //adds the number of steps from the "steps" object to the value

                mAsyncTaskDao.updateSteps(value, day, year); //puts the updated object back into place in the array.
            }
            else
            {
                mAsyncTaskDao.insert(steps[0]);
            }
            return null;
        }


    }

    /*private static class updateAsyncTask extends AsyncTask<Step, Void, Void>
    {
        private StepDao mAsyncTaskDao;
        private int day;
        private int year;
        updateAsyncTask(StepDao dao)
        {
            mAsyncTaskDao = dao;
            //this.day = day;
            //this.year = year;
        }

        @Override
        protected Void doInBackground(final Step... steps) {

            float stepCount = steps[0].getStep();
            day = steps[0].getDay();
            year = steps[0].getYear();


            mAsyncTaskDao.updateSteps(stepCount, day, year); //previously stepCount was written where steps in currently
            return null;
        }
    }

     */


    /*private class getStepsAsyncTask extends AsyncTask<Step, Void, Boolean> {

        private StepDao mAsyncTaskDao;
        public getStepsAsyncTask(StepDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Boolean doInBackground(Step... steps) {
            int day = steps[0].getDay();
            int year = steps[0].getYear();

            if(mAsyncTaskDao.getSteps(day, year).length < 1)
            {
                return false;
            }
            else
            {
                return true;
            }





        }
    }

     */

    /*private class getStep2AsyncTask extends AsyncTask<Step, Void, Step> {
        private StepDao mAsyncTaskDao;
        public getStep2AsyncTask(StepDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Step doInBackground(Step... steps) {
            int day = steps[0].getDay();
            int year = steps[0].getYear();
            return mAsyncTaskDao.getStep2(day, year);

        }
    }

     */
}
