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
      //the adapter should call directly to here i think
    //this method sends a StepDao object and step object information to the inner class intertAsyncTask in this class and activates the "execute" method
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




    //class that runs asynchronously from the main thread. Through the Dao can only access the database asynchronously. Asynchronous tasks allow the user to continue to swipe & scroll, etc. without freezing
    //if putting data into the database takes too long. In this case, it's also mandatory to have a asynchronous task querying the database as that is what the dao requires to work.
    //if you wanted to query the database on the main thread, you would have to explicitly state so in the Database class.
    private static class insertAsyncTask extends AsyncTask<Step, Void, Void>
    {
        private StepDao mAsyncTaskDao;
         insertAsyncTask(StepDao dao)
        {
            mAsyncTaskDao = dao;

        }

        //the method that is working asynchronously
        @Override
        protected Void doInBackground(final Step... steps)
        {
            int day = steps[0].getDay(); // extracting the day from the step object in the 0 position of the steps array.
            int year = steps[0].getYear(); //extracting the year from the step onject in the 0 position of the steps array.
            boolean result = mAsyncTaskDao.getStep2(day, year); // querying the database to see if such an object already exists
            if (result == true)
            {
                float value = mAsyncTaskDao.getSteps(day, year); //getting the # of steps from object that already exists
                value += steps[0].getStep(); //adding new amount of steps to the original amount -will only be 1 step added at a time because the code is activated after each step is detected.
                mAsyncTaskDao.updateSteps(value, day, year); //insert the new object with the increased number of steps back into the database.
                //send directly to the step adapter here??
            }
            else //if no object exists in the databse with the same day and year information then a new object is inserted into the database.
            {
                mAsyncTaskDao.insert(steps[0]);
                //send directly to the step adapter here??
            }
            return null; //no real functionality as far as I can tell, just required as part of the doInBackground code
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
