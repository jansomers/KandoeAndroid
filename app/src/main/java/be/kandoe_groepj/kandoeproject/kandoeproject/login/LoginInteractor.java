package be.kandoe_groepj.kandoeproject.kandoeproject.login;

public interface LoginInteractor {

    void login(String email, String password, OnLoginFinishedListener listener);
}
