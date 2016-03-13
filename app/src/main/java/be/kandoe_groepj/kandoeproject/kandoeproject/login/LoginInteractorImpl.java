package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.SessionApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.UserApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.SimpleResult;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginInteractorImpl implements LoginInteractor {

    private UserApi userApi;

    public LoginInteractorImpl() {
        prepareRetrofit();
    }

    private void prepareRetrofit() {
        userApi = new Retrofit.Builder()
                .baseUrl("http://192.168.0.149:80/api/")
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(User.class, new TypescriptTypeAdapter<>(User.class)).create()))
                .build().create(UserApi.class);
    }

    @Override
    public void login(final String email, final String password, final OnLoginFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userApi.loginUser(new User(email, password)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        System.out.println(response.body());
                        System.out.println(response.raw().body());
                        System.out.println("ok");
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("jasper: " + t.getCause());
                        System.out.println("jasper: " + t.getMessage());
                    }
                });
            }
        }).start();
    }
}
