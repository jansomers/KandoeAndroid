package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter;

import android.app.Activity;

import com.facebook.CallbackManager;

import be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners.OnLoginFinishedListener;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter, OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void validateCredentials(String email, String password) {
        loginInteractor.login(email, password, this);
    }

    @Override
    public void register(String email, String password) {
        loginInteractor.register(email, password, this);
    }

    @Override
    public void loginFacebook(CallbackManager callbackManager, Activity activity) {
        loginInteractor.loginFacebook(callbackManager, activity, this);
    }

    @Override
    public void onError(String error) {
        if (loginView != null) {
            loginView.setError(error);
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }
}
