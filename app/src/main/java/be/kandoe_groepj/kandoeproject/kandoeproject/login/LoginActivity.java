package be.kandoe_groepj.kandoeproject.kandoeproject.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.application.view.SessionActivity;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;

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
        TokenIO.initSharedPreferences(getPreferences(Context.MODE_PRIVATE));
        AccessToken.refreshCurrentAccessTokenAsync();
        String token = TokenIO.loadToken();
        if (token.equals("")) {

        } else {

            TokenIO.removeToken();

        }
        FacebookSdk.sdkInitialize(getApplicationContext());

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

    private CallbackManager callbackManager = CallbackManager.Factory.create();
    private void addEventsToComponents() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.validateCredentials(email.getText().toString(), password.getText().toString());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register(email.getText().toString(), password.getText().toString());
            }
        });
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginFacebook(callbackManager, LoginActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setError(String error) {
        errorTextView.setText(error);
    }

    @Override
    public void navigateToHome() {
        String token = TokenIO.loadToken();
        startActivity(new Intent(this, SessionActivity.class));

        finish();
    }
}