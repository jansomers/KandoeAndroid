package be.kandoe_groepj.kandoeproject;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.OnFinishListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.SessionActivity;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;

/**
 * Created by Jan on 15/03/2016.
 */
@RunWith(AndroidJUnit4.class)
public class SessionTest {

    @Rule
    public final ActivityRule<SessionActivity> main = new ActivityRule<>(SessionActivity.class);

    //We gaan ervan uit dat Rob zijn account bestaat met dezelfde token
    @Test
    public void testSessionOverview(){
        TokenIO.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfdHlwZSI6IndlYiIsIl9uYW1lIjoiUm9iSGVuZHJpY2t4IiwiX2VtYWlsIjoicm9iLmhlbmRyaWNreEBzdHVkZW50LmtkZy5iZSIsIl9pZCI6IjU2ZTAzNzhmOTljNDM2YzdiYjRkNmEwYyJ9.d51772e45d44a99454040bd508f5bfc8d4ff25194a81e94891d47cbd51d0600f");
        main.get().prepareData(new OnFinishListener() {
            @Override
            public void finished() {

            }
        });
    }
}
