package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.UserApi;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.api.UserApiFactory;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor {

    private UserApi userApi;

    public LoginInteractorImpl() {
        userApi = UserApiFactory.getApi();
    }

    @Override
    public void login(final String email, final String password, final OnLoginFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userApi.loginUser(new User(email, password)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            String token = response.body().message;
                            TokenIO.saveToken(token);
                            listener.onSuccess();
                        } else {
                            listener.onError("Wrong credentials");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        listener.onError("Fatal error");
                    }
                });
            }
        }).start();
    }

    @Override
    public void register(final String email, final String password, final OnLoginFinishedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userApi.registerUser(new User(email, password)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            String token = response.body().message;
                            System.out.println("JASPER REGISTER TOKEN: " + token);
                            TokenIO.saveToken(token);
                            listener.onSuccess();
                        } else {
                            listener.onError("Email already in use");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        listener.onError("Fatal error");
                    }
                });
            }
        }).start();
    }

    private AccessToken accessToken;
    @Override
    public void loginFacebook(CallbackManager callbackManager, Activity activity, OnLoginFinishedListener listener) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                try {
                    accessToken = loginResult.getAccessToken();
                    Profile profile = Profile.getCurrentProfile();
                    String name = profile.getName();
                    String id = profile.getId();
                    URL smallImage = new URL("http://graph.facebook.com/" + id + "/picture?type=small");
                    URL largeImage = new URL("http://graph.facebook.com/" + id + "/picture?type=large");
                    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            System.out.println(object);
                            System.out.println("ok");
                            //User user = new User();
                            //UserApiFactory.getApi().facebookLogin()
                        }
                    });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "email");
                    request.setParameters(parameters);
                    request.executeAsync();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
                System.out.println("hi");
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
        LoginManager.getInstance().logInWithReadPermissions(activity, Collections.singletonList("public_profile"));
    }
}