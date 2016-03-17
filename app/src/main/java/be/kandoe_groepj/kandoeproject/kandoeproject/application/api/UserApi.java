package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import java.util.List;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("user/login")
    Call<User> loginUser(@Body User user);

    @POST("user/register")
    Call<User> registerUser(@Body User user);

    @POST("user/login-facebook")
    Call<User> facebookLogin(@Body User user);

    @GET("user/bulk/{array}")
    Call<List<User>> getSessionUsers(@Path("array") String [] userIds);
}