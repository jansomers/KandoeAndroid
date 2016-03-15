package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface SessionApi {

    @GET("circlesessions")
    Call<List<Session>> getSessions();

    @GET("/circlesessions/{id}")
    Call<Session> getSession(@Path("id") String id);

    @GET("/user/circlesessions")
    Call<List<Session>> getUserSessions(@Header("Bearer") String bearer);

    //Todo GET SESSIONCARDS
}
