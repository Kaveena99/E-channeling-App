package com.example.talk_with_doctor;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class homePharmacyTest {

    @Rule
    //to test the activity
    //@rule specify the homePharmacy activity is launched
    public ActivityTestRule<homePharmacy> mActivityTestRule = new ActivityTestRule<homePharmacy>(homePharmacy.class);
    private homePharmacy mActivity = null;

    //monitors
    Instrumentation.ActivityMonitor monitorIncomeInsert = getInstrumentation().addMonitor(incomeInsert.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorViewPrescription = getInstrumentation().addMonitor(prescriptionPharmacy.class.getName(),null,false);


    @Before
    //every time before executing the test case
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    //before test launch setup will be launched
    //after test launch tearDown will be launched
    public void testLaunch(){
        View view = mActivity.findViewById(R.id.textView19);
        assertNotNull(view); //view is not null test case is successfully

    }

    //test for check launching of income insert activity after clicking the button in the homePharmacy page
    @Test
    public void testLaunchOfIncomeInsert(){
        assertNotNull(mActivity.findViewById(R.id.button));
        onView(withId(R.id.button)).perform(click());
        Activity viewIncomeInsert = getInstrumentation().waitForMonitorWithTimeout(monitorIncomeInsert, 5000);
        assertNotNull(viewIncomeInsert);
        viewIncomeInsert.finish();

    }

    //test for check launching of prescription  activity after clicking the button in the homePharmacy page
    @Test
    public void testLaunchOfPrescriptionPharmacy(){
        assertNotNull(mActivity.findViewById(R.id.button3));
        onView(withId(R.id.button3)).perform(click());
        Activity viewPrescription = getInstrumentation().waitForMonitorWithTimeout(monitorViewPrescription, 5000);
        assertNotNull(viewPrescription);
        viewPrescription.finish();

    }
    @After
    //every time after executing the test case
    public void tearDown() throws Exception {
    }
}