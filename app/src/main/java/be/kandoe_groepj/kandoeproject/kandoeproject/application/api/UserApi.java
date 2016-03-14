package be.kandoe_groepj.kandoeproject.kandoeproject.application.api;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.SimpleResult;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @POST("user/login")
    Call<User> loginUser(@Body User user);
}