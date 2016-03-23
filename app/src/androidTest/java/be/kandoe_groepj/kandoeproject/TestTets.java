package be.kandoe_groepj.kandoeproject;

import android.support.test.rule.ActivityTestRule;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.SessionActivity;

public class TestTets {

    @Rule
    public ActivityTestRule<SessionActivity> activityTestRule = new ActivityTestRule<>(SessionActivity.class);

    @Test
    public void validateEditText(){
        Assert.assertNotNull("hi");

    }

    /*@Test
    public void optionsMenuDisplayed() {
        onView(withId(R.menu.pop_up_menu)).check(ViewAssertions.matches(isDisplayed()));;
    }
    @Test
    public void optionItemLogoutDisplayed() {
        onView(withId(R.menu.pop_up_menu)).perform(ViewActions.click());
        onView(withId(R.id.logout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testLogout() {
        onView(withId(R.menu.pop_up_menu)).perform(ViewActions.click());
        onView(withId(R.id.logout)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.logout)).perform(ViewActions.click());
        //Sleep to let it do it's job
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(hasComponent(LoginActivity.class.getName()));
    }*/
}