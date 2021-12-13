package com.example.stepcounter_V3;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "step_table", primaryKeys = {"day", "year"})
public class Step
{
    @NonNull
    @ColumnInfo(name = "Step")
    private Step objectStep;

    @NonNull
    @ColumnInfo(name = "step")
    private float mStep;


    @NonNull
    @ColumnInfo(name = "year")
    private int mYear;


    @NonNull
    @ColumnInfo(name = "day")
    private int mDay;

    @NonNull
    @ColumnInfo(name = "date")
    private Date date;

    public Step(@NonNull int year, @NonNull int day, @NonNull Date date, @NonNull float step)
    {
        this.mYear = year;
        this.mDay = day;
        this.date = date;
        this.mStep = step;
        this.objectStep = new Step(mYear, mDay, date, step);

    }

    public float getStep()
    {
        return this.mStep;
    }

    public void setStep(float newSteps)
    {
        this.mStep = newSteps;
    }

    public int getYear()
    {
        return this.mYear;
    }

    public int getDay()
    {
        return this.mDay;
    }

    public Date getDate() {return this.date;}



}
