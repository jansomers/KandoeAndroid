package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import android.app.Activity;

import com.facebook.CallbackManager;

public interface LoginInteractor {

    void login(String email, String password, OnLoginFinishedListener listener);
    void register(String email, String password, OnLoginFinishedListener listener);
    void loginFacebook(CallbackManager callbackManager, Activity activity, OnLoginFinishedListener listener);
}
