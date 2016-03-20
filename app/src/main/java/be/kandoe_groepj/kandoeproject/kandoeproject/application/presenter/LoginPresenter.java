package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.app.Activity;

import com.facebook.CallbackManager;

public interface LoginPresenter {

    void validateCredentials(String email, String password);
    void register(String email, String password);
    void loginFacebook(CallbackManager callbackManager, Activity activity);
}