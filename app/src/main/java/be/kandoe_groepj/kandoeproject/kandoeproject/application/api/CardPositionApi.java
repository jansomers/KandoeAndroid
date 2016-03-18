package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.CardPosition;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by Jan on 18/03/2016.
 */
public interface CardPositionApi {

    @GET("/api/circlesessions/{sessionId}/positions")
    Call<List<CardPosition>> getCardPositions(@Path("sessionId") String sessionId, @Header("Bearer") String token);
}
