package com.example.bedshakerswe415;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Test
    public void testBasic() {

    }

//    @Rule
//    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
//
//    @Test
//    public void testButtonTextBeforeButtonPressed() {
//        //Check the text BEFORE button pressed
//        onView(withId(R.id.lblToggle)).check(matches(withText("Bed Shaker OFF")));
//    }
//
//    @Test
//    public void testButtonTextAfterButtonPressed() {
//        //Press the button
//        onView(withId(R.id.btnToggle)).perform(click());
//
//        //Check the text AFTER button is pressed
//        onView(withId(R.id.lblToggle)).check(matches(withText("Bed Shaker ON")));
//    }
//
//    @Test
//    public void testButtonToggleText() {
//        //Press button, turn shaker ON
//        onView(withId(R.id.btnToggle)).perform(click());
//        //Press button, turn shaker OFF
//        onView(withId(R.id.btnToggle)).perform(click());
//
//        //Check text after toggle ON/OFF
//        onView(withId(R.id.lblToggle)).check(matches(withText("Bed Shaker OFF")));
//    }
}