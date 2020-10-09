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

public class AppoinmentsDoctorActivityTest {


    @Rule
    public ActivityTestRule<HomeDoctorActivity> haActivityTestRule = new ActivityTestRule<HomeDoctorActivity>(HomeDoctorActivity.class);   //creating testRule to launch HomeDoctorActivity activity
    private HomeDoctorActivity haActivity = null;

    //creating monitors to monitor whether the activity launched or not
    Instrumentation.ActivityMonitor monitorAppoinment = getInstrumentation().addMonitor(AppoinmentsDoctorActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorMyAppoinments = getInstrumentation().addMonitor(MyAppoinmentsDoctorActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorViewPatientDoctor = getInstrumentation().addMonitor(ViewPatientDoctorActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor monitorDoctorNotification = getInstrumentation().addMonitor(NotificationDoctorActivity.class.getName(), null, false);

    @Before
    public void setUp() throws Exception {
        haActivity = haActivityTestRule.getActivity();      //get reference of HomeDoctorActivity activity
    }

    //Test whether HomeDoctorActivity activity launching
    @Test
    public void testLaunch(){
        View view = haActivity.findViewById(R.id.DoctorHome);
        assertNotNull(view);
    }

    //Test whether HomeDoctorActivity launch Appoinment activity when button clicked
    @Test
    public void testLaunchOfDoctorAppoinment(){
        assertNotNull(haActivity.findViewById(R.id.Appoinment));
        onView(withId(R.id.Appoinment)).perform(click());
        Activity AppoinmentDoctor = getInstrumentation().waitForMonitorWithTimeout(monitorAppoinment, 5000);
        assertNotNull(AppoinmentDoctor);
        AppoinmentDoctor.finish();

    }

    //Test whether HomeDoctor activity launch myAppoinment activity when button clicked
    @Test
    public void testLaunchOfMyAppoinments(){
        assertNotNull(haActivity.findViewById(R.id.my_appoinment));
        onView(withId(R.id.my_appoinment)).perform(click());
        Activity MyAppoinments = getInstrumentation().waitForMonitorWithTimeout(monitorMyAppoinments, 5000);
        assertNotNull(MyAppoinments);
        MyAppoinments.finish();

    }

    //Test whether HomeDoctor activity launch viewPatient activity when button clicked
    @Test
    public void testLaunchOfViewPatient(){
        assertNotNull(haActivity.findViewById(R.id.ViewPatient));
        onView(withId(R.id.ViewPatient)).perform(click());
        Activity ViewPatientDoctior = getInstrumentation().waitForMonitorWithTimeout(monitorViewPatientDoctor, 5000);
        assertNotNull(ViewPatientDoctior);
        ViewPatientDoctior.finish();

    }
    //Test whether HomeDoctor activity launch DoctorNotification activity when button clicked
    @Test
    public void testLaunchOfDoctorNotification(){
        assertNotNull(haActivity.findViewById(R.id.Notification));
        onView(withId(R.id.Notification)).perform(click());
        Activity NotificationDoctor = getInstrumentation().waitForMonitorWithTimeout(monitorDoctorNotification, 5000);
        assertNotNull(NotificationDoctor);
        NotificationDoctor.finish();

    }


    @After
    public void tearDown() throws Exception {
        haActivity = null;
    }
}