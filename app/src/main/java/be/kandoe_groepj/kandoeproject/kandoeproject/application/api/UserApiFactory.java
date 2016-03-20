package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import com.google.gson.GsonBuilder;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.helper.TypescriptTypeAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApiFactory {

    private static UserApi api;

    public static UserApi getApi() {
        return api == null ? (api = new Retrofit.Builder()
                .baseUrl("http://10.0.3.2:8080/api/")
                .addConverterFactory(
                        GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(User.class, new TypescriptTypeAdapter<>(User.class)).create()))
                .build().create(UserApi.class)) : api;
    }
}
