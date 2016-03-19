package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import org.json.JSONArray;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CardPosition;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CircleSessionMoveResponse;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Session;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SessionApi {

    @GET("circlesessions")
    Call<List<Session>> getSessions();

    @GET("circlesessions/{id}")
    Call<Session> getSession(@Path("id") String id);

    @GET("user/circlesessions")
    Call<List<Session>> getUserSessions(@Header("Bearer") String bearer);

    @POST("circlesessions/{id}/positions")
    Call<CardPosition> playCard(@Path("id") String id, @Header("Bearer") String bearer, @Body CardPosition cardPosition);

    @POST("circlesessions/{id}/cards")
    Call<CircleSessionMoveResponse> initCards(@Path("id") String id, @Header("Bearer") String bearer, @Body JSONArray selectedCards);
}
