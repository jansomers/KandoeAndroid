package be.kandoe_groepj.kandoeproject.kandoeproject.session;

import android.os.Debug;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.SessionApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(Session.class, new TypeAdapter<Session>() {
            @Override
            public void write(JsonWriter out, Session session) throws IOException {
                out.beginObject();
                Class c = Session.class;
                for (Field field : c.getDeclaredFields()) {
                    try {
                        field.setAccessible(true);
                        out.name("_" + field.getName()).value(field.get(session).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                out.endObject();
            }

            @Override
            public Session read(JsonReader in) throws IOException {
                Class c = Session.class;
                Session session = new Session();
                while (in.hasNext()) {
                    try {
                        Field field = c.getDeclaredField(in.nextName().replace("_", ""));
                        field.setAccessible(true);
                        field.set(session, in.nextString());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                in.endObject();
                return session;
            }
        }).create())).build();
        sessionApi = retrofit.create(SessionApi.class);
        Log.d("TEST", "Test!");
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
