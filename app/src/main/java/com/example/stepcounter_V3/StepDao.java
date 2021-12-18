package com.example.stepcounter_V3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface StepDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Step step);

    @Query("DELETE FROM step_table")
    void deleteAll();

    @Query("SELECT * from step_table")
    LiveData<List<Step>> getAllSteps();

    @Query("SELECT * from step_table LIMIT 1")
    Step[] getAnyStep();

    @Query("SELECT step from step_table WHERE day =:thisDay AND year =:thisYear")
     boolean getStep2(int thisDay, int thisYear);


    //@Query("UPDATE step_table SET step = :newSteps WHERE year = :thisYear AND day = :thisDay")
    //public void updateSteps(float newSteps, int thisYear, int thisDay);

    @Query("SELECT step FROM step_table WHERE day =:thisDay AND year =:thisYear")
     float getSteps(int thisDay,int thisYear);

    @Query("UPDATE step_table SET step = :newSteps WHERE year = :thisYear AND day = :thisDay")
    public void updateSteps(float newSteps, int thisDay, int thisYear); //not sure if the array
    //will work
}

