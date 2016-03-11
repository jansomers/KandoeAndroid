package be.kandoe_groepj.kandoeproject;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isFocusable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

//Testrunner you want to use
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final ActivityRule<LoginActivity> main = new ActivityRule<>(LoginActivity.class);
    //Adhv test gemerkt dat id's niet in aparte layout moet staan maar in de include
    @Test
    public void shouldLaunchLoginScreen() {
        onView(withId(R.layout.activity_login)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkLoginComponents() {
        onView(withId(R.id.txt_email)).check(ViewAssertions.matches(isFocusable()));
        onView(withId(R.id.txt_password)).check(ViewAssertions.matches(isFocusable()));
        onView(withId(R.id.login_btn)).check(ViewAssertions.matches(isClickable()));

    }

    /**/
    @Test
    public void checkWrongLogin() {
        onView(withId(R.id.txt_email)).perform(ViewActions.typeText(String.valueOf(R.string.wrong_email_input)));
        onView(withId(R.id.txt_password)).perform(ViewActions.typeText(String.valueOf(R.string.wrong_password_input)));
        onView(withId(R.id.login_btn)).perform(ViewActions.click());
        onView(withId(R.id.login_result_label)).check(ViewAssertions.matches(withText(String.valueOf(R.string.error_invalid_credentials))));

    }
}
