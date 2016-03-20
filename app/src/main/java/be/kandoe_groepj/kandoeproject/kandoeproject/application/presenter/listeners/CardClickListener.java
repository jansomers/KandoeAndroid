package be.kandoe_groepj.kandoeproject.kandoeproject.application.presenter.listeners;

import android.view.View;

/**
 * Created by Jan on 8/03/2016.
 */
public interface CardClickListener {

    public void onClick(View view, int position);
    public void onLongClick(View view, int position);

}
