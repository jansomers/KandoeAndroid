package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.model.User;
import be.kandoe_groepj.kandoeproject.kandoeproject.session.SessionOverviewActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText email;
    private EditText password;
    private Button facebookButton;
    private Button loginButton;
    private Button registerButton;
    private TextView errorTextView;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindComponents();
        addEventsToComponents();
        presenter = new LoginPresenterImpl(this);
    }

    private void bindComponents() {
        email = (EditText) findViewById(R.id.tLoginEmail);
        password = (EditText) findViewById(R.id.tLoginPassword);
        facebookButton = (Button) findViewById(R.id.bLoginActivityFacebook);
        loginButton = (Button) findViewById(R.id.bLoginActivityLogin);
        registerButton = (Button) findViewById(R.id.bLoginActivityRegister);
        errorTextView = (TextView) findViewById(R.id.lLoginError);
    }

    private void addEventsToComponents() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateCredentials(email.getText().toString(), password.getText().toString());
            }
        });
    }

    @Override
    public void setError() {
        errorTextView.setText("Error occurred!");
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, SessionOverviewActivity.class));
        finish();
    }
}
