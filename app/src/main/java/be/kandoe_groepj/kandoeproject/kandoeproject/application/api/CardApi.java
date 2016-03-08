package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.Card;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jan on 8/03/2016.
 */
public interface CardApi {

    @GET("themes/{themeId}/cards")
    Call<List<Card>> getCards(@Path("themeId") String themeId);
}
