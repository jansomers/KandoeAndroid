package be.kandoe_groepj.kandoeproject.kandoeproject.session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jan on 1/03/2016.
 */
public class SessionOverviewPresenter {

    private final String BASE_URL = "http://10.0.3.2:8080/api/";
    private SessionApi sessionApi;
    private List<Session> sessionList;
    public SessionOverviewPresenter() {
        sessionApi = null;
        sessionList = new ArrayList<>();
    }
    /*
    public List<Session> getSessionList() {
        return DummyCreator.getDummySessions();
    }*/

    public void prepareRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        sessionApi = retrofit.create(SessionApi.class);
    }

    public List<Session> getData() {
        Call<List<Session>> call = sessionApi.getSessions();
        try {
            return call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
