package com.saucelabs.testconnect;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class UrlTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void correctUrlTestSucceeds() {
        String testUrl = "https://google.com";
        onView(withId(R.id.urlField)).perform(typeText(testUrl));
        onView(withId(R.id.testButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Connection to " + testUrl + " was successful.")));
    }

    @Test
    public void incorrectUrlProtocolFails() {
        String testUrl = "htt://google.com";
        onView(withId(R.id.urlField)).perform(typeText(testUrl));
        onView(withId(R.id.testButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("java.net.MalformedURLException: unknown protocol"))));
    }

    @Test
    public void incorrectUrlDomainFails() {
        String testUrl = "https://google.ccccc";
        onView(withId(R.id.urlField)).perform(typeText(testUrl));
        onView(withId(R.id.testButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("java.net.UnknownHostException: Unable to resolve host"))));
    }

}
