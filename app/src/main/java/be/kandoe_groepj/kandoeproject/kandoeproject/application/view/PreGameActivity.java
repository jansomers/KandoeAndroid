package be.kandoe_groepj.kandoeproject.kandoeproject.application.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import be.kandoe_groepj.kandoeproject.R;
import be.kandoe_groepj.kandoeproject.kandoeproject.helper.TokenIO;
import butterknife.Bind;

public class PreGameActivity extends AppCompatActivity {

    @Bind(R.id.recyclerCardsPregame)
    RecyclerView recyclerView;

    @Bind(R.id.swipeCards)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSharedPref();
        setContentView(R.layout.activity_pre_game);


    }

    public void initSharedPref() {
        TokenIO.initSharedPreferences(getSharedPreferences("Test", Context.MODE_PRIVATE));
    }

}
