package be.kandoe_groepj.kandoeproject.kandoeproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import be.kandoe_groepj.kandoeproject.R;

public class StartActivity extends AppCompatActivity implements StartView {

    //private StartPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //presenter = new StartPresenter(this, new StartApi());
    }

    public void onSessionOverviewClicked(View view) {
        //presenter.onSessionOverviewClicked(this, view);
    }
}