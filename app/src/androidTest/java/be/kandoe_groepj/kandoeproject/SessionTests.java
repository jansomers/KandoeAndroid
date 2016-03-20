package be.kandoe_groepj.kandoeproject;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.SessionActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

//import android.support.test.espresso.contrib.RecyclerViewActions;

@RunWith(AndroidJUnit4.class)
public class SessionTests {

    @Rule
    public final ActivityRule<SessionActivity> main = new ActivityRule<>(SessionActivity.class);

    /*//We gaan ervan uit dat Rob zijn account bestaat met dezelfde token
    @Test
    public void testSessionOverview() {
        Intent intent = new Intent();
        intent.putExtra("isTest", true);
        final SessionActivity activity = main.launchActivity(intent);
        //final RecyclerView recyclerView = activity.getRecyclerView();
        //TokenIO.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfdHlwZSI6IndlYiIsIl9uYW1lIjoiUm9iSGVuZHJpY2t4IiwiX2VtYWlsIjoicm9iLmhlbmRyaWNreEBzdHVkZW50LmtkZy5iZSIsIl9pZCI6IjU2ZTAzNzhmOTljNDM2YzdiYjRkNmEwYyJ9.d51772e45d44a99454040bd508f5bfc8d4ff25194a81e94891d47cbd51d0600f");
        final List<Session> data = activity.getAdapter().getData();
        main.getActivity().prepareData(new OnFinishListener() {
            @Override
            public void finished() {
                Matcher matcher = allOf(withId(R.layout.session_item));
                onView(matcher).check(matches(isDisplayed()));
                for (int i = 0; i < data.size(); i++) {
                    //onView(withId(R.id.recyclerSessions)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));
                    //onView(withId(R.id.recyclerSessions)).perform(scrollToPosition(i)).check(matches(isDisplayed()));

                }
            }
        });
    }*/

    @Test
    public void itemsDisplayed() {
        onView(withId(R.id.recyclerSessions)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(ViewAssertions.matches(isDisplayed()));

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