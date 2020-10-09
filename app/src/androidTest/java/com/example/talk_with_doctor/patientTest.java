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
import static org.junit.Assert.assertNotNull;

public class patientTest {

    @Rule
    public ActivityTestRule<homePatient> mActivityTestRule = new ActivityTestRule<homePatient>(homePatient.class);
    private homePatient mActivity = null;

    //monitors
    Instrumentation.ActivityMonitor monitorNewAppointment = getInstrumentation().addMonitor(newAppointment.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorViewBookings = getInstrumentation().addMonitor(Bookings.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorOrderMedicines = getInstrumentation().addMonitor(orderMedicines.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorNotifications = getInstrumentation().addMonitor(notificationsPatient.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    //test for check launching
    @Test
    public void  testLaunch() {
        View view = mActivity.findViewById(R.id.txtPatient);
        assertNotNull(view);
    }

    //test for check launching of new appointment activity after clicking the button in the homePatient page
    @Test
    public void testLaunchNewAppointmentButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.newAppointment_btn));
        onView(withId(R.id.newAppointment_btn)).perform(click());
        Activity newAppointment = getInstrumentation().waitForMonitorWithTimeout(monitorNewAppointment,5000);
        assertNotNull(newAppointment);
        newAppointment.finish();
    }

    //test for check launching of Bookings activity after clicking the button in the homePatient page
    @Test
    public void testLaunchViewBookingsButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.viewBookings));
        onView(withId(R.id.viewBookings)).perform(click());
        Activity bookings = getInstrumentation().waitForMonitorWithTimeout(monitorViewBookings,5000);
        assertNotNull(bookings);
        bookings.finish();
    }

    //test for check launching of orderMedicines activity after clicking the button in the homePatient page
    @Test
    public void testLaunchOrderMedicinesButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.orderMedicine));
        onView(withId(R.id.orderMedicine)).perform(click());
        Activity orderMedicines = getInstrumentation().waitForMonitorWithTimeout(monitorOrderMedicines,5000);
        assertNotNull(orderMedicines);
        orderMedicines.finish();
    }

    //test for check launching of notification activity after clicking the button in the homePatient page
    @Test
    public void testLaunchNotificationButtonClick(){
        assertNotNull(mActivity.findViewById(R.id.notificationPatient));
        onView(withId(R.id.notificationPatient)).perform(click());
        Activity notification = getInstrumentation().waitForMonitorWithTimeout(monitorNotifications,5000);
        assertNotNull(notification);
        notification.finish();
    }


    @After
    public void tearDown() {
        mActivity = null;
    }
}
