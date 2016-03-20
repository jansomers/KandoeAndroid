package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.app.Activity;

import com.facebook.CallbackManager;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.OnLoginFinishedListener;

public interface LoginInteractor {

    void login(String email, String password, OnLoginFinishedListener listener);
    void register(String email, String password, OnLoginFinishedListener listener);
    void loginFacebook(CallbackManager callbackManager, Activity activity, OnLoginFinishedListener listener);
}
