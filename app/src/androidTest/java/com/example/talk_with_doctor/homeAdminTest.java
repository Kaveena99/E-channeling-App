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

public class homeAdminTest {

    @Rule
    public ActivityTestRule<homeAdmin> haActivityTestRule = new ActivityTestRule<homeAdmin>(homeAdmin.class);   //creating testRule to launch homeAdmin activity
    private homeAdmin haActivity = null;

    //creating monitors to monitor whether the activity launched or not
    Instrumentation.ActivityMonitor monitorAddDoctor = getInstrumentation().addMonitor(addDoctor.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorAddPharmacy = getInstrumentation().addMonitor(addPharmacy.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorViewPatient = getInstrumentation().addMonitor(viewPatient.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorViewPharmacy = getInstrumentation().addMonitor(viewPharmacy.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorVieDoctor = getInstrumentation().addMonitor(viewDoctor.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        haActivity = haActivityTestRule.getActivity();      //get reference of homeAdmin activity
    }

    //Test whether homeAdmin activity launching
    @Test
    public void testLaunch(){
        View view = haActivity.findViewById(R.id.txtAdmin);
        assertNotNull(view);
    }

    //Test whether homeAdmin activity launch addDoctor activity when button clicked
    @Test
    public void testLaunchOfAddDoctor(){
        assertNotNull(haActivity.findViewById(R.id.btnAddDoctor));
        onView(withId(R.id.btnAddDoctor)).perform(click());
        Activity addDoctorActivity = getInstrumentation().waitForMonitorWithTimeout(monitorAddDoctor, 5000);
        assertNotNull(addDoctorActivity);
        addDoctorActivity.finish();

    }

    //Test whether homeAdmin activity launch addPharmacy activity when button clicked
    @Test
    public void testLaunchOfAddPharmacy(){
        assertNotNull(haActivity.findViewById(R.id.btnAddPharmacy));
        onView(withId(R.id.btnAddPharmacy)).perform(click());
        Activity addPharmacyActivity = getInstrumentation().waitForMonitorWithTimeout(monitorAddPharmacy, 5000);
        assertNotNull(addPharmacyActivity);
        addPharmacyActivity.finish();

    }

    //Test whether homeAdmin activity launch viewPatient activity when button clicked
    @Test
    public void testLaunchOfViewPatient(){
        assertNotNull(haActivity.findViewById(R.id.btnViewPatient));
        onView(withId(R.id.btnViewPatient)).perform(click());
        Activity viewPatientActivity = getInstrumentation().waitForMonitorWithTimeout(monitorViewPatient, 5000);
        assertNotNull(viewPatientActivity);
        viewPatientActivity.finish();

    }
    //Test whether homeAdmin activity launch viewPharmacy activity when button clicked
    @Test
    public void testLaunchOfviewPharmacy(){
        assertNotNull(haActivity.findViewById(R.id.btnViewPharmacy));
        onView(withId(R.id.btnViewPharmacy)).perform(click());
        Activity viewPharmacyActivity = getInstrumentation().waitForMonitorWithTimeout(monitorViewPharmacy, 5000);
        assertNotNull(viewPharmacyActivity);
        viewPharmacyActivity.finish();

    }
    //Test whether homeAdmin activity launch viewDoctor activity when button clicked
    @Test
    public void testLaunchOfviewDoctor(){
        assertNotNull(haActivity.findViewById(R.id.btnViewDoctor));
        onView(withId(R.id.btnViewDoctor)).perform(click());
        Activity viewDoctorActivity = getInstrumentation().waitForMonitorWithTimeout(monitorVieDoctor, 5000);
        assertNotNull(viewDoctorActivity);
        viewDoctorActivity.finish();

    }

    @After
    public void tearDown() throws Exception {
        haActivity = null;
    }
}