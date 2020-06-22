package com.example.stepcounter_V3;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters
{
    @TypeConverter
    public static Long fromDate (Date date)
    {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Date fromLong (Long value)
    {
        return value == null ? null : new Date(value);
    }
}
