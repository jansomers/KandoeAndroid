package be.kandoe_groepj.kandoeproject.kandoeproject.session;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jan on 4/03/2016.
 */
public interface SessionApi {

    @GET("circlesessions")
    Call<List<Session>> getSessions();

}
