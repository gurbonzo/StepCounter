package com.example.stepcounter_V3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Step.class}, version = 1, exportSchema = false)
public abstract class StepRoomDatabase extends RoomDatabase
{
    public abstract StepDao stepDao();

    private static StepRoomDatabase INSTANCE;

    public static StepRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null) //if not null then goes to return INSTANCE below
        {
            synchronized(StepRoomDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),StepRoomDatabase.class, "step_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                   // new PopulateDbAsync(INSTANCE).execute();
                }
            };

   /* private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>
    {
        private final StepDao mDao;

        public PopulateDbAsync(StepRoomDatabase db)
        {
            mDao = db.stepDao();
        }

        @Override
        protected Void doInBackground(final Void... params)
        {
           if (mDao.getAnyStep().length < 1)
           {

           }
           return null;
        }
    }

    */
}
