package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CircleSessionCardWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CardApi {

    @GET("themes/{themeId}/cards")
    Call<List<Card>> getCards(@Path("themeId") String themeId ,@Header("Bearer") String token);

    @GET("circlesessions/{id}/cards")
    Call<List<CircleSessionCardWrapper>> getCircleSessionCards(@Path("id") String id, @Header("Bearer") String bearer);
}