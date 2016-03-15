package be.kandoe_groepj.kandoeproject;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.OnFinishListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.SessionActivity;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class SessionTest {

    @Rule
    public final ActivityTestRule<SessionActivity> main = new ActivityTestRule<>(SessionActivity.class, true, false);

    //We gaan ervan uit dat Rob zijn account bestaat met dezelfde token
    @Test
    public void testSessionOverview() {
        Intent intent = new Intent();
        intent.putExtra("isTest", true);
        final SessionActivity activity = main.launchActivity(intent);
        final RecyclerView recyclerView = activity.getRecyclerView();
        TokenIO.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfdHlwZSI6IndlYiIsIl9uYW1lIjoiUm9iSGVuZHJpY2t4IiwiX2VtYWlsIjoicm9iLmhlbmRyaWNreEBzdHVkZW50LmtkZy5iZSIsIl9pZCI6IjU2ZTAzNzhmOTljNDM2YzdiYjRkNmEwYyJ9.d51772e45d44a99454040bd508f5bfc8d4ff25194a81e94891d47cbd51d0600f");
        final List<Session> data = activity.getAdapter().getData();
        main.getActivity().prepareData(new OnFinishListener() {
            @Override
            public void finished() {
                Matcher matcher = allOf(withId(R.layout.session_item));
                onView(matcher).check(matches(isDisplayed()));
                for (int i = 0; i < data.size(); i++) {
                    onView(withId(R.id.recyclerSessions)).perform(RecyclerViewActions.actionOnItemAtPosition(i, click()));


                }
            }
        });
    }
}
