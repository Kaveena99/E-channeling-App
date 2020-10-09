package com.example.talk_with_doctor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Income income;

    @Before
    public void setup(){
        income = new Income();
    }

    @Test
    public void setProfit(){

        int income = 1200;
        int expence = 1000;
        int output = income-expence;
        int expected = 200;

        assertEquals(expected, output);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}