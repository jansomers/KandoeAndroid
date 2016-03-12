package be.kandoe_groepj.kandoeproject.kandoeproject;

import android.content.Intent;
import android.view.View;

import be.kandoe_groepj.kandoeproject.kandoeproject.session.SessionOverviewActivity;

public class StartPresenter {
    private StartView view;
    private StartApi api;

    public StartPresenter(StartView view, StartApi api) {
        this.view = view;
        this.api = api;
    }

    public void onSessionOverviewClicked(StartActivity start, View view) {
        Intent i = new Intent(start, SessionOverviewActivity.class);
        start.startActivity(i);
    }
}