package com.example.stepcounter_V3;

import android.app.Application;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Application mockApplication;

    int day;
    int year;
    float currentValue;
    Step step;
    StepRepository tRepository;
    StepViewModel tstepViewModel;

    @Before
    public void setUp()
    {
        day = 13;
        year = 2020;
        currentValue = 10;
        mockApplication = new Application();
        tstepViewModel = new StepViewModel(mockApplication);
        step = new Step(year, day, currentValue);
        tRepository = new StepRepository(mockApplication);
    }

    @Test
    public void checkRepository() {
        //final boolean expected = false;
        boolean result = tRepository.getDate(day, year);


        Assert.assertFalse(result);
    }
}