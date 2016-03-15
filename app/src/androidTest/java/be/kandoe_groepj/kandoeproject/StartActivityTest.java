package be.kandoe_groepj.kandoeproject;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Jan on 1/03/2016.
 */
//Testrunner you want to use
@RunWith(AndroidJUnit4.class)
public class StartActivityTest {

    @Rule
    public final ActivityRule<StartActivity> main = new ActivityRule<>(StartActivity.class);
    //Adhv test gemerkt dat id's niet in aparte layout moet staan maar in de include
    @Test
    public void shouldLaunchStartScreen() {
        onView(withId(R.id.startActivityLayout)).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void sessionButtonActive() {
        //Is the button clickable
        onView(withId(R.id.go_sessions_btn)).check(ViewAssertions.matches(isClickable()));
        //Click the button
        onView(withId(R.id.go_sessions_btn)).perform(ViewActions.click());
        //Check if new Activity is displayed
        onView(withId(R.id.sessionActivityLayout)).check(ViewAssertions.matches(isDisplayed()));
    }
}
